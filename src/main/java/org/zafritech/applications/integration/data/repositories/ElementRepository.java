/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;

/**
 *
 * @author lukes
 */
public interface ElementRepository extends CrudRepository<Element, Long> {
    
    Element findByUuId(String uuid);
    
    Element findBySbs(String sbs);
    
    @Override
    List<Element> findAll();
    
    List<Element> findByEntityOrderBySortOrder(IntegrationEntity entity);
    
    List<Element> findByParent(Element parent);
    
    List<Element> findByParentOrderBySortOrder(Element parent);
    
    List<Element> findByEntityAndParentOrderBySortOrder(IntegrationEntity entity, Element parent);
}
