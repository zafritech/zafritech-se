/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.dao.DefinitionDao;
import org.zafritech.core.data.domain.Definition;
import org.zafritech.core.data.repositories.DefinitionRepository;
import org.zafritech.core.data.repositories.LocaleRepository;
import org.zafritech.core.enums.DefinitionDomain;
import org.zafritech.core.enums.DefinitionScope;
import org.zafritech.core.enums.DefinitionTypes;

/**
 *
 * @author LukeS
 */
@Component
public class DefinitionsInit {

    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private LocaleRepository localeRepository;
    
    @Autowired
    private DefinitionRepository definitionRepository;
        
    @Transactional
    public void init() {
            
        String dataFile = data_dir + "initialisation/definitions.json";
               
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            
            List<DefinitionDao> jsonDefinitions = Arrays.asList(mapper.readValue(new File(dataFile), DefinitionDao[].class));
            
            jsonDefinitions.forEach((_item) -> {
                
                Definition definition = new Definition(_item.getTerm(), 
                                                       _item.getDefinition(), 
                                                       DefinitionTypes.ABBREVIATION, 
                                                       localeRepository.findByCode("en_US")); 
                
                definition.setScopeEntityId(0);
                definition.setDefinitionScope(DefinitionScope.valueOf("SCOPE_" + _item.getScope().toUpperCase())); 
                definition.setApplicationDomain(DefinitionDomain.valueOf("DOMAIN_" + _item.getDomain().toUpperCase())); 
                
                definitionRepository.save(definition);
                
                String msg = "Definition data initialisation: " + definition.toString();
                Logger.getLogger(DefinitionsInit.class.getName()).log(Level.INFO, msg);
            });
            
        } catch (IOException ex) {
            
            Logger.getLogger(DefinitionsInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
