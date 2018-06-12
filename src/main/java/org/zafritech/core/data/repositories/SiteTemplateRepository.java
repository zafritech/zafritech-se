package org.zafritech.core.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.SiteTemplate;	

public interface SiteTemplateRepository  extends CrudRepository<SiteTemplate, Long>  {

	SiteTemplate findFirstByActive(boolean isActive);
}
