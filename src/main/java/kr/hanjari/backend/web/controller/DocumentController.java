package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import kr.hanjari.backend.service.document.DocumentService;
import kr.hanjari.backend.web.dto.document.request.CreateDocumentRequest;
import kr.hanjari.backend.web.dto.document.request.UpdateDocumentRequest;
import kr.hanjari.backend.web.dto.document.response.GetAllDocumentsResponse;
import kr.hanjari.backend.web.dto.document.response.GetDocumentFilesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Tag(name = "📂 자료실", description = "자료실 관련 API")
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "[자료실] 자료 업로드", description = """
        ## 자료실에 파일을 업로드합니다.

        ### Request
        #### requestBody (JSON)
        - **title**: 제목

        #### files (multipart/form-data 리스트)
        - **업로드할 파일 리스트**

        ### Response
        - **생성된 document의 ID**
        """
    )
    @PostMapping(name = "/", consumes = {"application/json", "multipart/form-data"})
    public ApiResponse<Long> postNewDocument(@RequestPart(name = "requestBody") CreateDocumentRequest requestBody,
                                             @RequestPart(name = "files") List<MultipartFile> files) {

        Long result = documentService.createDocument(requestBody, files);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[자료실] 자료 전체 조회", description = """
        ## 자료실에 업로드된 파일을 전체 조회합니다.

        ### Response
        #### documentDTOList
        - **documentId**: 자료 ID
        - **title**: 자료 제목
        """
    )
    @GetMapping("")
    public ApiResponse<GetAllDocumentsResponse> getAllDocuments() {

        GetAllDocumentsResponse result = documentService.getAllDocuments();
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[자료실] 자료 파일 조회", description = """
        ## 특정 자료의 파일을 조회합니다.

        ### PathVariable
        #### documentId: 조회할 document의 ID

        ### Response
        #### fileDTOList
        - **fileDTO** 객체들의 리스트
        
        #### fileDTO
        - **fileName**: 파일 이름
        - **downloadUrl**: 다운로드 URL
        """
    )
    @GetMapping("/{documentId}")
    public ApiResponse<GetDocumentFilesResponse> getDocumentFiles(@PathVariable Long documentId) {

        GetDocumentFilesResponse result = documentService.getDocumentFiles(documentId);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[자료실] 자료 수정", description = """
        ## 자료를 수정합니다.
        
        ### PathVariable
        #### documentId: 수정할 document의 ID

        ### Request
        #### requestBody (JSON)
        - **title**: 수정할 제목
        - **removedFileIDList**: 삭제할 파일 ID 리스트

        #### files (multipart/form-data 리스트)
        - **새롭게 추가할 파일 리스트(필수 x, 있는 경우에만 입력)**
        """
    )
    @PatchMapping("/{documentId}")
    public ApiResponse<Void> updateDocument(@PathVariable Long documentId,
                                            @RequestPart(name = "requestBody") UpdateDocumentRequest requestBody,
                                            @RequestPart(name = "files", required = false) List<MultipartFile> files) {

        documentService.updateDocument(documentId, requestBody, files);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "[자료실] 자료 삭제", description = """
        ## 자료를 삭제합니다.

        ### PathVariable
        #### documentId: 삭제할 document의 ID
        """
    )
    @DeleteMapping("/{documentId}")
    public ApiResponse<Void> deleteDocument(@PathVariable Long documentId) {

        documentService.deleteDocument(documentId);
        return ApiResponse.onSuccess();
    }
}

