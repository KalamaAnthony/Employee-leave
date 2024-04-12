package com.example.Success18.ImageData;

import com.example.Success18.Utilities.EntityResponse;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.PrivateKey;

@Service
@Slf4j
public class ImageDataService {
     ImageDataRepo imageDataRepo;

    public ImageDataService(ImageDataRepo imageDataRepo) {
        this.imageDataRepo = imageDataRepo;
    }
//    public EntityResponse upload(MultipartFile file){
//        EntityResponse entityResponse = new EntityResponse<>();
//        try {
//          M savedfile = imageDataRepo.save(file);
//
//
//        }catch (Exception e){
//            log.error("error {}" + e);
//        }
//    }



//    public EntityResponse upload(MultipartFile file) {
//        try {
//            EntityResponse entityResponse = new EntityResponse<>();
//            // Validate the file, if necessary
//            if (file.isEmpty()) {
//                entityResponse.setMessage("File is empty");
//                entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
//            }
//
//            // Get the file name
//            String fileName = file.getOriginalFilename();
//
//            // Set the target file path
//            Path targetPath = Path.of(uploadDirectory, fileName);
//
//            // Copy the file to the target path
//            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
//
//            // Perform additional processing or store file information in the database, if needed
//
//            entityResponse.setMessage("File uploaded successfully");
//            entityResponse.setStatusCode(HttpStatus.OK.value());
//        } catch (IOException e) {
//            // Handle the exception, log it, and return an error response
//
//        }
//        return null;
//    }
}
