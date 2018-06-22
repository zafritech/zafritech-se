/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;

/**
 *
 * @author lukes
 */
public interface InterfaceIssueRepository extends CrudRepository<InterfaceIssue, Long> {
    
    InterfaceIssue findFirstByOrderByIdDesc();
    
    List<InterfaceIssue> findAllByOrderBySystemIdAsc();
}