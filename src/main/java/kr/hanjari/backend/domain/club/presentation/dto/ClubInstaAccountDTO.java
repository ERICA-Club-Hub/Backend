package kr.hanjari.backend.domain.club.presentation.dto;

public record ClubInstaAccountDTO(
        String clubName,
        String accountName,
        String profileImage,
        String instagramProfileUrl
) {
    public static ClubInstaAccountDTO of(String clubName, String accountName, String profileImage, String instagramProfileUrl) {
        return new ClubInstaAccountDTO(clubName, accountName, profileImage, instagramProfileUrl);
    }
}
