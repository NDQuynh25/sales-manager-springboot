package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.service.FileService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Object>> uploadFile(@RequestParam("files") MultipartFile files[]) throws Exception {

        ApiResponse<Object> response = new ApiResponse<>();
        System.out.println("files: " + files.length);
        try {
            List<String> urls = fileService.handleUploadMultipleFiles(files);

            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Upload file successfully");
            response.setData(urls);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error uploading file");
            response.setData(null);
            return ResponseEntity.status(500).body(response);

        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Object>> deleteFile(@RequestParam("imageURLs") String imageURLs[]) throws Exception {

        ApiResponse<Object> response = new ApiResponse<>();

        try {
            fileService.handleDeleteMultipleFiles(imageURLs);

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Delete file successfully");
            response.setData(true);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error deleting file");
            response.setData(null);
            return ResponseEntity.status(500).body(response);

        }
    }

}
