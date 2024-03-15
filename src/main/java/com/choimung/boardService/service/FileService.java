package com.choimung.boardService.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    public String getPullPath(String fileName) {
        return fileDir + fileName;
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = getStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getPullPath(storeFileName)));

        return storeFileName;
    }

    private String getStoreFileName(String originalFilename) {
        return UUID.randomUUID() + "." + extractedExt(originalFilename);
    }

    private String extractedExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
