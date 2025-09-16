package com.example.lp.security.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageHandler {
    private String baseDir;

    public ImageHandler(@Value("${image.base-dir}") String baseDir) {
            this.baseDir = baseDir;
    }
    public String saveProductImage(String productName, MultipartFile image) {
        String fileName = getOriginName(image);
        File dir = new File(baseDir, productName);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("폴더 생성 실패: " + dir.getAbsolutePath());
        }
        String fullPathName = baseDir + productName + "/" + fileName;
        try {
            image.transferTo(new File(fullPathName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File skuDir = new File(baseDir, productName + "/" + "productSku");
        if (!skuDir.exists() && !skuDir.mkdirs()) {
            throw new RuntimeException("SKU 폴더 생성 실패: " + skuDir.getAbsolutePath());
        }
        return fullPathName;
    }

    public String saveSkuImage(String productName,MultipartFile image) {
        String fileName = getOriginName(image);
        String fullPathName = baseDir + productName + "/productSku/" + fileName;
        try {
            image.transferTo(new File(fullPathName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fullPathName;
    }

    public String saveEventImage(String eventName, MultipartFile image) {
        String fileName = getOriginName(image);
        File dir = new File(baseDir, eventName);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("폴더 생성 실패: " + dir.getAbsolutePath());
        }
        String fullPathName = baseDir + eventName + "/" + fileName;
        try {
            image.transferTo(new File(fullPathName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fullPathName;
    }

    private String getOriginName(MultipartFile image){
        return image.getOriginalFilename();
    }
}
