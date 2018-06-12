package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.zafritech.core.data.domain.Application;

public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long>{

	Application findByUuId(String uuid);
        
        List<Application> findAllByOrderByApplicationTitleAsc();
}
