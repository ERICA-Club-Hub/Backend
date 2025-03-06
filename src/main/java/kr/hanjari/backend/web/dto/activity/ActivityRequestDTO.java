package kr.hanjari.backend.web.dto.activity;

import java.time.LocalDate;

public class ActivityRequestDTO {

    public static class CommonActivityDTO {
        private LocalDate date;
        private String title;
    }
}
