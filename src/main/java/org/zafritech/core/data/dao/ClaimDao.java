/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

/**
 *
 * @author LukeS
 */
public class ClaimDao {
    
    private Long claimId;
            
    private String userUuId;
    
    private String userClaimType;
    
    private Long userClaimValue;
    
    private String userClaimStringValue;

    public ClaimDao() {
        
    }

    public ClaimDao(String userUuId, String userClaimType, Long userClaimValue) {
        
        this.userUuId = userUuId;
        this.userClaimType = userClaimType;
        this.userClaimValue = userClaimValue;
    }

    @Override
    public String toString() {
        
        return "ClaimDao{" + "userUuId=" + userUuId + 
                ", userClaimType=" + userClaimType + 
                ", userClaimValue=" + userClaimValue + '}';
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }
    
    public String getUserUuId() {
        return userUuId;
    }

    public void setUserUuId(String userUuId) {
        this.userUuId = userUuId;
    }

    public String getUserClaimType() {
        return userClaimType;
    }

    public void setUserClaimType(String userClaimType) {
        this.userClaimType = userClaimType;
    }

    public Long getUserClaimValue() {
        return userClaimValue;
    }

    public void setUserClaimValue(Long userClaimValue) {
        this.userClaimValue = userClaimValue;
    }

    public String getUserClaimStringValue() {
        return userClaimStringValue;
    }

    public void setUserClaimStringValue(String userClaimStringValue) {
        this.userClaimStringValue = userClaimStringValue;
    }
}
