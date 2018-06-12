/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Reference;
import org.zafritech.core.enums.ReferenceSources;

/**
 *
 * @author LukeS
 */
public interface ReferenceRepository extends CrudRepository<Reference, Long> {
    
    Reference findBySourceTypeAndIdValue(ReferenceSources sourece, Long id);
}
