/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.requirements.data.domain.VerificationReference;

/**
 *
 * @author LukeS
 */
public interface VerificationReferenceRepository extends CrudRepository<VerificationReference, Long> {
    
}
