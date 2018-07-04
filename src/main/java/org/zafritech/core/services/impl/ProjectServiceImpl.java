/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.generic.FiveValueDao;
import org.zafritech.core.data.dao.ProjectDao;
import org.zafritech.core.data.dao.generic.ImageItemDao;
import org.zafritech.core.data.dao.generic.ImageItemTitleDao;
import org.zafritech.core.data.dao.generic.TriValueDao;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.TemplateVariable;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectCompanyRole;
import org.zafritech.core.data.domain.ProjectSetting;
import org.zafritech.core.data.domain.ProjectWbsPackage;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectCompanyRoleRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.ProjectSettingRepository;
import org.zafritech.core.data.repositories.ProjectWbsPackageRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.TemplateVariableCategories;
import org.zafritech.core.enums.ProjectStatus;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.ProjectService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.data.repositories.TemplateVariableRepository;
import org.zafritech.core.enums.CompanyRole;
import org.zafritech.core.enums.ProjectSettingType;
import org.zafritech.core.services.DocumentService;
import org.zafritech.core.services.FileIOService;

/**
 *
 * @author LukeS
 */
@Service
public class ProjectServiceImpl implements ProjectService {
 
    @Value("${zafritech.paths.static-resources}")
    private String static_resources;

    @Autowired
    private CompanyRepository companyRepository;
   
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectWbsPackageRepository wbsPackageRepository;
    
    @Autowired
    private InformationClassRepository infoClassRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private ProjectCompanyRoleRepository roleRepository;
    
    @Autowired
    private ContactRepository contactRepository;
 
    @Autowired
    private ClaimRepository claimRepository;
 
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
 
    @Autowired
    private UserClaimRepository userClaimRepository;
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FileIOService fileIOService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private TemplateVariableRepository configRepository;
    
    @Autowired
    private DocumentService documentService;
    
    @Autowired
    private ProjectSettingRepository projectSettingRepository;
    
    @Override
    public Project saveDao(ProjectDao dao) {
        
        User manager;
        
        Company company = companyRepository.findOne(dao.getCompanyId());
        Project project;
        
        if (dao.getId() != null) {
        
            project = projectRepository.findOne(dao.getId());
            project.setProjectName(dao.getProjectName());
            project.setProjectShortName(dao.getProjectShortName());
            project.setProjectSponsor(company); 
            project.setModifiedDate(new Timestamp(System.currentTimeMillis())); 
            if (dao.getProjectNumber() != null) { project.setProjectNumber(dao.getProjectNumber()); }
            
        } else {
            
            project = new Project(entityTypeRepository.findOne(dao.getProjectTypeId()),
                                  dao.getProjectNumber(),
                                  dao.getProjectName(), 
                                  dao.getProjectShortName(), 
                                  company,
                                  infoClassRepository.findOne(dao.getInfoClassId()));
        }

        manager = (dao.getManagerId() != null) ? userRepository.findOne(dao.getManagerId()) : userService.loggedInUser();
       
        if (dao.getInfoClassId() != null) { project.setInfoClass(infoClassRepository.findOne(dao.getInfoClassId())); }
        if (dao.getProjectTypeId() != null) { project.setProjectType(entityTypeRepository.findOne(dao.getProjectTypeId())); }
        if (dao.getStartDate() != null) { project.setStartDate(Date.valueOf(dao.getStartDate())); }
        if (dao.getEndDate() != null) { project.setEndDate(Date.valueOf(dao.getEndDate())); }
        if (dao.getStatus() != null) { project.setStatus(ProjectStatus.valueOf(dao.getStatus())); }
        if (dao.getContactId() != null) { project.setProjectContact(contactRepository.findOne(dao.getContactId())); }
        if (dao.getProjectDescription() != null) { project.setProjectDescription(dao.getProjectDescription()); }
        
        project.setProjectManager(manager);
        project.setCreatedBy(userService.loggedInUser()); 

        project = projectRepository.save(project);
        
        // For new project
        if (dao.getId() == null) {

            // Create initial top level WBS packages
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0101", "SLC", "System Level Concept"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0102", "SLP", "System Level Planning"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0103", "SLS", "System Level Specification"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0104", "SLD", "System Level Design"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0105", "SLI", "System Level Integration"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0106", "SLV", "System Level Verification and Validation"));
            
            // Create initial folders
            Folder folder = folderRepository.save(new Folder(project.getProjectShortName(), entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_PROJECT"), null, project, 0));
            folderRepository.save(new Folder("Concept", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 0));
            folderRepository.save(new Folder("Planning", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 1));
            folderRepository.save(new Folder("Specification", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 2));
            folderRepository.save(new Folder("Design", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 3));
            folderRepository.save(new Folder("Integration", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 4));
            folderRepository.save(new Folder("Validation", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 5));

            // Create project manager claim
            ClaimType claimType = claimTypeRepository.findFirstByTypeName("PROJECT_MANAGER");
            String description = claimType.getTypeDescription() + " - " + project.getProjectName() + " (" + project.getProjectNumber() + ")";
            claimService.updateUserClaim(manager, claimType, project.getId(), description);

            // Create project member claim
            claimType = claimTypeRepository.findFirstByTypeName("PROJECT_MEMBER");
            description = claimType.getTypeDescription() + " - " + project.getProjectName() + " (" + project.getProjectNumber() + ")";
            claimService.updateUserClaim(manager, claimType, project.getId(), description);
        
            documentService.initNewProjectDocuments(project, userService.loggedInUser());
        }
        
        return project;
    }

    @Override
    public Project createProjectImageSetting(ImageItemTitleDao dao) throws IOException, ParseException {
       
        Project project = projectRepository.findOne(dao.getItemId());
        String images_dir = static_resources + "images/projects/project_" + project.getUuId();
         
        if (!dao.getImageFile().isEmpty()) {
            
            ProjectSettingType settingType = ProjectSettingType.valueOf(dao.getItemTitle());
            ProjectSetting setting = projectSettingRepository.findFirstByProjectAndSettingType(project, settingType);
            String imageFileExtension = FilenameUtils.getExtension(dao.getImageFile().getOriginalFilename());
            String imageFullPath = images_dir;
            String imageRelPath = "";
            
            if (settingType == ProjectSettingType.SETTING_REPORT_HEADER_IMAGE_FIRST) {
                
                imageRelPath =  "header_image_first." + imageFileExtension;
                
            } else if (settingType == ProjectSettingType.SETTING_REPORT_HEADER_IMAGE_SECOND) {
                
                imageRelPath = "header_image_second." + imageFileExtension;
            }
                
            imageFullPath = imageFullPath + "/" + imageRelPath;

            // Image already exists (Remove image file)
            File file = new File(imageFullPath);
            if (file.exists()) {

                file.delete();
            }
            
            // Setting already exists (Update)
            if (setting != null) {
                
                setting.setValue(imageRelPath); 
                
            } else {
                
                setting = new ProjectSetting(project, settingType, imageRelPath);
            }
            
            List<String> imageFiles = fileIOService.saveUploadedFiles(Arrays.asList(dao.getImageFile()));
            FileUtils.moveFile(FileUtils.getFile(imageFiles.get(0)), FileUtils.getFile(imageFullPath));
            
            projectSettingRepository.save(setting);
        }
        
        return project;
    }
    
    @Override
    public Project createProjectStringSetting(TriValueDao dao) {
        
        Project project = projectRepository.findOne(dao.getId());
        ProjectSettingType settingType = ProjectSettingType.valueOf(dao.getItemName());
        ProjectSetting setting = projectSettingRepository.findFirstByProjectAndSettingType(project, settingType);
        
        // Already has this setting 
        if (setting != null) {

            setting.setValue(dao.getItemDescription());
            
        } else {
            
            setting = new ProjectSetting(project, settingType, dao.getItemDescription());
        }
        
        projectSettingRepository.save(setting);
        
        return project;
    }
    
    @Override
    public Project updateProjectLogo(ImageItemDao dao) throws IOException, ParseException {
         
        String images_dir = static_resources + "images/projects/";
        
        if (!dao.getImageFile().isEmpty()) {
            
            Project project = projectRepository.findOne(dao.getItemId());
            String imageFileExtension = FilenameUtils.getExtension(dao.getImageFile().getOriginalFilename());
            String imageFileName = "project_logo" + "." + imageFileExtension;
            String imageFullPath = images_dir + "project_" + project.getUuId() + "/" + imageFileName;
                    
            // Already has a logo set (Remove)
            if (project.getProjectLogo() != null) {
                
                // Remove uploaded file
                File file = new File(imageFullPath);
                if (file.exists()) {

                    file.delete();
                }
            }
            
            // Upload and move logo image file
            List<String> imageFiles = fileIOService.saveUploadedFiles(Arrays.asList(dao.getImageFile()));
            FileUtils.moveFile(FileUtils.getFile(imageFiles.get(0)), FileUtils.getFile(imageFullPath)); 
            
            project.setProjectLogo(imageFileName); 
            projectRepository.save(project);
            
            return project;
            
        } else {
        
            return null;
        }
    }
    
    @Override
    public User addMemberToProject(Project project, User user) {
        
        ClaimType claimType = claimTypeRepository.findByTypeName("PROJECT_MEMBER");
        String claimDecsription = claimType.getTypeDescription() + " - " + project.getProjectName() + " (" + project.getProjectNumber() + ")";
        
        UserClaim claim = claimService.updateUserClaim(user, claimType, project.getId(), claimDecsription);
        
        return claim.getUser();
    }

    @Override
    public List<Application> addProjectApplications(Project project, List<Application> applications){
        
        Set<Application> appSet = new HashSet<>(applications);
        
        project.setApplications(appSet); 
        project = projectRepository.save(project);
        
        return new ArrayList<>(project.getApplications());
    }
    
    @Override
    public List<User> addProjectMembers(Project project, List<User> users) {
        
        ClaimType claimType = claimTypeRepository.findByTypeName("PROJECT_MEMBER");
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, project.getId());
        String claimDecsription = claimType.getTypeDescription() + " - " + project.getProjectName() + " (" + project.getProjectNumber() + ")";
        
        // Delete old claims
        List<User> current = claimService.findProjectMemberClaims(project);
        
        current.stream().map((user) -> userClaimRepository.findFirstByUserAndClaim(user, claim)).forEachOrdered((userClaim) -> {
            userClaimRepository.delete(userClaim);
        });
        
        // Replace with new claims
        users.forEach((user) -> {
            claimService.updateUserClaim(user, claimType, project.getId(), claimDecsription);
        });
        
        return claimService.findProjectMemberClaims(project); 
    }

    @Override
    public String generateProjectNumber(EntityType type) {
        
        Map<String, String> valuesMap = new HashMap<>();
        
        List<TemplateVariable> projectProperties = configRepository.findByCategory(TemplateVariableCategories.VARIABLES_PROJECT);
        
        projectProperties.forEach((config) -> {
            valuesMap.put(config.getVariable(), config.getValue());
        });
        
        StrSubstitutor subst = new StrSubstitutor(valuesMap);
        
        String numericValue;
        String format = String.format("%%0%dd", Integer.valueOf(subst.replace("${project_numbering_digits}")));
        
        Project project = projectRepository.findFirstByOrderByNumericNumberDesc();
        
        if (project != null) {
            
            if (project.getNumericNumber() != null) {
            
                numericValue = String.format(format, Integer.valueOf(project.getNumericNumber()) + 1);
                
            } else {
                
                numericValue = String.format(format, Integer.valueOf(project.getProjectNumber().replaceAll("[^0-9]", "")) + 1);
            }
            
        } else {
            
            numericValue = String.format(format, Integer.valueOf(subst.replace("${project_numbering_start}")));
        }
        
        valuesMap.put("project_type_code", type.getEntityTypeCode());
        valuesMap.put("project_numbering_numberic", numericValue);
        
        return subst.replace("${project_numbering_format}"); 
    }
    
    @Override
    public ProjectCompanyRole saveProjectRoleDao(FiveValueDao dao) {
        
        ProjectCompanyRole companyRole = new ProjectCompanyRole(projectRepository.findOne(dao.getId()),
                                                                companyRepository.findOne(Long.valueOf(dao.getValue1())),
                                                                CompanyRole.valueOf(dao.getValue2()),
                                                                dao.getValue3());
        
        companyRole.setCompanyRoleDescription(dao.getValue4());
        
        companyRole = roleRepository.save(companyRole);
        
        return companyRole;
    }
}
