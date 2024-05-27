package com.example.alumni.feature.fogot_password;


import com.example.alumni.feature.fogot_password.dto.MailBody;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendOtpMessage(MailBody mailBody, String otp) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("name", mailBody.to());
            context.setVariable("otp", otp);
            context.setVariable("logo", "resources/static/images/Alumni.jpg");

            String html = templateEngine.process("otpEmailTemplate", context);

            helper.setTo(mailBody.to());
            helper.setText(html, true);
            helper.setSubject(mailBody.subject());
            helper.setFrom("vannraruos@gmail.com"); // replace with your email

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

}
