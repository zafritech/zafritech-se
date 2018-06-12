/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.Interface;

/**
 *
 * @author lukes
 */
public interface InterfaceRepository extends CrudRepository<Interface, Long> {
    
    @Override
    List<Interface> findAll();
    
    Interface findByUuId(String uuid);
    
    Interface findFirstByOrderByIdDesc();
    
    List<Interface> findByPrimaryElement(Element element);
    
    List<Interface> findBySecondaryElement(Element element);
            
    List<Interface> findByPrimaryElementAndInterfaceLevelIn(Element element, List<Integer> levels);
    
    List<Interface> findBySecondaryElementAndInterfaceLevelIn(Element element, List<Integer> levels);
    
    List<Interface> findByPrimaryElementOrSecondaryElement(Element primary, Element secondary);
    
    Interface findFirstByPrimaryElementAndSecondaryElement(Element primary, Element secondary);
    
    List<Interface> findByPrimaryElementAndInterfaceLevelInOrSecondaryElementAndInterfaceLevelIn(Element primary, List<Integer> levels1, Element secondary, List<Integer> levels2);
    
    Interface findFirstByPrimaryElementAndSecondaryElementAndInterfaceLevelIn(Element primary, Element secondary, List<Integer> levels);
}
