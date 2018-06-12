/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.initializr;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.applications.integration.data.domain.InterfaceType;
import org.zafritech.applications.integration.data.repositories.InterfaceTypeRepository;

/**
 *
 * @author lukes
 */
@Component
public class InterfaceTypesInit {
 
    @Autowired
    private InterfaceTypeRepository interfaceTypeRepository;
   
    @Transactional
    public void init() {
        
        List<InterfaceType> types = new ArrayList<>();
        
        types.add(new InterfaceType("P", "Physical"));
        types.add(new InterfaceType("F", "Functional"));
        types.add(new InterfaceType("M", "Mechanical"));
        types.add(new InterfaceType("E", "Electical"));
        types.add(new InterfaceType("D", "Data"));
        types.add(new InterfaceType("S", "RAMS"));
        types.add(new InterfaceType("G", "Electromagnetic"));
        
        types.forEach((type) -> {
            
            interfaceTypeRepository.save(type);
        });
    }  
}
