/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.ApplicationRepository;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author Luke Sibisi
 */
@RestController
public class ApplicationRestController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserSessionService stateService;
    
    @RequestMapping("/api/admin/applications/list/all")
    public ResponseEntity<List<Application>> getAllApplicationsList(Model model) {
        
        List<Application> applications = applicationRepository.findAllByOrderByApplicationTitleAsc();
        
        if (applications != null) {
            
            return new ResponseEntity<>(applications, HttpStatus.OK);
            
        } else {
            
            return new ResponseEntity<>(applications, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping("/api/admin/applications/list")
    public ResponseEntity<List<Application>> getApplicationsList(Model model) {
        
        List<Application> applications = new ArrayList<>();
        Project project = stateService.getLastOpenProject();
        
        if (project != null) {
            
            project.getApplications().forEach(applications::add);
            return new ResponseEntity<>(applications, HttpStatus.OK);
            
        } else {
            
            return new ResponseEntity<>(applications, HttpStatus.BAD_REQUEST);
        }
    }
}
