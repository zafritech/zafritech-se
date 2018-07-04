package org.zafritech.core.data.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.zafritech.core.enums.ProjectSettingType;

@Entity(name = "CORE_PROJECT_SETTINGS")
public class ProjectSetting implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @Enumerated(EnumType.STRING)
    private ProjectSettingType settingType;

    private String value;

    public ProjectSetting() {
        
    }

    public ProjectSetting(Project project, ProjectSettingType settingType, String value) {
        
        this.project = project;
        this.settingType = settingType;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectSettingType getSettingType() {
        return settingType;
    }

    public void setSettingType(ProjectSettingType settingType) {
        this.settingType = settingType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        
        return "ProjectSettings{" + "id=" + id + ", project=" + project + 
                                ", settingType=" + settingType + ", value=" + value + '}';
    }
}

