package kr.hanjari.backend.service.s3;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class S3UtilService {

    public String generateFileKey(String fileName) {
        return UUID.randomUUID() + fileName;
    }

    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) return "";
        else return fileName.substring(dotIndex + 1);
    }

}
