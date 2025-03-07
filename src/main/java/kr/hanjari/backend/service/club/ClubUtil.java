package kr.hanjari.backend.service.club;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Str;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubUtil {

    @Value("spring.mail.username")
    private String serviceEmail;


    private final ClubRepository clubRepository;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 코드에 사용할 문자 집합
    private static final String SUBJECT = "[한자리] 동아리 신청 승인 결과 안내드립니다. ";
    private static final int CODE_LENGTH = 6; // 코드 길이

    private final Random random = new SecureRandom();

    public String reissueClubCode(Long clubId) {
        String code;
        boolean isUnique;

        do {
            code = generateRandomCode();
            isUnique = !clubRepository.existsByCode(code);
        } while (!isUnique);

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        club.updateCode(code);

        return code;
    }

    public void sendEmail(
            String email, String clubName, String verificationCode, String loginUrl) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(serviceEmail);
            helper.setTo(email);
            helper.setSubject(SUBJECT);


            String htmlContent = createApproveContent(clubName, verificationCode, loginUrl);

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



    private String generateRandomCode() {
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            codeBuilder.append(CHAR_POOL.charAt(index));
        }

        return codeBuilder.toString();
    }



}
