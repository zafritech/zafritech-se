/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.risksman.services.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.zafritech.applications.risksman.enums.Probability;
import org.zafritech.applications.risksman.enums.RiskClass;
import org.zafritech.applications.risksman.enums.Severity;
import org.zafritech.applications.risksman.services.RiskItemService;

/**
 *
 * @author LukeS
 */
@Service
public class RiskItemServiceImpl implements RiskItemService {

    @Override
    public Integer severityValue(Severity severity) {
        
        EnumMap<Severity, Integer> severities = getSeverityValues();
        
        return severities.get(severity);
    }

    @Override
    public Integer probabilityValue(Probability probability) {
        
        EnumMap<Probability, Integer> probabilities = getProbabilityValues();
        
        return probabilities.get(probability);
    }

    @Override
    public Integer riskClassValue(Severity severity, Probability probability) {
        
        EnumMap<Severity, Integer> severities = getSeverityValues();
        EnumMap<Probability, Integer> probabilities = getProbabilityValues();
        
        return severities.get(severity) * probabilities.get(probability);
    }

    @Override
    public Severity severityString(Integer severity) {
        
        EnumMap<Severity, Integer> severities = getSeverityValues();
        
        for (Map.Entry<Severity, Integer> entry : severities.entrySet()) {
            
            if (Objects.equals(severity, entry.getValue())) {
                
                return entry.getKey();
            }
        }
        
        return null;
    }

    @Override
    public Probability probabilityString(Integer probability) {
        
        EnumMap<Probability, Integer> probabilities = getProbabilityValues();
        
        for (Map.Entry<Probability, Integer> entry : probabilities.entrySet()) {
            
            if (Objects.equals(probability, entry.getValue())) {
                
                return entry.getKey();
            }
        }
        
        return null;
    }

    @Override
    public RiskClass riskClassString(Integer riskClass) {
        
        // TBD
        
        return null;
    }
    
    private EnumMap<Severity, Integer> getSeverityValues() {
        
        EnumMap<Severity, Integer> map = new EnumMap<Severity, Integer>(Severity.class); 
        
        map.put(Severity.CATASTROPHIC, 1);
        map.put(Severity.CRITICAL, 2);
        map.put(Severity.MARGINAL, 3);
        map.put(Severity.NEGLIGIBLE, 4);
        
        return map;
    }
    
    private EnumMap<Probability, Integer> getProbabilityValues() {
        
        EnumMap<Probability, Integer> map = new EnumMap<Probability, Integer>(Probability.class); 
        
        map.put(Probability.FREQUENT, 1);
        map.put(Probability.PROBABLE, 2);
        map.put(Probability.OCCASIONAL, 3);
        map.put(Probability.REMOTE, 4);
        map.put(Probability.IMPROBABLE, 5);
        map.put(Probability.ELIMINATED, 6);
        
        return map;
    }
}
