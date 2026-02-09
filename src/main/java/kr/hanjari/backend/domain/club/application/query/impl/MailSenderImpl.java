package kr.hanjari.backend.domain.club.application.query.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.hanjari.backend.domain.club.application.query.MailSender;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
@Transactional
public class MailSenderImpl implements MailSender {

    @Value("${spring.mail.username}")
    private String serviceEmail;

    private static final String APPROVE_SUBJECT = "[한자리] 동아리 신청 승인 결과 안내드립니다.";
    private static final String RECRUITMENT_OPEN_SUBJECT_FORMAT = "[한자리] %s 동아리 모집이 시작되었습니다.";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendEmail(String email, String clubName, String verificationCode, String loginUrl) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(serviceEmail);
            helper.setTo(email);
            helper.setSubject(APPROVE_SUBJECT);

            String htmlContent = createApproveContent(clubName, verificationCode, loginUrl);

            helper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new GeneralException(ErrorStatus._FAIL_TO_SEND_EMAIL);
        }
    }

    @Override
    public void sendRecruitmentOpenEmail(String email, String clubName, String clubDetailUrl) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(serviceEmail);
            helper.setTo(email);
            helper.setSubject(RECRUITMENT_OPEN_SUBJECT_FORMAT.formatted(clubName));

            String htmlContent = createRecruitmentOpenContent(clubName, clubDetailUrl);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new GeneralException(ErrorStatus._FAIL_TO_SEND_EMAIL);
        }
    }

    private String createApproveContent(
            String clubName, String verificationCode, String loginUrl) {

        Context context = new Context();
        context.setVariable("clubName", clubName);
        context.setVariable("verificationCode", verificationCode);
        context.setVariable("loginUrl", loginUrl);

        return templateEngine.process("success", context);
    }

    private String createRecruitmentOpenContent(String clubName, String clubDetailUrl) {
        Context context = new Context();
        context.setVariable("clubName", clubName);
        context.setVariable("clubDetailUrl", clubDetailUrl);
        return templateEngine.process("recruitment-open", context);
    }
}
