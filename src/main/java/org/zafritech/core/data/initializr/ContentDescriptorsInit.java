/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.DocumentContentDescriptor;
import org.zafritech.core.data.repositories.DocumentContentDescriptorRepository;

/**
 *
 * @author LukeS
 */
@Component
public class ContentDescriptorsInit {
  
    @Autowired
    private DocumentContentDescriptorRepository descriptorRepository;
    
    @Transactional
    public void init() {

        descriptorRepository.save(new DocumentContentDescriptor("Generic Information", "CONTENT_TYPE_GENERIC", "requirements", "Generic textual information"));
        descriptorRepository.save(new DocumentContentDescriptor("Project Information", "CONTENT_TYPE_PROJECT", "requirements", "Project detail information"));
        descriptorRepository.save(new DocumentContentDescriptor("Tasks", "CONTENT_TYPE_TASKS", "requirements", "Task list information"));
        descriptorRepository.save(new DocumentContentDescriptor("Requirements", "CONTENT_TYPE_REQUIREMENTS", "requirements", "Requirements specification information"));
        descriptorRepository.save(new DocumentContentDescriptor("Requirements Link", "CONTENT_TYPE_LINKS", "requirements", "Requirements links information"));
    }
}
