/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.enums.ClaimRelations;

/**
 *
 * @author LukeS
 */
@Component
public class ClaimTypesInit {
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
     
    @Transactional
    public void init() {

        claimTypeRepository.save(new ClaimType("PROJECT_OWNER", "PROJECT", ClaimRelations.OWNER, "Project Owner"));
        claimTypeRepository.save(new ClaimType("PROJECT_MANAGER", "PROJECT", ClaimRelations.MANAGER, "Project Manager"));
        claimTypeRepository.save(new ClaimType("PROJECT_MEMBER", "PROJECT", ClaimRelations.MEMBER, "Project Member"));
        claimTypeRepository.save(new ClaimType("PROJECT_REVIEWER", "PROJECT", ClaimRelations.REVIEWER, "Project Reviewer"));
        claimTypeRepository.save(new ClaimType("PROJECT_APPROVER", "PROJECT", ClaimRelations.APPROVER, "Project Approver"));
        claimTypeRepository.save(new ClaimType("PROJECT_EDITOR", "PROJECT", ClaimRelations.EDITOR, "Project Editor"));

        claimTypeRepository.save(new ClaimType("CATEGORY_LEADER", "CATEGORY", ClaimRelations.LEADER, "Category Leader"));
        claimTypeRepository.save(new ClaimType("CATEGORY_MEMBER", "CATEGORY", ClaimRelations.MEMBER, "Category Member"));
        claimTypeRepository.save(new ClaimType("CATEGORY_OBSERVER", "CATEGORY", ClaimRelations.OBSERVER, "Category Observer"));

        claimTypeRepository.save(new ClaimType("DOCUMENT_OWNER", "DOCUMENT", ClaimRelations.OWNER, "Document Owner"));
        claimTypeRepository.save(new ClaimType("DOCUMENT_MANAGER", "DOCUMENT", ClaimRelations.MANAGER, "Document Manager"));
        claimTypeRepository.save(new ClaimType("DOCUMENT_MEMBER", "DOCUMENT", ClaimRelations.MEMBER, "Document Member"));
        claimTypeRepository.save(new ClaimType("DOCUMENT_REVIEWER", "DOCUMENT", ClaimRelations.REVIEWER, "Document Reviewer"));
        claimTypeRepository.save(new ClaimType("DOCUMENT_APPROVER", "DOCUMENT", ClaimRelations.APPROVER, "Document Approver"));
        claimTypeRepository.save(new ClaimType("DOCUMENT_EDITOR", "DOCUMENT", ClaimRelations.EDITOR, "Document Editor"));
    }
}
