package kr.hanjari.backend.service.s3;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import jakarta.transaction.Transactional;
import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Transactional
public class S3Service {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Operations s3Operations;
    private final S3UtilService s3UtilService;

    private final FileRepository fileRepository;

    public File uploadFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {

            String fileName = file.getOriginalFilename();
            String fileKey = s3UtilService.generateFileKey(fileName);
            String fileExtension = s3UtilService.getFileExtension(fileName);

            s3Operations.upload(bucket, fileKey, inputStream,
                    ObjectMetadata.builder()
                            .contentLength(file.getSize())
                            .contentType(file.getContentType())
                            .build()
            );

            File newFile = File.builder()
                    .name(fileName)
                    .file_key(fileKey)
                    .extension(fileExtension)
                    .size(file.getSize())
                    .build();

            return fileRepository.save(newFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
