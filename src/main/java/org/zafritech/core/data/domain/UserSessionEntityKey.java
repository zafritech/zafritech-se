/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.zafritech.core.enums.UserSessionEntityTypes;

/**
 *
 * @author LukeS
 */
@Embeddable
public class UserSessionEntityKey implements Serializable {
    
    @Column(name = "userId", nullable = false)
    private Long userId;
    
    @Column(name = "entityType", length = 191, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSessionEntityTypes entityType;

    @Column(name = "entityId", nullable = false)
    private Long entityId;

    public UserSessionEntityKey() {
        
    }

    public UserSessionEntityKey(Long userId, UserSessionEntityTypes entityType, Long entityId) {
        
        this.userId = userId;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.userId);
        hash = 17 * hash + Objects.hashCode(this.entityType);
        hash = 17 * hash + Objects.hashCode(this.entityId);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final UserSessionEntityKey other = (UserSessionEntityKey) obj;
        
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        
        if (this.entityType != other.entityType) {
            return false;
        }
        
        if (!Objects.equals(this.entityId, other.entityId)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        
        return "UserEntityStateKey{" + "userId=" + getUserId() + ", entityType=" 
                + getEntityType() + ", entityId=" + getEntityId() + '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserSessionEntityTypes getEntityType() {
        return entityType;
    }

    public void setEntityType(UserSessionEntityTypes entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
