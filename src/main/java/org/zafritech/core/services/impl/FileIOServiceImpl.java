/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zafritech.core.services.FileIOService;

/**
 *
 * @author LukeS
 */
@Service
public class FileIOServiceImpl implements FileIOService {
    
    @Override
    public String getUploadDirectory() {
        
        String path = new File(System.getProperty("user.dir") + "/uploads").getAbsolutePath();
        
        File file = new File(path);
        
        if (!file.exists()) {
            
            if (!file.mkdir()) {
                
                return null;
            }
        }
        
        return file.getAbsolutePath();
    }
    
    @Override
    public List<String> saveUploadedFiles(List<MultipartFile> files) throws IOException {
        
        String uploadPath = getUploadDirectory();
        List<String> filePaths = new ArrayList<String>();
        
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                
                continue; //next pls
            }

            String filePath = uploadPath + File.separator + file.getOriginalFilename();
            
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
            
            filePaths.add(filePath);
        }
        
        return filePaths;
    }

    @Override
    public boolean convertImageFormat(String inputImagePath, String outputImagePath, String formatName) throws IOException {
        
        FileInputStream inputStream = new FileInputStream(inputImagePath);
        FileOutputStream outputStream = new FileOutputStream(outputImagePath);
        
        BufferedImage inputImage = ImageIO.read(inputStream);
        boolean result = ImageIO.write(inputImage, formatName, outputStream);
        
        outputStream.close();
        inputStream.close();
         
        return result;
    }
}
