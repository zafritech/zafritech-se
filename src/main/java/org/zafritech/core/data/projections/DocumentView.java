/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.projections;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import org.zafritech.core.data.domain.Document;

/**
 *
 * @author LukeS
 */
@Projection(name = "DocumentView", types = Document.class)
public interface DocumentView {
    
    String getId();
    
    @Value("#{target.identifier}")
    String getIdentifier();

    @Value("#{target.documentName}")
    String getDocumentName();

    @Value("#{target.documentLongName}")
    String getDocumentLongName();

    @Value("#{target.project.projectName}")
    String getProjectName();

    @Value("#{target.project.projectShortName}")
    String getInfoClass();

    @Value("#{target.documentIssue}")
    String getVersion();

    @Value("#{target.modifiedDate}")
    Date getModDate();
}
