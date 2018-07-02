/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.CompanyCreateDao;
import org.zafritech.core.data.dao.CompanyUpdateDao;
import org.zafritech.core.data.dao.ContactDao;
import org.zafritech.core.data.dao.generic.ImageItemDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.Contact;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.CountryRepository;
import org.zafritech.core.services.CompanyService;
import org.zafritech.core.services.FileIOService;

/**
 *
 * @author LukeS
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Value("${zafritech.paths.static-resources}")
    private String static_resources;

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private FileIOService fileIOService;
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
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
    public Company createNewCompany(CompanyCreateDao dao) throws IOException, ParseException {
                
        String images_dir = static_resources + "images/";
        
        Company company = new Company(dao.getCompanyName(), dao.getCompanyShortName());
        
        if (!dao.getCompanyLogo().isEmpty()) {
        
            // Upload and move Reference Image file
            String imageFileExtension = FilenameUtils.getExtension(dao.getCompanyLogo().getOriginalFilename());
            String imageRelPath = "companies/logo_" + company.getUuId() + "." + imageFileExtension;
            String imageFullPath = images_dir + imageRelPath;
            List<String> imageFiles = fileIOService.saveUploadedFiles(Arrays.asList(dao.getCompanyLogo()));
            FileUtils.moveFile(FileUtils.getFile(imageFiles.get(0)), FileUtils.getFile(imageFullPath)); 

            company.setLogoPath(imageRelPath);
            
            // Remove uploaded file
            File file = new File(imageFiles.get(0));
            if (file.exists()) {
                
                file.delete();
            }
        }
        
        company.setCompanyCode(dao.getCompanyCode());
        company.setCompanyRoleDescription(dao.getCompanyDescription());
        
        company = companyRepository.save(company);
        
        return company;
    }

    @Override
    public Company updateCompanyProfile(CompanyUpdateDao dao) {
        
       Company company = companyRepository.findOne(dao.getCompanyId());
       
       company.setCompanyName(dao.getCompanyName());
       company.setCompanyShortName(dao.getCompanyShortName());
       company.setCompanyCode(company.getCompanyCode());
       company.setCompanyRoleDescription(dao.getCompanyDescription()); 
       
       company = companyRepository.save(company);
       
       return company; 
    }
    
    @Override
    public Company updateCompanyLogo(ImageItemDao dao) throws IOException, ParseException {
          
        String images_dir = static_resources + "images/";
        
        if (!dao.getImageFile().isEmpty()) {
        
            Company company = companyRepository.findOne(dao.getItemId());
            
            // Already has a logo set
            if (company.getLogoPath() != null) {
                
                // Remove uploaded file
                File file = new File(images_dir + company.getLogoPath());
                if (file.exists()) {

                    file.delete();
                }
            }
            
            // Upload and move Reference Image file
            String imageFileExtension = FilenameUtils.getExtension(dao.getImageFile().getOriginalFilename());
            String imageRelPath = "companies/logo_" + company.getUuId() + "." + imageFileExtension;
            String imageFullPath = images_dir + imageRelPath;
            List<String> imageFiles = fileIOService.saveUploadedFiles(Arrays.asList(dao.getImageFile()));
            FileUtils.moveFile(FileUtils.getFile(imageFiles.get(0)), FileUtils.getFile(imageFullPath)); 
            
            company.setLogoPath(imageRelPath);
            companyRepository.save(company);
        
            return company;
            
        } else {
            
            return null;
        }
    }
    
    @Override
    public Contact updateCompanyContact(Company company, ContactDao dao) {
        
        Contact contact = company.getContact();
        
        if (contact == null) {
            
            contact = new Contact(dao.getEmail(), countryRepository.findByIso3(dao.getCountry())); 
        }
        
        contact.setFirstName("Information");
        contact.setLastName("");
        contact.setAddress(dao.getAddress());
        contact.setPhone(dao.getPhone());
        contact.setMobile(dao.getMobile());
        contact.setEmail(dao.getEmail());
        contact.setWebsite(dao.getWebsite());
        contact.setCountry(countryRepository.findByIso3(dao.getCountry()));
        contact.setState(dao.getState());
        contact.setCity(dao.getCity());
        contact.setPostCode(dao.getPostCode()); 
        contact = contactRepository.save(contact);
        
        company.setContact(contact);
        companyRepository.save(company);
        
        return contact;
    }
}
