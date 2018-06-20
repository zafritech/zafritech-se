/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.risksman.services;

import org.zafritech.applications.risksman.enums.Probability;
import org.zafritech.applications.risksman.enums.RiskClass;
import org.zafritech.applications.risksman.enums.Severity;

/**
 *
 * @author LukeS
 */
public interface RiskItemService {
    
    Integer severityValue(Severity severity);
    
    Integer probabilityValue(Probability probability);
    
    Integer riskClassValue(Severity severity, Probability probability);
    
    Severity severityString(Integer severity);
    
    Probability probabilityString(Integer probability);
    
    RiskClass riskClassString(Integer riskClass);
}
