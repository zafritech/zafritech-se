/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao.generic;

/**
 *
 * @author Luke Sibisi
 */
public class TriValueDao {
    
    private Long Id;
    
    private String itemName;
    
    private String itemDescription;

    public TriValueDao() {
        
    }

    public TriValueDao(Long Id, String itemName, String itemDescription) {
        
        this.Id = Id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    @Override
    public String toString() {
        
        return "TriValueDao{" + "Id=" + getId() + ", itemName=" + getItemName() + ", itemDescription=" + getItemDescription() + '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
