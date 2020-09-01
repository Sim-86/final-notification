package movieTicket;

import movieTicket.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    NotificationRepository notificationRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentSucceed_SendNotification(@Payload PaymentSucceeded paymentSucceeded){

        if(paymentSucceeded.isMe()){

            Notification notification = new Notification();
            if(("").equals(paymentSucceeded.getBookingId())){
                notification.setBookingId(5L);
            }
            else
                notification.setBookingId(paymentSucceeded.getBookingId());
            notification.setNotificationType("SMS");
            notification.setPhoneNumber("010-1234-5678");
            notification.setNotificationStatus("send SMS paymentSucceeded");

            notificationRepository.save(notification);

            System.out.println("##### listener SendNotification : " + paymentSucceeded.toJson());
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCanceled_SendNotification(@Payload PaymentCanceled paymentCanceled){

        if(paymentCanceled.isMe()){
            Notification notification = new Notification();
            if(("").equals(paymentCanceled.getBookingId())){
                notification.setBookingId(6L);
            }
            else
                notification.setBookingId(paymentCanceled.getBookingId());
            notification.setNotificationStatus("sent SMS paymentCanceled");
            notification.setPhoneNumber("010-1234-5678");
            notification.setNotificationStatus("send SMS paymentSucceeded");

            notificationRepository.save(notification);


            System.out.println("##### listener SendNotification : " + paymentCanceled.toJson());
        }
    }

}
