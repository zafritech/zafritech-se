package org.zafritech.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.SiteTemplate;
import org.zafritech.core.data.repositories.SiteTemplateRepository;
import org.zafritech.core.services.ApplicationService;

/**
 *
 * @author LukeS
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private SiteTemplateRepository siteTemplateRepository;
    
	@Override
	public String getApplicationTemplateName() {
		
		SiteTemplate template = siteTemplateRepository.findFirstByActive(true);
		
                if (template == null) {
                    
                    return "pixeladmin";
                }
                
		return template.getTemplateName();
	}

}
