package kr.hanjari.backend.domain.club.application.query;

public interface MailSender {

    void sendEmail(String email, String clubName, String verificationCode, String loginUrl);

    void sendRecruitmentOpenEmail(String email, String clubName, String clubDetailUrl);
}
