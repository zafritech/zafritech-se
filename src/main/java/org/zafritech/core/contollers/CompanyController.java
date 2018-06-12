/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.Country;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.CountryRepository;
import org.zafritech.core.services.ApplicationService;

/**
 *
 * @author LukeS
 */
@Controller
public class CompanyController {
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private CountryRepository countryRepository;
	
    @RequestMapping(value = {"/companies", "/company/list"})
    public String getCompaniesList(Model model) {
        
        List<Company> companies = companyRepository.findAllByOrderByCompanyNameAsc();
        
        model.addAttribute("companies", companies);
        
        return applicationService.getApplicationTemplateName() + "/views/core/companies/index";
    }
    
    @RequestMapping("/companies/{uuid}")
    public String getCompany(@PathVariable String uuid, Model model) {
        
        Company company = companyRepository.getByUuId(uuid);
        List<Country> countries = countryRepository.findAllByOrderByCountryNameAsc();
        
        model.addAttribute("company", company);
        model.addAttribute("countries", countries);
        
        return applicationService.getApplicationTemplateName() + "/views/core/companies/company";
    }
}
