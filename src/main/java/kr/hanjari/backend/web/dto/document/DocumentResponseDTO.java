package kr.hanjari.backend.web.dto.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class DocumentResponseDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class GetAllDocuments {
        private List<DocumentDTO> documentDTOs;

        public static GetAllDocuments of(List<DocumentDTO> documentDTOs) {
            return new GetAllDocuments(documentDTOs);
        }
    }
}
