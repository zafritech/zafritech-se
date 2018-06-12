/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.requirements.data.domain.LinkGroup;
import org.zafritech.core.data.domain.Document;

/**
 *
 * @author LukeS
 */
public interface LinkGroupRepository extends CrudRepository<LinkGroup, Long> {
    
    LinkGroup findFirstBySourceDocumentAndDestinationDocument(Document source, Document destination);
}
