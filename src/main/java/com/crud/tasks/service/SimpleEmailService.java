package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleEmailService {
    private final JavaMailSender javaMailSender;
    private final MailCreatorService mailCreatorService;

    public void send(final Mail mail) {
        try {
            log.info("Starting email preparation...");
            javaMailSender.send(createMimeMessage(mail));
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }

    public void sendSecondEmail(final Mail mail) {
        try {
            log.info("Email in preparation to send");
            javaMailSender.send(createMimeMessageForSecondMail(mail));
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }

    public void sendNormalMail(final Mail mail) {
        try {
            log.info("Starting email preparation...");
            SimpleMailMessage mailMessage = createMailMessage(mail);
            javaMailSender.send(mailMessage);
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }

    private MimeMessageHelper prepareMimeMessageHelper(MimeMessage mimeMessage, Mail mail) throws MessagingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setTo(mail.getMailTo());
        messageHelper.setSubject(mail.getSubject());
        return messageHelper;
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = prepareMimeMessageHelper(mimeMessage, mail);
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private MimeMessagePreparator createMimeMessageForSecondMail(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = prepareMimeMessageHelper(mimeMessage, mail);
            messageHelper.setText(mailCreatorService.buildSecondTrelloCardEmail(mail.getMessage()), true);
        };
    }


    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        Optional<String> toCc = Optional.ofNullable(mail.getToCc());
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (toCc.isPresent()) {
            mailMessage.setCc(mail.getToCc());
        }
        return mailMessage;
    }

}
