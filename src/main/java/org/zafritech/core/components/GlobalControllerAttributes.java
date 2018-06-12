/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.zafritech.core.data.dao.GlobalSessionDao;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author Luke Sibisi
 */
@ControllerAdvice(annotations = Controller.class)
public class GlobalControllerAttributes {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionService userSessionService;

    @ModelAttribute
    public void globalAttributes(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String name = userDetails.getUsername();

            User user = userRepository.findByUserName(name);

            GlobalSessionDao userSession = new GlobalSessionDao();
            
            if (user != null) {
                
                userSession.setFirstName(user.getFirstName());
                userSession.setLastName(user.getLastName());
                userSession.setEmail(user.getEmail());

                Project openProject = userSessionService.getLastOpenProject();
                if (openProject != null) {

                    userSession.setHasOpenProject(true);
                    userSession.setProjectUuId(openProject.getUuId());

                } else {

                    userSession.setHasOpenProject(false);
                    userSession.setProjectUuId(null);
                }
            } else {
                
                userSession.setHasOpenProject(false);
                userSession.setProjectUuId(null);
            }

            model.addAttribute("usersession", userSession);
        }
    }
}
