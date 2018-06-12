/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.api;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.ModelAndView;
import org.zafritech.applications.integration.data.converters.InterfaceListToInterfaceViewListConverter;
import org.zafritech.applications.integration.data.converters.InterfaceToInterfaceViewConverter;
import org.zafritech.applications.integration.data.dao.InterfaceCommentDao;
import org.zafritech.applications.integration.data.dao.InterfaceCommentUpdateDao;
import org.zafritech.applications.integration.data.dao.InterfaceCreateDao;
import org.zafritech.applications.integration.data.dao.InterfaceEditDao;
import org.zafritech.applications.integration.data.dao.InterfaceViewDao;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.data.domain.InterfaceIssueComment;
import org.zafritech.applications.integration.data.repositories.ElementRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceIssueCommentRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceIssueRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;
import org.zafritech.applications.integration.enums.InterfaceStatus;
import org.zafritech.applications.integration.services.IntegrationService;
import org.zafritech.applications.integration.services.InterfaceService;
import org.zafritech.core.data.dao.generic.TriValueDao;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.services.ApplicationService;

/**
 *
 * @author lukes
 */
@RestController
public class InterfaceRestController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private InterfaceRepository interfaceRepository;

    @Autowired
    private InterfaceIssueCommentRepository commentRepository;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private InterfaceIssueRepository issueRepository;

    @Autowired
    private InterfaceToInterfaceViewConverter interfaceConverter;
    
    @Autowired
    private InterfaceListToInterfaceViewListConverter interfaceListConverter;
    
    @RequestMapping(value = "/api/integration/interface/{id}", method = GET)
    public ResponseEntity<Interface> getInterfaceById(@PathVariable(value = "id") Long id) {

        Interface iface = interfaceRepository.findOne(id);

        return new ResponseEntity<>(iface, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/interface/view/{id}", method = GET)
    public ModelAndView ElementInterfaceView(@PathVariable(value = "id") Long id) {

        InterfaceViewDao iface = interfaceConverter.convert(interfaceRepository.findOne(id));
        List<InterfaceIssue> issues = interfaceRepository.findOne(id).getIssues();
        ModelAndView modelView = new ModelAndView(applicationService.getApplicationTemplateName() + "/views/integration/interface");
        
        modelView.addObject("interface", iface);
        modelView.addObject("issues", issues);

        return modelView;
    }

    @RequestMapping(value = "/api/integration/interface/issue/view/{id}", method = GET)
    public ModelAndView ElementInterfaceIssueView(@PathVariable(value = "id") Long id) {

        InterfaceIssue issue = issueRepository.findOne(id);
        List<InterfaceIssueComment> comments = issue.getComments();
        Project project = issue.getIssueInterface().getProject();

        ModelAndView modelView = new ModelAndView(applicationService.getApplicationTemplateName() + "/views/integration/interface-issue");

        modelView.addObject("project", project);
        modelView.addObject("interface", issue.getIssueInterface());
        modelView.addObject("issue", issue);
        modelView.addObject("comments", comments);

        return modelView;
    }

    @RequestMapping(value = "/api/integration/element/interfaces/view/{id}", method = GET)
    public ModelAndView ElementInterfaces(@PathVariable(value = "id") Long id) {

        Element element = elementRepository.findOne(id);
        List<InterfaceViewDao> interfaces = interfaceListConverter.convert(interfaceRepository.findByPrimaryElementOrSecondaryElement(element, element));
        
        String title = element.getEntity().getCompanyCode() + " -> ";
        if (element.getParent() != null) {

            title = title + element.getParent().getSbs() + " " + element.getParent().getName() + " -> ";
        }
        title = title + element.getSbs() + " " + element.getName();

        ModelAndView modelView = new ModelAndView(applicationService.getApplicationTemplateName() + "/views/integration/interfaces-list");
        modelView.addObject("elementId", id);
        modelView.addObject("element", element);
        modelView.addObject("interfaceListTitle", title);

        if (!interfaces.isEmpty()) {

            modelView.addObject("message", interfaces.size() + " defined interfaces found");

        } else {

            modelView.addObject("message", "No interfaces have been defined for this element.");
        }
            
        modelView.addObject("interfaces", interfaces);

        return modelView;
    }

    @RequestMapping(value = "/api/integration/element/interfaces/view/{id}/{levels}", method = GET)
    public ModelAndView ElementInterfacesByLevel(@PathVariable(value = "id") Long id,
            @PathVariable(value = "levels") Long levels) {

        String levelString = StringUtils.leftPad(Integer.toBinaryString((int) (long) levels), 4, '0');

        Element element = elementRepository.findOne(id);
        List<Interface> interfaces = interfaceService.findByLevels(element, levelString);

        ModelAndView modelView = new ModelAndView(applicationService.getApplicationTemplateName() + "/views/integration/interfaces-summary");
        modelView.addObject("elementId", id);
        modelView.addObject("detailsTitle", "System Element Interfaces - " + element.getName());

        if (!interfaces.isEmpty()) {

            modelView.addObject("interfaces", interfaces);
            modelView.addObject("message", interfaces.size() + " defined interfaces found");

        } else {

            modelView.addObject("interfaces", interfaces);
            modelView.addObject("message", "No interfaces have been defined for this element.");
        }

        return modelView;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/integration/interfaces/create/save", method = RequestMethod.POST)
    public ResponseEntity<Interface> newInterfaceCreateSave(@RequestBody InterfaceCreateDao interfaceDao) {

        if (interfaceService.isSelfInterfacing(interfaceDao)) {

            return new ResponseEntity("SBS element cannot interface to itself.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        if (interfaceService.isDuplicateInterface(interfaceDao)) {

            return new ResponseEntity("Duplicate interface. The inetrace between the selected SBS elements already exists.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        Interface iface = interfaceService.saveInterface(interfaceDao);

        return new ResponseEntity<>(iface, HttpStatus.OK);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/integration/interfaces/edit/update", method = RequestMethod.POST)
    public ResponseEntity<Interface> uipdateSystemInterface(@RequestBody InterfaceEditDao interfaceDao) {

        if (interfaceService.isSelfInterfacing(interfaceDao)) {

            return new ResponseEntity("SBS element cannot interface to itself.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        Interface interFace = interfaceService.updateInterface(interfaceDao);

        return new ResponseEntity<>(interFace, HttpStatus.OK);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/integration/interfaces/issue/save", method = RequestMethod.POST)
    public ResponseEntity<InterfaceIssue> newInterfaceIssue(@RequestBody TriValueDao issueDao) {

        InterfaceIssue issue = integrationService.createNewInterfaceIssue(issueDao);

        if (issue == null) {

            return new ResponseEntity("Issue title and description cannot be empty.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/integration/interfaces/issue/comment/save", method = RequestMethod.POST)
    public ResponseEntity<InterfaceIssueComment> newInterfaceIssueComment(@RequestBody InterfaceCommentDao commentDao) {

        InterfaceIssueComment comment = interfaceService.createNewInterfaceIssueComment(commentDao);

        if (comment == null) {

            return new ResponseEntity("Issue title and description cannot be empty.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/api/integration/interfaces/issue/comment/update", method = RequestMethod.POST)
    public ResponseEntity<InterfaceIssueComment> updateInterfaceIssueComment(@RequestBody InterfaceCommentUpdateDao commentDao) {
        
        InterfaceIssueComment comment = interfaceService.updateInterfaceIssueComment(commentDao);
        
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/interfaces/issue/commentbyid/{id}", method = GET)
    public ResponseEntity<InterfaceIssueComment> getInterfaceIssueComment(@PathVariable(value = "id") Long id) {
        
        InterfaceIssueComment comment = commentRepository.findOne(id);
                
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/integration/interface/status/list", method = GET)
    public List<InterfaceStatus> getInterfaceStatusList() {

        return Arrays.asList(InterfaceStatus.values());
    }
}
