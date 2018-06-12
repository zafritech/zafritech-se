/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author lukes
 */
public interface IntegrationEntityRepository extends CrudRepository<IntegrationEntity, Long> {
    
    @Override
    List<IntegrationEntity> findAll();
    
    List<IntegrationEntity> findByProjectAndHasElementsOrderBySortOrderAsc(Project project, boolean hasElements);
    
    IntegrationEntity findFirstByCompanyCode(String code);
    
    List<IntegrationEntity> findAllByOrderBySortOrderAsc();
    
    List<IntegrationEntity> findAllByOrderByCompanyCompanyCodeAsc();
    
    List<IntegrationEntity> findByHasElements(boolean hasElements);
    
    List<IntegrationEntity> findByHasElementsOrderBySortOrderAsc(boolean hasElements);
    
    IntegrationEntity findByCompanyCompanyCode(String code);
    
    IntegrationEntity findByUuId(String uuid);
}
