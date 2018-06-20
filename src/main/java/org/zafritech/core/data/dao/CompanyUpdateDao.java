/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LukeS
 */
public class CompanyUpdateDao {
   
    private Long companyId;
    private String companyName;
    private String companyShortName;
    private String companyCode;
    private String companyDescription;
    private MultipartFile companyLogo;

    public CompanyUpdateDao() {
        
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public MultipartFile getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(MultipartFile companyLogo) {
        this.companyLogo = companyLogo;
    }
}
