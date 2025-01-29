package kr.hanjari.backend.web.dto.club.request;

public record CommonClubDTO(String clubName, String leaderEmail, String category, String oneLiner, String briefIntroduction) {
    public static CommonClubDTO of(String clubName, String leaderEmail, String category, String oneLiner, String briefIntroduction) {
        return new CommonClubDTO(clubName, leaderEmail, category, oneLiner, briefIntroduction);
    }
}
