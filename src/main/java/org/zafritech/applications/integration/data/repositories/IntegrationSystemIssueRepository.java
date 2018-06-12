/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.integration.data.domain.IntegrationSystemIssue;

/**
 *
 * @author Luke Sibisi
 */
public interface IntegrationSystemIssueRepository extends CrudRepository<IntegrationSystemIssue, Long> {
    
}
