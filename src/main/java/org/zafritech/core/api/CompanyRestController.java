/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Comparator.naturalOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.servlet.ModelAndView;
import org.zafritech.core.data.dao.CompanyCreateDao;
import org.zafritech.core.data.dao.CompanyUpdateDao;
import org.zafritech.core.data.dao.ContactDao;
import org.zafritech.core.data.dao.PageNavigationDao;
import org.zafritech.core.data.dao.generic.ImageItemDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.Contact;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.enums.CompanyRole;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.core.services.CommonService;
import org.zafritech.core.services.CompanyService;

/**
 *
 * @author LukeS
 */
@RestController
public class CompanyRestController {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private CompanyService companyService;
  
    @RequestMapping("/api/admin/companies/list")
    public ResponseEntity<List<Company>> getCompaniesList(Model model) {
        
        return new ResponseEntity<>(companyRepository.findAllByOrderByCompanyNameAsc(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/companies/{id}", method = GET)
    public ResponseEntity<Company> getCompanyById(@PathVariable(value = "id") Long id) {
        
        return new ResponseEntity<>(companyRepository.findOne(id), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/companies/contacts/{uuid}", method = GET)
    public ResponseEntity<Company> getCompanyByUuId(@PathVariable(value = "uuid") String uuid) {
        
        return new ResponseEntity<>(companyRepository.getByUuId(uuid), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/companies/roles/list", method = GET)
    public List<CompanyRole> getCompaniesRolesList() {
        
        List coList = Arrays.asList(CompanyRole.values()); 
        coList.sort(naturalOrder());
        
        return coList;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/admin/companies/create/new", method = RequestMethod.POST)
    public ResponseEntity<?> createNewCompany(CompanyCreateDao dao) throws IOException, ParseException {
        
        Company company = companyService.createNewCompany(dao);

        return new ResponseEntity("Successfully created reference item: " + company.getCompanyName(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/admin/companies/update/profile", method = RequestMethod.POST)
    public ResponseEntity<?> updateCompanyProfile(CompanyUpdateDao dao) throws IOException, ParseException {
        
        Company company = companyService.updateCompanyProfile(dao);

        return new ResponseEntity("Successfully created reference item: " + company.getCompanyName(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/admin/companies/contact/save/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateCompanyContact(@PathVariable(value = "id") Long id,
                                                  @RequestBody ContactDao dao) {
        
        Company company = companyRepository.findOne(id);
        Contact contact = companyService.updateCompanyContact(company, dao);
        
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/company/contact/{id}", method = GET)
    public ResponseEntity<Contact> getCompanyContacts(@PathVariable(value = "id") Long id) {
        
        Company company = companyRepository.findOne(id);
        Contact contact = company.getContact();
        
        if (contact == null) {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
                
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/admin/companies/logo/update", method = RequestMethod.POST)
    public ResponseEntity<?> addUpdateCompanyLogo(ImageItemDao imageDao) throws IOException, ParseException {
        
        if (imageDao.getImageFile().isEmpty()) {

            return new ResponseEntity("Please select a image file!", HttpStatus.OK);
        }
        
        Company company = companyService.updateCompanyLogo(imageDao);
        
        if (company != null) {
        
            return new ResponseEntity("Successfully updated logo for " + company.getCompanyName(), new HttpHeaders(), HttpStatus.OK);
        }
        
        return new ResponseEntity("Error updating company logo!", HttpStatus.OK);
    }
}