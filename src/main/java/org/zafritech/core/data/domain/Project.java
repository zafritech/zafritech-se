package org.zafritech.core.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.ProjectStatus;

@Entity(name = "CORE_PROJECTS")
public class Project implements Serializable {
    private static final long serialVersionUID = -1267086640569786293L;

    @Id
    @GeneratedValue
    private Long Id;

    private String uuId;

    private String numericNumber;
    
    private String projectNumber;

    private String projectCode;
    
    private String projectName;

    private String projectShortName;

    private String projectTagLine;
    
    @ManyToOne
    @JoinColumn(name = "infoClassId")
    private InformationClass infoClass;
    
    @ManyToOne
    @JoinColumn(name = "projectTypeId")
    private EntityType projectType;

    @Column(columnDefinition = "TEXT")
    private String projectDescription;

    private String projectLogo;
    
    @ManyToOne
    @JoinColumn(name = "sponsorId")
    private Company projectSponsor;

    @ManyToOne
    @JoinColumn(name = "contactId")
    private Contact projectContact;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "creatorId")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "managerId")
    private User projectManager;
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "CORE_PROJECT_APPLICATIONS",
               joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "application_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Application> applications = new HashSet<>();
      
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "CORE_PROJECT_MEMBERS",
               joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<User> projectMembers = new HashSet<User>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "CORE_PROJECT_DEFINITIONS",
               joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "definition_id", referencedColumnName = "id")}
    )
    @JsonManagedReference
    @OrderBy("term ASC")
    private List<Definition> definitions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Project() {
    }

    public Project(String name,
            String shortName,
            Company company) {

        this.uuId = UUID.randomUUID().toString();
        this.projectName = name;
        this.projectShortName = shortName;
        this.projectCode = new SimpleDateFormat("yy").format(new Date()) + new SimpleDateFormat("MM").format(new Date()) + name.substring(0, 3).toUpperCase();
        this.projectTagLine = "A Zidingo RMS managed project";
        this.projectSponsor = company;
        this.startDate = new Timestamp(System.currentTimeMillis());
        this.endDate = new Timestamp(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30 * 6));  // default approx 6 months in the future
        this.status = ProjectStatus.STATUS_PLANNED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());

    }

    public Project(String name,
            String shortName,
            Company company,
            InformationClass infoClass) {

        this.uuId = UUID.randomUUID().toString();
        this.projectName = name;
        this.projectShortName = shortName;
        this.projectCode = new SimpleDateFormat("yy").format(new Date()) + new SimpleDateFormat("MM").format(new Date()) + name.substring(0, 3).toUpperCase();
        this.projectTagLine = "A Zidingo RMS managed project";
        this.projectSponsor = company;
        this.infoClass = infoClass;
        this.startDate = new Timestamp(System.currentTimeMillis());
        this.endDate = new Timestamp(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30 * 6));  // default approx 6 months in the future
        this.status = ProjectStatus.STATUS_PLANNED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());

    }

    public Project(String number,
            String name,
            String shortName,
            Company company,
            InformationClass infoClass) {

        this.uuId = UUID.randomUUID().toString();
        this.projectNumber = number;
        this.projectName = name;
        this.projectCode = new SimpleDateFormat("yy").format(new Date()) + new SimpleDateFormat("MM").format(new Date()) + name.substring(0, 3).toUpperCase();
        this.projectShortName = shortName;
        this.projectTagLine = "A Zidingo RMS managed project";
        this.projectSponsor = company;
        this.infoClass = infoClass;
        this.startDate = new Timestamp(System.currentTimeMillis());
        this.endDate = new Timestamp(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30 * 6));  // default approx 6 months in the future
        this.status = ProjectStatus.STATUS_PLANNED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Project(EntityType type,
                    String number,
                    String name,
                    String shortName,
                    Company company,
                    InformationClass infoClass) {

        this.uuId = UUID.randomUUID().toString();
        this.projectType = type;
        this.projectNumber = number;
        this.numericNumber = number.replaceAll("[^0-9]", "");
        this.projectName = name;
        this.projectCode = number + new SimpleDateFormat("yy").format(new Date()) + new SimpleDateFormat("MM").format(new Date()) + name.replaceAll("\\s+","").substring(0, 3).toUpperCase();
        this.projectShortName = shortName;
        this.projectTagLine = "A Zidingo RMS managed project";
        this.projectSponsor = company;
        this.infoClass = infoClass;
        this.startDate = new Timestamp(System.currentTimeMillis());
        this.endDate = new Timestamp(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30 * 6));  // default approx 6 months in the future
        this.status = ProjectStatus.STATUS_PLANNED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Project{" + "Id=" + getId() + ", uuId=" + getUuId() + ", projectNumber=" + 
                getProjectNumber() + ", projectCode=" + getProjectCode() + ", projectName=" + 
                getProjectName() + ", projectShortName=" + getProjectShortName() + ", projectTagLine=" +
                getProjectTagLine() + ", infoClass=" + getInfoClass() + ", projectType=" + 
                getProjectType() + ", projectDescription=" + getProjectDescription() + ", projectSponsor=" +
                getProjectSponsor() + ", projectContact=" + getProjectContact() + ", startDate=" + 
                getStartDate() + ", endDate=" + getEndDate() + ", createdBy=" + getCreatedBy() + ", projectManager=" + 
                getProjectManager() + ", projectMembers=" + getProjectMembers() + ", status=" + 
                getStatus() + ", creationDate=" + getCreationDate() + ", modifiedDate=" + getModifiedDate() + '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return Id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getNumericNumber() {
        return numericNumber;
    }

    public void setNumericNumber(String numericNumber) {
        this.numericNumber = numericNumber;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectShortName() {
        return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    public String getProjectTagLine() {
        return projectTagLine;
    }

    public void setProjectTagLine(String projectTagLine) {
        this.projectTagLine = projectTagLine;
    }

    public InformationClass getInfoClass() {
        return infoClass;
    }

    public void setInfoClass(InformationClass infoClass) {
        this.infoClass = infoClass;
    }

    public EntityType getProjectType() {
        return projectType;
    }

    public void setProjectType(EntityType projectType) {
        this.projectType = projectType;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public void setProjectLogo(String projectLogo) {
        this.projectLogo = projectLogo;
    }

    public Company getProjectSponsor() {
        return projectSponsor;
    }

    public void setProjectSponsor(Company projectSponsor) {
        this.projectSponsor = projectSponsor;
    }

    public Contact getProjectContact() {
        return projectContact;
    }

    public void setProjectContact(Contact projectContact) {
        this.projectContact = projectContact;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public Set<User> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(Set<User> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}

