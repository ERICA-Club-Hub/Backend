package kr.hanjari.backend.web.dto.file;

import kr.hanjari.backend.domain.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileDTO {
    private String fileName;
    private String downloadUrl;

    public static FileDTO of(String fileName, String downloadUrl) {
        return new FileDTO(fileName, downloadUrl);
    }
}
