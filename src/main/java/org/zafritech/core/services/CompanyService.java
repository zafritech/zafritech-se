/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.zafritech.core.data.dao.CompanyCreateDao;
import org.zafritech.core.data.dao.CompanyUpdateDao;
import org.zafritech.core.data.dao.ContactDao;
import org.zafritech.core.data.dao.generic.ImageItemDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.Contact;

/**
 *
 * @author LukeS
 */
public interface CompanyService {
    
    List<Company> findOrderById(int pageSize, int pageNumber);
    
    List<Company> findOrderByCompanyName(int pageSize, int pageNumber);
    
    Company createNewCompany(CompanyCreateDao dao) throws IOException, ParseException;
    
    Company updateCompanyProfile(CompanyUpdateDao dao);
    
    Company updateCompanyLogo(ImageItemDao dao) throws IOException, ParseException;
    
    Contact updateCompanyContact(Company company, ContactDao dao);
}
