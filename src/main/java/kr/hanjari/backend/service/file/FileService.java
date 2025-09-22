package kr.hanjari.backend.service.file;

import kr.hanjari.backend.domain.File;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.FileRepository;
import kr.hanjari.backend.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    private final S3Service s3Service;

    public void deleteFileAndObject(Long fileId) {

        File fileToDelete = fileRepository.findById(fileId)
                        .orElseThrow(() -> new GeneralException(ErrorStatus._FILE_NOT_FOUND));

        s3Service.deleteObject(fileToDelete.getFileKey());
        fileRepository.deleteById(fileId);
    }
}
