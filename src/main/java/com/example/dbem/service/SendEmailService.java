package com.example.dbem.service;

import com.example.dbem.entity.SendEmail;
import com.example.dbem.exception.custom.email.SendEmailInternalServerError;
import com.example.dbem.exception.custom.email.SendEmailNotFoundException;
import com.example.dbem.repository.SendEmailRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SendEmailService {
    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.email}")
    private String fromEmail;

    private final SendEmailRepository sendEmailRepository;

    public void sendEmailVerification(String toEmail) {
        String token = UUID.randomUUID().toString();

        Email from = new Email(fromEmail);
        String subject = "DBEM 회원가입 이메일 인증 요청";
        Email to = new Email(toEmail);
        String contentText = "아래 링크를 클릭해 이메일 인증을 완료하세요:\n" +
                "http://localhost:8080/api/send/verify?token=" + token;
        Content content = new Content("text/plain", contentText);

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("SendGrid Response Status Code: " + response.getStatusCode());

            Optional<SendEmail> email = this.sendEmailRepository.findByEmail(toEmail);

            if (email.isEmpty()) {
                this.sendEmailRepository.save(
                        SendEmail.builder()
                                .email(toEmail)
                                .token(token)
                                .verification(false)
                                .build()
                );
            } else {
                SendEmail updateEmail = email.get();
                updateEmail.update(token);
                this.sendEmailRepository.save(updateEmail);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), "\n메일 전송 중에 오류가 발생했습니다.");
            throw new SendEmailInternalServerError("메일 전송 중에 오류가 발생했습니다.");
        }
    }

    public boolean getEmailVerification(String findEmail) {
        SendEmail sendEmail = this.sendEmailRepository.findByEmail(findEmail)
                .orElseThrow(() -> new SendEmailNotFoundException("해당 기록을 찾을 수 없습니다."));
        return sendEmail.isVerification();
    }

    public void verifyEmailVerification(String token) {
        SendEmail sendEmail = this.sendEmailRepository.findByToken(token)
                .orElseThrow(() -> new SendEmailNotFoundException("해당 기록을 찾을 수 없습니다."));
        sendEmail.verify();
        this.sendEmailRepository.save(sendEmail);
    }
}
