/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.generic.FiveValueDao;
import org.zafritech.core.data.dao.ProjectDao;
import org.zafritech.core.data.dao.generic.ImageItemDao;
import org.zafritech.core.data.dao.generic.ImageItemTitleDao;
import org.zafritech.core.data.dao.generic.TriValueDao;
import org.zafritech.core.data.dao.generic.ValuePairDao;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectCompanyRole;
import org.zafritech.core.data.domain.ProjectWbsPackage;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.projections.UserView;
import org.zafritech.core.data.repositories.ApplicationRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectCompanyRoleRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.ProjectWbsPackageRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.ProjectSettingType;
import org.zafritech.core.enums.ProjectStatus;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.ProjectService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.services.UserSessionService;


/**
 *
 * @author LukeS
 */
@RestController
public class ProjectRestController {
    
    @Autowired
    private ApplicationRepository applicationRepository;
        
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectCompanyRoleRepository companyRoleRepository;
    
    @Autowired
    private ProjectWbsPackageRepository wbsPackageRepository;
    
    @Autowired
    private ClaimService claimService;
      
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserSessionService stateService;
    
    @RequestMapping(value = "/api/admin/projects/list", method = GET)
    public ResponseEntity<List<Project>> listProjects() {
  
        User user = userService.loggedInUser();
        boolean isAdmin = userService.hasRole("ROLE_ADMIN");
        List<Project> allProjects = projectRepository.findAllByOrderByProjectName();
        List<Project> projects = new ArrayList<>();
        
        allProjects.stream().filter((project) -> (isAdmin || claimService.isProjectMember(user, project))).forEachOrdered((project) -> {
            projects.add(project);
        });
        
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/projects/list/open", method = GET)
    public ResponseEntity<List<Project>> listOpenProjects() {
  
        List<Project> projects = stateService.getOpenProjects();
 
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/projects/list/closed", method = GET)
    public ResponseEntity<List<Project>> listClosedProjects() {
  
        User user = userService.loggedInUser();
        boolean isAdmin = userService.hasRole("ROLE_ADMIN");
        List<Project> closedProjects = new ArrayList<>();
        
        List<Project> projects = projectRepository.findAllByOrderByProjectName();
 
        projects.stream().filter((project) -> (!stateService.isProjectOpen(project) && (isAdmin || claimService.isProjectMember(user, project)))).forEachOrdered((project) -> {
            
            closedProjects.add(project);
        });
 
        return new ResponseEntity<>(closedProjects, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/projects/close/allopen", method = GET)
    public ResponseEntity<Integer> closeAllOpenProjects() {
        
        Integer numberClosed = stateService.closeAllProjects();
        
        return new ResponseEntity<>(numberClosed, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/projects/project/byid/{id}", method = GET)
    public ResponseEntity<Project> getProjectById(@PathVariable(value = "id") Long id) {
  
        Project project = projectRepository.findOne(id);
 
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
 
    @RequestMapping(value = "/api/projects/project/new/number/{id}", method = GET)
    public ResponseEntity<String> getNewProjectNumber(@PathVariable(value = "id") Long id) {
  
        String number = projectService.generateProjectNumber(entityTypeRepository.findOne(id));
 
        return new ResponseEntity<>(number, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/project/new", method = POST)
    public ResponseEntity<Project> newProject(@RequestBody ProjectDao projectDao) {
  
        Project project = projectService.saveDao(projectDao);
 
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/company/role/add", method = POST)
    public ResponseEntity<ProjectCompanyRole> newProjectCompanyRole(@RequestBody FiveValueDao dao) {
  
        ProjectCompanyRole companyRole = projectService.saveProjectRoleDao(dao);
 
        return new ResponseEntity<>(companyRole, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/projects/companies/byproject/{id}", method = GET)
    public ResponseEntity<List<ProjectCompanyRole>> getProjectCompanies(@PathVariable(value = "id") Long id) {
    
        Project project = projectRepository.findOne(id);
        List<ProjectCompanyRole> roles = companyRoleRepository.findByProjectOrderByProjectProjectNameAsc(project);
        
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }  
    
    @RequestMapping(value = "/api/admin/projects/update/{uuid}", method = POST)
    public ResponseEntity<Project> updateProject(@RequestBody ProjectDao projectDao,
                                                 @PathVariable(value = "uuid") String uuid) {
  
        projectDao.setId(projectRepository.getByUuId(uuid).getId());
        Project project = projectService.saveDao(projectDao);
        
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/get/{uuid}", method = GET)
    public ResponseEntity<Project> getProjectProperties(@PathVariable(value = "uuid") String uuid) {
        
        Project project = projectRepository.getByUuId(uuid);
                
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/admin/project/logo/update", method = RequestMethod.POST)
    public ResponseEntity<?> addUpdateProjectLogo(ImageItemDao imageDao) throws IOException, ParseException {
        
        if (imageDao.getImageFile().isEmpty()) {

            return new ResponseEntity("Please select a image file!", HttpStatus.OK);
        }
        
        Project project = projectService.updateProjectLogo(imageDao);
        
        if (project != null) {
        
            return new ResponseEntity("Successfully updated logo for " + project.getProjectName(), new HttpHeaders(), HttpStatus.OK);
        }
        
        return new ResponseEntity("Error updating company logo!", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/status/list", method = GET)
    public List<ProjectStatus> getProjectStatusList() {
        
        return Arrays.asList(ProjectStatus.values());
    }
    
    @RequestMapping(value = "/api/projects/settings/types/list", method = GET)
    public List<ProjectSettingType> getProjectSettingsTypeList() {
        
        return Arrays.asList(ProjectSettingType.values());
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/projects/settings/image/create", method = RequestMethod.POST)
    public ResponseEntity<?> addCreateProjectImageSetting(ImageItemTitleDao imageDao) throws IOException, ParseException {
        
        if (imageDao.getImageFile().isEmpty()) {

            return new ResponseEntity("Please select a image file!", HttpStatus.OK);
        }
        
        Project project = projectService.createProjectImageSetting(imageDao);
        
        if (project != null) {
        
            return new ResponseEntity("Successfully created image setting for " + project.getProjectName(), new HttpHeaders(), HttpStatus.OK);
        }
        
        return new ResponseEntity("Error creating project image setting!", HttpStatus.OK);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/projects/settings/string/create", method = RequestMethod.POST)
    public ResponseEntity<?> addCreateProjectStringSetting(TriValueDao stringsDao) {
        
        System.out.println(stringsDao);
        
        Project project = projectService.createProjectStringSetting(stringsDao);
        
        if (project != null) {
        
            return new ResponseEntity("Successfully created string setting for " + project.getProjectName(), new HttpHeaders(), HttpStatus.OK);
        }
        
        return new ResponseEntity("Error creating project string setting!", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/project/members/list/{uuid}", method = GET)
    public ResponseEntity<List<UserView>> getProjectMembersList(@PathVariable(value = "uuid") String uuid) {
       
        Project project = projectRepository.getByUuId(uuid);
        List<User> users = claimService.findProjectMemberClaims(project);
         
        List<UserView> members = new ArrayList<>();
        
        users.forEach((user) -> {
            
            members.add(userRepository.findUserViewByEmail(user.getEmail()));
        });
        
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/project/applications/list/{uuid}", method = GET)
    public ResponseEntity<List<Application>> getProjectApplicationsByUuId(@PathVariable(value = "uuid") String uuid) {
        
        Project project = projectRepository.getByUuId(uuid);
        List<Application> applications = new ArrayList<>(project.getApplications());
        
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/add/user/{uuid}", method = POST)
    public ResponseEntity<UserClaim> addProjectMembersByUuId(@RequestBody Long projectId,
                                                             @PathVariable(value = "uuid") String uuid) {
       
        User user = userRepository.findByUuId(uuid);
        ClaimType type = claimTypeRepository.findFirstByTypeName("PROJECT_MEMBER");
        
        UserClaim claim = claimService.updateUserClaim(user, type, projectId);
        
        return new ResponseEntity<>(claim, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/members/add/{uuid}", method = POST)
    public ResponseEntity<List<User>> addProjectMembers(@RequestBody List<ValuePairDao> daos,
                                                        @PathVariable(value = "uuid") String uuid) {
       
        List<User> users = new ArrayList<>();
        Project project = projectRepository.getByUuId(uuid);
        
        daos.forEach((dao) -> {
            
            users.add(userRepository.findOne(dao.getId()));
        });
        
        List<User> members = projectService.addProjectMembers(project, users);
        
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/applications/add/{uuid}", method = POST)
    public ResponseEntity<List<Application>> addProjectApplications(@RequestBody List<ValuePairDao> daos,
                                                                    @PathVariable(value = "uuid") String uuid) {
       
        Project project = projectRepository.getByUuId(uuid);
        List<Application> apps = new ArrayList<>();
        
        daos.forEach((dao) -> {
            
            apps.add(applicationRepository.findOne(dao.getId()));
        });
        
        List<Application> applications = projectService.addProjectApplications(project, apps);
        
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/project/infoclass/list", method = GET)
    public ResponseEntity<List<InformationClass>> listInformationClasses() {
        
        List<InformationClass> infoClasses = infoClassRepository.findAllByOrderByClassNameAsc(); 
        
        return new ResponseEntity<>(infoClasses, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/projects/wbs/packages/{id}", method = GET)
    public ResponseEntity<ProjectWbsPackage> getProjectWBSPackage(@PathVariable(value = "id") Long id) {
        
        ProjectWbsPackage wbs = wbsPackageRepository.findOne(id);
        
        return new ResponseEntity<>(wbs, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/projects/wbs/packages/list/{uuid}", method = GET)
    public ResponseEntity<List<ProjectWbsPackage>> listProjectWBSPackages(@PathVariable(value = "uuid") String uuid) {
        
        Project project = projectRepository.getByUuId(uuid);
        List<ProjectWbsPackage> wbsList = wbsPackageRepository.findByProjectOrderByWbsName(project);
        
        return new ResponseEntity<>(wbsList, HttpStatus.OK);
    }
}
