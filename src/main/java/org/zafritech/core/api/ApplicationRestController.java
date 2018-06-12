/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.repositories.ApplicationRepository;

/**
 *
 * @author Luke Sibisi
 */
@RestController
public class ApplicationRestController {

    @Autowired
    private ApplicationRepository applicationRepository;
 
    @RequestMapping("/api/admin/applications/list")
    public ResponseEntity<List<Application>> getApplicationsList(Model model) {
        
        return new ResponseEntity<>(applicationRepository.findAllByOrderByApplicationTitleAsc(), HttpStatus.OK);
    }
           
}
