/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import static java.util.Comparator.naturalOrder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.zafritech.core.data.dao.CompanyCreateDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.enums.CompanyRole;
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
}