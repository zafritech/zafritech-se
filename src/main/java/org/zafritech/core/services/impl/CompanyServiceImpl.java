/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.CompanyCreateDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.services.CompanyService;
import org.zafritech.core.services.FileIOService;

/**
 *
 * @author LukeS
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private FileIOService fileIOService;
    
    @Override
    public List<Company> findOrderById(int pageSize, int pageNumber) {
        
        List<Company> companies = new ArrayList<>();
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        
        companyRepository.findAll(request).forEach(companies::add);
        
        return companies;
    }

    @Override
    public List<Company> findOrderByCompanyName(int pageSize, int pageNumber) {
        
        List<Company> companies = new ArrayList<>();
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "companyName");
        
        companyRepository.findAll(request).forEach(companies::add);
        
        return companies;
    }
    
    @Override
    public Company createNewCompany(CompanyCreateDao dao) throws IOException, ParseException  {
                
        Company company = new Company(dao.getCompanyName(), dao.getCompanyShortName());
        
        if (!dao.getCompanyLogo().isEmpty()) {
        
            List<String> companyLogos = fileIOService.saveUploadedFiles(Arrays.asList(dao.getCompanyLogo()));
            Path logoPath = Paths.get(companyLogos.get(0));
            byte[] arrayLogo = Files.readAllBytes(logoPath);

            company.setCompanyLogo(arrayLogo);
            
            // Remove uploaded file
            File file = new File(companyLogos.get(0));
            if (file.exists()) {
                
                file.delete();
            }
        }
        
        company.setCompanyCode(dao.getCompanyCode());
        company.setCompanyRoleDescription(dao.getCompanyDescription());
        
        company = companyRepository.save(company);
        
        return company;
    }
}
