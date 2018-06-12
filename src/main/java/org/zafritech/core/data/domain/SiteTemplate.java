package org.zafritech.core.data.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_APPLICATION_TEMPLATES")
public class SiteTemplate implements Serializable {

	private static final long serialVersionUID = -8499493086087672888L;
	
	@Id
    @GeneratedValue
    private Long id;

    private String uuId;

    private String templateName;
    
    private String templateDescription;
    
    private boolean active;

	@Override
	public String toString() {
		
		return "SiteTemplate [id=" + id + ", uuId=" + uuId + ", templateName=" + templateName + ", templateDescription="
				+ templateDescription + ", active=" + active + "]";
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}
}
