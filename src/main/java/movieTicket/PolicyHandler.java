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

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverInsertedPromotion_SendNotification(@Payload InsertedPromotion insertedPromotion) {

        if (insertedPromotion.isMe()) {
            Notification notification = new Notification();
            if (("").equals(insertedPromotion.getPromotionId())) {
                notification.setPromotionId(99999L);  // configMap에서 defulatId 가져오도록 가능한지 확인.
                notification.setNote("promotionId를 확인하세요");
            } else {
                notification.setBookingId(0L); // 그냥 알림 등록에 대한 공지 이기 때문에 bookingID는 없음
                notification.setNotificationStatus("sent SMS promotionInserted");
                notification.setPhoneNumber("010-1234-5678");
                notification.setNote(insertedPromotion.getNote());

                notificationRepository.save(notification);
                System.out.println("##### listener SendNotification : " + insertedPromotion.toJson());
            }
        }
    }

}
