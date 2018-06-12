/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.services.ApplicationService;

/**
 *
 * @author Luke Sibisi
 */
@Controller
public class CustomErrorController implements ErrorController {
    
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        
        String errorsPath = applicationService.getApplicationTemplateName() + "/views/core/errors/";
        
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
        
            Integer statusCode = Integer.valueOf(status.toString());

                if(statusCode == HttpStatus.NOT_FOUND.value()) {

                    return errorsPath + "404";
                }
                else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {

                    return errorsPath + "500";
                }
            }
        
        return errorsPath + "global-errors";
    }
    
    @Override
    public String getErrorPath() {
        
        return "/error";
    }
}
