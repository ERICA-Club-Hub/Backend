package kr.hanjari.backend.service.file;

import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.domain.vo.S3UploadResultDTO;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.FileRepository;
import kr.hanjari.backend.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    private final S3Service s3Service;

    public Long uploadObjectAndSaveFile(MultipartFile file) {

        S3UploadResultDTO uploadResult = s3Service.uploadObject(file);
        File fileToSave = uploadResult.toFile();

        fileRepository.save(fileToSave);
        return fileToSave.getId();

    }

    public void deleteObjectAndFile(Long fileId) {

        File fileToDelete = fileRepository.findById(fileId)
                        .orElseThrow(() -> new GeneralException(ErrorStatus._FILE_NOT_FOUND));

        s3Service.deleteObject(fileToDelete.getFileKey());
        fileRepository.deleteById(fileId);
    }
}
