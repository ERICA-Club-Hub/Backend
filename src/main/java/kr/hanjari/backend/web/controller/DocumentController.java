package kr.hanjari.backend.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @PostMapping("/")
    public void postNewDocument() {
        return;
    }

    @GetMapping("/")
    public void getAllDocuments() {
        return;
    }

    @GetMapping("/{documentId}")
    public void getDocumentDetail() {
        return;
    }

    @PatchMapping("/{documentId}")
    public void updateDocument() {
        return;
    }

    @DeleteMapping("/{documentId}")
    public void deleteDocument() {
        return;
    }


}
