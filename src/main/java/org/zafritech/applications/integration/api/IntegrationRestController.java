/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.api;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zafritech.applications.integration.data.converters.ElementListToElementViewDaoListConverter;
import org.zafritech.applications.integration.data.converters.ElementToElementViewDaoConverter;
import org.zafritech.applications.integration.data.converters.EntityListToEntityViewListDaoConverter;
import org.zafritech.applications.integration.data.converters.EntityToEntityViewDaoConverter;
import org.zafritech.applications.integration.data.converters.InterfaceListToInterfaceViewListConverter;
import org.zafritech.applications.integration.data.dao.ElementCreateDao;
import org.zafritech.applications.integration.data.dao.ElementEditDao;
import org.zafritech.applications.integration.data.dao.ElementViewDao;
import org.zafritech.applications.integration.data.dao.EntityViewDao;
import org.zafritech.applications.integration.data.dao.InterfaceViewDao;
import org.zafritech.applications.integration.data.dao.SBSTreeDao;
import org.zafritech.applications.integration.data.dao.TreeElementDao;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.repositories.ElementRepository;
import org.zafritech.applications.integration.data.repositories.IntegrationEntityRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;
import org.zafritech.applications.integration.services.IntegrationService;
import org.zafritech.core.data.dao.generic.ValuePairDao;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author lukes
 */
@RestController
public class IntegrationRestController {
	
    @Autowired
    private UserSessionService userSessionService;
	
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private InterfaceRepository interfaceRepository;

    @Autowired
    private IntegrationEntityRepository entityRepository;

    @Autowired
    private IntegrationEntityRepository integrationEntityRepository;

    @Autowired
    private IntegrationService elementService;

    @Autowired
    private ElementToElementViewDaoConverter elementConverter;

    @Autowired
    private ElementListToElementViewDaoListConverter elementListConverter;

    @Autowired
    private EntityToEntityViewDaoConverter entityConverter;

    @Autowired
    private EntityListToEntityViewListDaoConverter entityListConverter;

    @Autowired
    private InterfaceListToInterfaceViewListConverter interfaceListConverter;
    
    @RequestMapping(value = "/api/integration/project/entity/add", method = POST)
    public ResponseEntity<IntegrationEntity> newProjectCompanyRole(@RequestBody ValuePairDao dao) {
  
        IntegrationEntity entity = elementService.createIntegrationEntity(dao);
 
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/integration/element/id/{id}", method = RequestMethod.GET)
    public ElementViewDao getElementById(@PathVariable(value = "id") Long elementId) {

        ElementViewDao element = elementConverter.convert(elementRepository.findOne(elementId));

        return element;
    }

    @RequestMapping(value = "/api/integration/elements/list", method = RequestMethod.GET)
    public ResponseEntity<List<ElementViewDao>> getAllElements() {

        List<ElementViewDao> elements = elementListConverter.convert(elementRepository.findAll());

        return new ResponseEntity<>(elements, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/elements/tree/load", method = RequestMethod.GET)
    public ResponseEntity<List<SBSTreeDao>> getSystemBreaksdownStructureTree() {

        Project project = userSessionService.getLastOpenProject();
        List<SBSTreeDao> sbsTree = elementService.getSBSTree(project);

        return new ResponseEntity<>(sbsTree, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/entities/list", method = RequestMethod.GET)
    public ResponseEntity<List<EntityViewDao>> getActiveEntities() {

        List<EntityViewDao> entities = entityListConverter.convert(integrationEntityRepository.findByHasElementsOrderBySortOrderAsc(true));

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/entities/list/all", method = RequestMethod.GET)
    public List<EntityViewDao> getAllIntegrationEntities() {

        List<EntityViewDao> entities = entityListConverter.convert(integrationEntityRepository.findAllByOrderBySortOrderAsc());

        return entities;
    }

    @RequestMapping(value = "/api/integration/entity/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<EntityViewDao> getEntityById(@PathVariable(value = "id") Long entityId) {

        EntityViewDao entity = entityConverter.convert(integrationEntityRepository.findOne(entityId));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/entity/full/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<IntegrationEntity> getFullEntityById(@PathVariable(value = "id") Long entityId) {

        IntegrationEntity entity = integrationEntityRepository.findOne(entityId);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/entity/elements/{id}", method = RequestMethod.GET)
    public ResponseEntity<Element> getEntityElementById(@PathVariable(value = "id") Long elementId) {

        Element element = elementRepository.findOne(elementId);

        return new ResponseEntity<>(element, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/entity/elements/list/{id}", method = RequestMethod.GET)
    public List<ElementViewDao> getEntityElementsById(@PathVariable(value = "id") Long entityId) {

        List<ElementViewDao> elements = elementListConverter.convert(elementRepository.findByEntityOrderBySortOrder(entityRepository.findOne(entityId)));

        return elements;
    }

    @RequestMapping(value = "/api/integration/entity/elements/view/{id}", method = RequestMethod.GET)
    public ModelAndView getEntityElementsViewById(@PathVariable(value = "id") Long entityId) {

        IntegrationEntity entity = entityRepository.findOne(entityId);
        List<IntegrationEntity> entities = entityRepository.findByHasElements(true);
        List<TreeElementDao> tree = elementService.getElementsTreeByEntity(entity);

        String title = "System Interfaces";
        if (entity != null) {
            
            title = title + "  ->  " + entity.getCompany().getCompanyCode() + " - " + entity.getCompany().getCompanyCode();
        }
       
        ModelAndView modelView = new ModelAndView(applicationService.getApplicationTemplateName() + "/views/integration/elements");
        modelView.addObject("tree", tree);
        modelView.addObject("entity", entity);
        modelView.addObject("element", null);
        modelView.addObject("entities", entities);
        modelView.addObject("title", title);

        return modelView;
    }

    @RequestMapping(value = "/api/integration/element/elements/view/{id}", method = RequestMethod.GET)
    public ModelAndView getElementSubelementsViewById(@PathVariable(value = "id") Long elementId) {

        IntegrationEntity entity = null;
        String title = "Element not found or is empty";
        
        Element element = elementRepository.findOne(elementId);
        List<InterfaceViewDao> interfaces = new ArrayList();
        
        if (element != null) {
            
            entity = element.getEntity();
            title = element.getEntity().getCompany().getCompanyCode() + " - " + element.getSbs() + " " + element.getName();
        }
        
        List<IntegrationEntity> entities = entityRepository.findByHasElements(true);
        List<TreeElementDao> tree = elementService.getElementsTreeByParent(element);
        List<Interface> ifaces = interfaceRepository.findByPrimaryElementOrSecondaryElement(element, element);
        
        if (ifaces.size() > 0) {
        
            interfaces = interfaceListConverter.convert(ifaces);
        }
        
        ModelAndView modelView = new ModelAndView(applicationService.getApplicationTemplateName() + "/views/integration/elements");
        modelView.addObject("tree", tree);
        modelView.addObject("entity", entity);
        modelView.addObject("element", element);
        modelView.addObject("entities", entities);
        modelView.addObject("interfaces", interfaces);
        modelView.addObject("title", title);

        return modelView;
    }

    @RequestMapping(value = "/api/integration/element/create/save", method = RequestMethod.POST)
    public ResponseEntity<Element> newEntityElement(@RequestBody ElementCreateDao elementDao) {

        Element element = elementService.createNewElement(elementDao);

        return new ResponseEntity<>(element, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/integration/element/edit/update", method = RequestMethod.POST)
    public ResponseEntity<Element> updateEntityElement(@RequestBody ElementEditDao elementDao) {

        Element element = elementService.updateElement(elementDao);

        return new ResponseEntity<>(element, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/integration/elements/sortorder/update", method = RequestMethod.GET)
    public ResponseEntity<?> updatetElementSortOrder() {

        List<Element> elements = elementRepository.findAll();
        
        elements.forEach((element) -> {
            
            String sbs = element.getSbs();
            element.setSortOrder(sbs != null && !sbs.isEmpty() && sbs.indexOf('.') >= 0 ? Integer.valueOf(sbs.substring(sbs.lastIndexOf('.') + 1)) : Integer.valueOf(sbs));
            elementRepository.save(element); 
        });
        
        return new ResponseEntity<>("Successfully updated elements sort orders.", HttpStatus.OK);
    }
}
