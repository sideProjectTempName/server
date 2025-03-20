package com.tripplannerai.service;

import com.tripplannerai.dto.request.EmailCertificationRequest;
import com.tripplannerai.provider.EmailProvider;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AsyncService {
    private final EmailProvider emailProvider;

    @Async("customAsyncExecutor")
    public void asyncTask(EmailCertificationRequest emailCertificationRequest){
        try {
            emailCertification(emailCertificationRequest);
        }catch (MessagingException e){

            log.error(e.getMessage(),e);
            //failService.send(emailCertificationRequest.getClientId());
        }
    }

    private void emailCertification(EmailCertificationRequest emailCertificationRequest) throws MessagingException {
        emailProvider.sendCertificationMail(emailCertificationRequest.getEmail(), certificationNumber());
    }

    private String certificationNumber(){
        int number = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(number);
    }

}
