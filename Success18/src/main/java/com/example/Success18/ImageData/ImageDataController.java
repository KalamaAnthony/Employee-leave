package com.example.Success18.ImageData;

import com.example.Success18.Utilities.EntityResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping ("/api/v1/imageData")
public class ImageDataController {
     ImageDataService imageDataService;

    public ImageDataController(ImageDataService imageDataService) {
        this.imageDataService = imageDataService;
    }
//    @PostMapping("/upload")
//
//    public EntityResponse upload(@RequestParam("file")MultipartFile file){
//        return imageDataService.upload(file);
//    }
}
