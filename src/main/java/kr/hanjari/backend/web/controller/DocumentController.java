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
@Tag(name = "자료실", description = "자료실 관련 API")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "[자료실] 자료실 업로드", description = """
            ## 자료실에 파일을 업로드합니다.
            ### RequestBody
            - **title**: 제목
            ### Multipart/form-data
            - **files**: 업로드하려는 파일 리스트
            """)
    @PostMapping(name = "/", consumes = {"application/json", "multipart/form-data"})
    public ApiResponse<Long> postNewDocument(@RequestPart CreateDocumentRequest requestBody,
                                             @RequestPart List<MultipartFile> files) {

        Long result = documentService.createDocument(requestBody, files);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[자료실] 자료 전체 조회", description = """
            ## 자료실에 업로드된 파일을 전부 조회합니다.
            """)
    @GetMapping("/")
    public ApiResponse<GetAllDocumentsResponse> getAllDocuments() {

        GetAllDocumentsResponse result = documentService.getAllDocuments();
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[자료실] 자료 상세 조회", description = """
            ## 자료 하나를 상세조회합니다.
            ### PathVariable
            - documentId: 조회를 희망하는 자료의 id
            """)
    @GetMapping("/{documentId}")
    public ApiResponse<GetDocumentFilesResponse> getDocumentDetail(@PathVariable Long documentId) {

        GetDocumentFilesResponse result = documentService.getDocumentFiles(documentId);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "[자료실] 자료실 수정", description = """
            ## 자료 하나를 수정합니다.
            ### RequestBody
            - **title**: 제목
            ### Multipart/form-data
            - **files**: 업로드하려는 파일 리스트
            """)
    @PatchMapping("/{documentId}")
    public ApiResponse<Void> updateDocument(@PathVariable Long documentId,
                               @RequestPart UpdateDocumentRequest request,
                               @RequestPart List<MultipartFile> files) {

        documentService.updateDocument(documentId, request, files);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "[자료실] 자료 삭제", description = """
            ## 자료실에 업로드된 자료를 하나 삭제합니다.
            ### PathVariable
            - documentId: 삭제를 희망하는 자료의 id
            """)
    @DeleteMapping("/{documentId}")
    public ApiResponse<Void> deleteDocument(@PathVariable Long documentId) {

        documentService.deleteDocument(documentId);
        return ApiResponse.onSuccess();
    }


}
