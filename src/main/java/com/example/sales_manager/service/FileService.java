package com.example.sales_manager.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
public class FileService {

    private final Cloudinary cloudinary;

    private final ObjectMapper objectMapper;

    public FileService(Cloudinary cloudinary, ObjectMapper objectMapper) {
        this.cloudinary = cloudinary;
        this.objectMapper = objectMapper;
    }

    public String uploadFile(MultipartFile files[]) throws IOException {

        String urlsImageString = "";

        for (MultipartFile file : files) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            urlsImageString += uploadResult.get("url").toString() + ",";
        }
        return urlsImageString;   
    }
}
