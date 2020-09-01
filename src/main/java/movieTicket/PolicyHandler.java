package movieTicket;

import movieTicket.config.kafka.KafkaProcessor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentSucceed_SendNotification(@Payload PaymentSucceeded paymentSucceeded){

        if(paymentSucceeded.isMe()){
            System.out.println("##### listener SendNotification : " + paymentSucceeded.toJson());
        }
    }

}
