/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.exceptions;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.zafritech.core.services.ApplicationService;

/**
 *
 * @author Luke Sibisi
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @Autowired
    private ApplicationService applicationService;

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception ex) throws Exception {
        
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            
            throw ex;
        }

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        
        String errorsPath = applicationService.getApplicationTemplateName() + "/views/core/errors/";
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.valueOf(status.toString());
        
        if(statusCode == HttpStatus.NOT_FOUND.value()) {
            
            mav.setViewName(errorsPath + "500");
            
        } else {
            
            mav.addObject("statusCode", statusCode);
            mav.addObject("msg", ex.getMessage());
            mav.setViewName(errorsPath + "global-errors");
        }
        
        return mav;
    }
}
