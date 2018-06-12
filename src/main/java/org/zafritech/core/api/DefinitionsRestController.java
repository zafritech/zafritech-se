/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.DocumentDefinitionDao;
import org.zafritech.core.data.domain.Definition;
import org.zafritech.core.data.repositories.DefinitionRepository;
import org.zafritech.core.enums.DefinitionTypes;
import org.zafritech.core.services.DocumentService;

/**
 *
 * @author LukeS
 */
@RestController
public class DefinitionsRestController {
    
    @Autowired
    private DefinitionRepository definitionRepository;
       
    @Autowired
    private DocumentService documentService;
     
    @RequestMapping(value = "/api/definitions/abbreviation/list", method = GET)
    public ResponseEntity<List<Definition>> listAbbreviations() {
        
        List<Definition> abbreviations = definitionRepository.findByDefinitionTypeOrderByTerm(DefinitionTypes.ABBREVIATION); 
        
        return new ResponseEntity<>(abbreviations, HttpStatus.OK);
    }
     
    @RequestMapping(value = "/api/definitions/acronym/list", method = GET)
    public ResponseEntity<List<Definition>> listAcronyms() {
        
        List<Definition> acronyms = definitionRepository.findByDefinitionTypeOrderByTerm(DefinitionTypes.ACRONYM); 
        
        return new ResponseEntity<>(acronyms, HttpStatus.OK);
    }
     
    @RequestMapping(value = "/api/definitions/definition/list", method = GET)
    public ResponseEntity<List<Definition>> listDefinitions() {
        
        List<Definition> definitions = definitionRepository.findByDefinitionTypeOrderByTerm(DefinitionTypes.DEFINITION); 
        
        return new ResponseEntity<>(definitions, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/definitions/definition/{id}", method = GET)
    public ResponseEntity<Definition> getAbbreviation(@PathVariable(value = "id") Long id) {
        
        Definition abbreviation = definitionRepository.findOne(id); 
        
        return new ResponseEntity<>(abbreviation, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/definition/add", method = RequestMethod.POST)
    public ResponseEntity<List<Definition>> documentAddDefinition(@RequestBody DocumentDefinitionDao dao) {
        
        return new ResponseEntity<>(documentService.addDefinition(dao), HttpStatus.OK);
    }
}
