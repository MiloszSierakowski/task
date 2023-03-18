package com.crud.tasks.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

//    @Test
//    void shouldSendEmail() {
//
//        Mail mail = Mail.builder()
//                .mailTo("test@test.com")
//                .subject("Test")
//                .message("Test Message")
//                .toCc("Milosz@test.com")
//                .build();
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(mail.getMailTo());
//        mailMessage.setSubject(mail.getSubject());
//        mailMessage.setText(mail.getMessage());
//        mailMessage.setCc(mail.getToCc());
//
//        simpleEmailService.send(mail);
//
//        verify(javaMailSender, times(1)).send(mailMessage);
//
//    }
}