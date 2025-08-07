package com.example.sales_manager.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


@Service
public class FileService {

    private final Cloudinary cloudinary;

    private List<String> urls;

    public FileService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public List<String> handleUploadMultipleFiles(MultipartFile files[]) throws IOException {

        try {
            urls = new ArrayList<>();

            for (MultipartFile file : files) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String url = uploadResult.get("url").toString();
                urls.add(url);

            }
            return urls;
        } catch (IOException e) {
            if (urls.size() > 0) {
                for (String url : urls) {
                    handleDeleteFile(url);
                }
            }
            throw new IOException("Error uploading files");
        } catch (RuntimeException e) {
            if (urls.size() > 0) {
                for (String url : urls) {
                    handleDeleteFile(url);
                }
            }
            throw new RuntimeException("Error uploading files");
        }
    }

    public String handleUploadFile(MultipartFile file) throws IOException {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new IOException("Error uploading file");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error uploading file");
        }
    }

    public void handleDeleteFile(String imageURL) throws IOException {
        try {
            String publicId = imageURL.substring(imageURL.lastIndexOf("/") + 1, imageURL.lastIndexOf("."));
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new IOException("Error deleting file");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting file");
        }
    }

    public void handleDeleteMultipleFiles(String imageURLs[]) throws IOException {
        try {
            for (String imageURL : imageURLs) {
                String publicId = imageURL.substring(imageURL.lastIndexOf("/") + 1, imageURL.lastIndexOf("."));
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (IOException e) {
            throw new IOException("Error deleting files");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting files");
        }
    }
}
