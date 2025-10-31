package kr.hanjari.backend.domain.serviceAnnouncement.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for creating a service announcement")
public record CreateServiceAnnouncementRequestDTO(
        @NotBlank(message = "Title is required.")
        @Schema(description = "Announcement title", nullable = false, example = "Server Maintenance Notice", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @NotBlank(message = "Content is required.")
        @Schema(description = "Announcement content", nullable = false, example = "There will be a server maintenance from midnight.", requiredMode = Schema.RequiredMode.REQUIRED)
        String content
) {
}
