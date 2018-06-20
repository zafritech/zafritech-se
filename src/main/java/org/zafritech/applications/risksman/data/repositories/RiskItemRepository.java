/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.risksman.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.risksman.data.domain.RiskItem;

/**
 *
 * @author LukeS
 */
public interface RiskItemRepository extends CrudRepository<RiskItem, Long> {
    
}
