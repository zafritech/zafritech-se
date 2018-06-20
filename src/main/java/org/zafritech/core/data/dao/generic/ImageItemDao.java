/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao.generic;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LukeS
 */
public class ImageItemDao {
    
    private Long itemId;
    private MultipartFile imageFile;

    public ImageItemDao() {
        
    }

    public ImageItemDao(Long itemId, MultipartFile imageFile) {
        
        this.itemId = itemId;
        this.imageFile = imageFile;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
