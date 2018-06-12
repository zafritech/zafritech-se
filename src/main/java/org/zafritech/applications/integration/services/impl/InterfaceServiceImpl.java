/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.data.dao.InterfaceCommentDao;
import org.zafritech.applications.integration.data.dao.InterfaceCommentUpdateDao;
import org.zafritech.applications.integration.data.dao.InterfaceCreateDao;
import org.zafritech.applications.integration.data.dao.InterfaceEditDao;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.data.domain.InterfaceIssueComment;
import org.zafritech.applications.integration.data.domain.InterfaceType;
import org.zafritech.applications.integration.data.repositories.ElementRepository;
import org.zafritech.applications.integration.data.repositories.IntegrationEntityRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceIssueCommentRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceIssueRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;
import org.zafritech.applications.integration.enums.InterfaceStatus;
import org.zafritech.applications.integration.services.InterfaceService;
import org.zafritech.core.data.repositories.ProjectCompanyRoleRepository;
import org.zafritech.core.services.ExcelService;

/**
 *
 * @author lukes
 */
@Service
public class InterfaceServiceImpl implements InterfaceService {

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private IntegrationEntityRepository entityRepository;

    @Autowired
    private InterfaceRepository interfaceRepository;

    @Autowired
    private InterfaceIssueRepository interfaceIssueRepository;

    @Autowired
    private ProjectCompanyRoleRepository companyRoleRepository;

    @Autowired
    private InterfaceIssueCommentRepository commentRepository;

    @Autowired
    private InterfaceIssueRepository issueRepository;
   
    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private ExcelService excelService;

    private static Map<String, CellStyle> styles;

    @Override
    public Interface saveInterface(InterfaceCreateDao dao) {

        IntegrationEntity primaryEntity = entityRepository.findOne(dao.getPrimaryEntityId());
        IntegrationEntity secondaryEntity = entityRepository.findOne(dao.getSecondaryEntityId());
        Element primaryElement = elementRepository.findOne(dao.getPrimaryElementId());
        Element secondaryElement = elementRepository.findOne(dao.getSecondaryElementId());
        String interfaceTitle = (dao.getInterfaceTitle() != null && !dao.getInterfaceTitle().isEmpty()) ? dao.getInterfaceTitle() : null;
        String interfaceDescription = (dao.getInterfaceDescription() != null && !dao.getInterfaceDescription().isEmpty()) ? dao.getInterfaceDescription() : null;
                
        String systemId = interfaceService.getNextSystemIdentifier(primaryEntity, secondaryEntity);

        Interface interFace = new Interface(systemId,
                                            primaryEntity.getProject(),
                                            primaryElement,
                                            secondaryElement,
                                            entityRepository.findOne(dao.getPrimaryEntityId()),
                                            entityRepository.findOne(dao.getSecondaryEntityId()),
                                            dao.getInterfaceLevel(),
                                            interfaceTitle,
                                            interfaceDescription);

        interFace = interfaceRepository.save(interFace);

        return interFace;
    }

    @Override
    public Interface updateInterface(InterfaceEditDao dao) {
        
        IntegrationEntity primaryEntity = entityRepository.findOne(dao.getPrimaryEntityId());
        IntegrationEntity secondaryEntity = entityRepository.findOne(dao.getSecondaryEntityId());
        
        Interface iface = interfaceRepository.findOne(dao.getId());
        
        // Update system identifier to reflect the new entities involved if necessary
        String systemId = iface.getSystemId();
        String primaryIdent = primaryEntity.getCompanyCode();
        String secondaryIdent = secondaryEntity.getCompanyCode();
        String numericIdent = systemId.substring(systemId.length() - 5);
                
        // Update the interface with new data
        iface.setSystemId(primaryIdent + secondaryIdent + numericIdent);
        iface.setPrimaryEntity(primaryEntity); 
        iface.setSecondaryEntity(secondaryEntity); 
        iface.setPrimaryElement(elementRepository.findOne(dao.getPrimaryElementId()));
        iface.setSecondaryElement(elementRepository.findOne(dao.getSecondaryElementId())); 
        iface.setStatus(InterfaceStatus.valueOf(dao.getStatus()));
        iface.setInterfaceLevel(dao.getInterfaceLevel());
        iface.setInterfaceTitle(dao.getInterfaceTitle());
        iface.setInterfaceDescription(dao.getInterfaceDescription()); 
        
        iface = interfaceRepository.save(iface);
        
        return iface;
    }
    
    @Override
    public InterfaceIssueComment createNewInterfaceIssueComment(InterfaceCommentDao commentDao) {
        
        Long issueId = commentDao.getIssueId();
        String issueStatus = commentDao.getStatus();
        String issueComment = commentDao.getComment();
        
        if((issueStatus == null || issueStatus.isEmpty()) ||
           (issueComment == null || issueComment.isEmpty())) {
            
            return null;
        }
        
        InterfaceIssueComment comment = new InterfaceIssueComment(issueRepository.findOne(issueId),  
                                                                  issueComment, 
                                                                  companyRoleRepository.findOne(commentDao.getActionById()),
                                                                  InterfaceStatus.valueOf(issueStatus)); 
        
        comment.setCommentAction(commentDao.getAction());
        comment.setActionBy(companyRoleRepository.findOne(commentDao.getActionById()).getDiplayCode());
        
        InterfaceIssue issue = issueRepository.findOne(issueId);
        issue.getComments().add(comment);
        issueRepository.save(issue);
        
        return comment;
    }
    
    @Override
    public InterfaceIssueComment updateInterfaceIssueComment(InterfaceCommentUpdateDao dao) {
        
        InterfaceIssueComment comment = commentRepository.findOne(dao.getCommentId());
        
        comment.setComment(dao.getComment());
        comment.setCommentAction(dao.getAction());
        comment.setCommentBy(companyRoleRepository.findOne(dao.getCommentById())); 
        comment.setStatus(InterfaceStatus.valueOf(dao.getStatus())); 
        comment.setActionBy(dao.getActionBy()); 

        try {
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            date = formatter.parse(dao.getCreationDate());
            comment.setCreationDate(date);
            
        } catch (ParseException ex) {
            
            Logger.getLogger(InterfaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        comment = commentRepository.save(comment);
        
        return comment;
    }
    
    @Override
    public List<Interface> findByLevels(Element element, String s) {

        List<Integer> levels = new ArrayList<>();
        Integer index;

        for (int i = 4; i > 0; i--) {

            index = 4 - i;
            String bit = s.substring(index, index + 1);

            if (bit.equalsIgnoreCase("1")) {

                levels.add(i);
            }
        }

        List<Interface> interfaces = interfaceRepository.findByPrimaryElementAndInterfaceLevelInOrSecondaryElementAndInterfaceLevelIn(element, levels, element, levels);

        return interfaces;
    }

    @Override
    public boolean isSelfInterfacing(InterfaceCreateDao dao) {

        return (dao.getPrimaryElementId().equals(dao.getSecondaryElementId()));
    }

    @Override
    public boolean isSelfInterfacing(InterfaceEditDao dao) {

        return (dao.getPrimaryElementId().equals(dao.getSecondaryElementId()));
    }

    @Override
    public boolean isDuplicateInterface(InterfaceCreateDao dao) {

        Interface forwardIF = interfaceRepository.findFirstByPrimaryElementAndSecondaryElement(elementRepository.findOne(dao.getPrimaryElementId()),
                elementRepository.findOne(dao.getSecondaryElementId()));

        Interface reverseIF = interfaceRepository.findFirstByPrimaryElementAndSecondaryElement(elementRepository.findOne(dao.getSecondaryElementId()),
                elementRepository.findOne(dao.getPrimaryElementId()));

        return (forwardIF != null || reverseIF != null);
    }

    @SuppressWarnings("resource")
    @Override
    public XSSFWorkbook DownloadExcel() throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        // Add worksheets
        workbook = addSystemElementsWorksheet(workbook);
        workbook = addInterfacesWorksheet(workbook);
        workbook = addInterfaceIssuesWorksheet(workbook);
        workbook = addIssueCommentsWorksheet(workbook);

        return workbook;
    }

    private XSSFWorkbook addSystemElementsWorksheet(XSSFWorkbook workbook) {

        styles = excelService.getExcelStyles(workbook);

        XSSFSheet elementsSheet = workbook.createSheet("System Elements");

        // Column Widths
        elementsSheet.setColumnWidth(0, 2500);       // SBS
        elementsSheet.setColumnWidth(1, 20000);      // Title
        elementsSheet.setColumnWidth(2, 5000);       // Entity

        Row headerRow = elementsSheet.createRow(0);
        headerRow.setHeightInPoints(32);

        Cell cellId = headerRow.createCell(0);
        cellId.setCellValue("SBS");
        cellId.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        Cell cellTitle = headerRow.createCell(1);
        cellTitle.setCellValue("System Element Name");
        cellTitle.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellLevel = headerRow.createCell(2);
        cellLevel.setCellValue("Owner Entity");
        cellLevel.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        List<IntegrationEntity> entities = entityRepository.findByHasElements(true);

        int rowCount = 0;

        for (IntegrationEntity entity : entities) {

            Row row = elementsSheet.createRow(++rowCount);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue("#");
            cell0.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell1 = row.createCell(1);
            if (entity.getCompany().getCompanyName() instanceof String) {
                cell1.setCellValue(entity.getCompany().getCompanyName());
            }
            cell1.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell2 = row.createCell(2);
            if (entity.getCompany().getCompanyCode() instanceof String) {
                cell2.setCellValue(entity.getCompany().getCompanyCode());
            }
            cell2.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));

            List<Element> rootElements = elementRepository.findByEntityAndParentOrderBySortOrder(entity, null);

            for (Element rootElement : rootElements) {

                Row rootRow = elementsSheet.createRow(++rowCount);

                Cell rootCell0 = rootRow.createCell(0);
                if (rootElement.getSbs() instanceof String) {
                    rootCell0.setCellValue(rootElement.getSbs());
                }
                rootCell0.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                Cell rootCell1 = rootRow.createCell(1);
                if (rootElement.getName() instanceof String) {
                    rootCell1.setCellValue(rootElement.getName());
                }
                rootCell1.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                Cell rootCell2 = rootRow.createCell(2);
                if (entity.getCompany().getCompanyCode() instanceof String) {
                    rootCell2.setCellValue(entity.getCompany().getCompanyCode());
                }
                rootCell2.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));

                List<Element> children = elementRepository.findByParent(rootElement);

                if (!children.isEmpty()) {

                    for (Element child : children) {

                        Row childRow = elementsSheet.createRow(++rowCount);

                        Cell childCell0 = childRow.createCell(0);
                        if (child.getSbs() instanceof String) {
                            childCell0.setCellValue(child.getSbs());
                        }
                        childCell0.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                        Cell childCell1 = childRow.createCell(1);
                        if (child.getName() instanceof String) {
                            childCell1.setCellValue(child.getName());
                        }
                        childCell1.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                        Cell childCell2 = childRow.createCell(2);
                        if (entity.getCompany().getCompanyCode() instanceof String) {
                            childCell2.setCellValue(entity.getCompany().getCompanyCode());
                        }
                        childCell2.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));
                    }
                }
            }

        }

        elementsSheet.setAutoFilter(CellRangeAddress.valueOf("C1:C1"));

        elementsSheet.createFreezePane(0, 1);

        return workbook;
    }

    private XSSFWorkbook addInterfacesWorksheet(XSSFWorkbook workbook) {

        styles = excelService.getExcelStyles(workbook);

        XSSFSheet interfaceSheet = workbook.createSheet("System Interfaces");

        // Column Widths
        interfaceSheet.setColumnWidth(0, 2500);       // Id
        interfaceSheet.setColumnWidth(1, 12000);      // Title
        interfaceSheet.setColumnWidth(2, 2500);       // Id
        interfaceSheet.setColumnWidth(3, 10000);      // Primary Element
        interfaceSheet.setColumnWidth(4, 5000);       // Primary Entity
        interfaceSheet.setColumnWidth(5, 10000);      // Seccondary Element
        interfaceSheet.setColumnWidth(6, 5000);       // Seccondary Entity
        interfaceSheet.setColumnWidth(7, 12000);      // Description (TEXT)
        interfaceSheet.setColumnWidth(8, 8000);      // ICD Reference
        interfaceSheet.setColumnWidth(9, 4000);       // Status
        interfaceSheet.setColumnWidth(10, 5000);       // Type
        interfaceSheet.setColumnWidth(11, 12000);     // Comments (TEXT)

        Row headerRow = interfaceSheet.createRow(0);
        headerRow.setHeightInPoints(32);

        Cell cellId = headerRow.createCell(0);
        cellId.setCellValue("ID");
        cellId.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        Cell cellTitle = headerRow.createCell(1);
        cellTitle.setCellValue("Interface Title");
        cellTitle.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellLevel = headerRow.createCell(2);
        cellLevel.setCellValue("Level");
        cellLevel.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        Cell cellPrimaryElement = headerRow.createCell(3);
        cellPrimaryElement.setCellValue("Primasy SBS Element");
        cellPrimaryElement.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellPrimaryEntity = headerRow.createCell(4);
        cellPrimaryEntity.setCellValue("Primary Entity");
        cellPrimaryEntity.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        Cell cellSecondaryElement = headerRow.createCell(5);
        cellSecondaryElement.setCellValue("Secondary SBS Element");
        cellSecondaryElement.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellSecondaryEntity = headerRow.createCell(6);
        cellSecondaryEntity.setCellValue("Secondary Entity");
        cellSecondaryEntity.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        Cell cellDescription = headerRow.createCell(7);
        cellDescription.setCellValue("Description");
        cellDescription.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell referenceICD = headerRow.createCell(8);
        referenceICD.setCellValue("ICD Reference");
        referenceICD.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellStatus = headerRow.createCell(9);
        cellStatus.setCellValue("Status");
        cellStatus.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        Cell cellInterfaceType = headerRow.createCell(10);
        cellInterfaceType.setCellValue("Interface Type");
        cellInterfaceType.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellComments = headerRow.createCell(11);
        cellComments.setCellValue("Interface Notes");
        cellComments.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        List<Interface> interfaces = interfaceRepository.findAll();

        int rowCount = 0;

        for (Interface interf : interfaces) {

            Row row = interfaceSheet.createRow(++rowCount);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(interf.getId());
            cell0.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));

            Cell cell1 = row.createCell(1);
            if (interf.getInterfaceTitle() instanceof String) {
                cell1.setCellValue(interf.getInterfaceTitle());
            }
            cell1.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(interf.getInterfaceLevel());
            cell2.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));

            Cell cell3 = row.createCell(3);
            if (interf.getPrimaryElement() != null) {
                if (interf.getPrimaryElement().getName() instanceof String) {
                    cell3.setCellValue(interf.getPrimaryElement().getName());
                }
            } else {
                cell3.setCellValue("");
            }
            cell3.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell4 = row.createCell(4);
            if (interf.getPrimaryEntity() != null) {
                if (interf.getPrimaryEntity().getCompany().getCompanyCode() instanceof String) {
                    cell4.setCellValue(interf.getPrimaryEntity().getCompany().getCompanyCode());
                }
            } else {
                cell4.setCellValue("");
            }
            cell4.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));

            Cell cell5 = row.createCell(5);
            if (interf.getSecondaryElement() != null) {
                if (interf.getSecondaryElement().getName() instanceof String) {
                    cell5.setCellValue(interf.getSecondaryElement().getName());
                }
            } else {
                cell5.setCellValue("");
            }
            cell5.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell6 = row.createCell(6);
            if (interf.getSecondaryEntity() != null) {
                if (interf.getSecondaryEntity().getCompany().getCompanyCode() instanceof String) {
                    cell6.setCellValue(interf.getSecondaryEntity().getCompany().getCompanyCode());
                }
            } else {
                cell6.setCellValue("");
            }
            cell6.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));

            Cell cell7 = row.createCell(7);
            if (interf.getInterfaceDescription() instanceof String) {
                cell7.setCellValue(ConvertHTML2Text(interf.getInterfaceDescription()));
            }
            cell7.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell8 = row.createCell(8);
            cell8.setCellValue("");     // ICD Reference
            cell8.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));

            Cell cell9 = row.createCell(9);
            if (interf.getStatus().name() instanceof String) {

                if (null == interf.getStatus()) {

                    cell9.setCellValue(interf.getStatus().name().replace("INTERFACE_STATUS_", ""));
                    cell9.setCellStyle((CellStyle) styles.get("OrangeCenterAlign"));

                } else {
                    switch (interf.getStatus()) {

                        case INTERFACE_STATUS_CLOSED:

                            cell9.setCellValue("CLOSED");
                            cell9.setCellStyle((CellStyle) styles.get("GreenCenterAlign"));
                            break;

                        case INTERFACE_STATUS_OPEN:

                            cell9.setCellValue("OPEN");
                            cell9.setCellStyle((CellStyle) styles.get("RedCenterAlign"));
                            break;

                        default:
                            cell9.setCellValue(interf.getStatus().name().replace("INTERFACE_STATUS_", ""));
                            cell9.setCellStyle((CellStyle) styles.get("OrangeCenterAlign"));
                            break;
                    }
                }
            }

            String strTypes = "";

            if (interf.getInterfaceTypes() != null) {

                for (InterfaceType type : interf.getInterfaceTypes()) {
                    strTypes = strTypes + type.getTypeName() + "\n";
                }
            }

            Cell cell10 = row.createCell(10);
            cell10.setCellValue(strTypes);     // Interface Types TBD
            cell10.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell11 = row.createCell(11);
            if (interf.getInterfaceNotes() != null) {
                if (interf.getInterfaceNotes() instanceof String) {
                    cell11.setCellValue(interf.getInterfaceNotes());
                }
            } else {
                cell11.setCellValue("");
            }
            cell11.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));
        }

        interfaceSheet.setAutoFilter(CellRangeAddress.valueOf("A1:L1"));

        interfaceSheet.createFreezePane(0, 1);

        return workbook;
    }

    private XSSFWorkbook addInterfaceIssuesWorksheet(XSSFWorkbook workbook) {

        styles = excelService.getExcelStyles(workbook);

        XSSFSheet issuesSheet = workbook.createSheet("Interface Issues");

        // Column Widths
        issuesSheet.setColumnWidth(0, 5000);       // Issue Id
        issuesSheet.setColumnWidth(1, 5000);       // Issue Date
        issuesSheet.setColumnWidth(2, 12000);      // Interface
        issuesSheet.setColumnWidth(3, 12000);      // Issue Title
        issuesSheet.setColumnWidth(4, 18000);      // Issue Description
        issuesSheet.setColumnWidth(5, 4000);       // Status

        Row headerRow = issuesSheet.createRow(0);
        headerRow.setHeightInPoints(32);

        Cell cellId = headerRow.createCell(0);
        cellId.setCellValue("ID");
        cellId.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        Cell cellCreationDate = headerRow.createCell(1);
        cellCreationDate.setCellValue("Date Created");
        cellCreationDate.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellInterface = headerRow.createCell(2);
        cellInterface.setCellValue("Interface");
        cellInterface.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellTitle = headerRow.createCell(3);
        cellTitle.setCellValue("Issue Title");
        cellTitle.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellDescription = headerRow.createCell(4);
        cellDescription.setCellValue("Issue Description");
        cellDescription.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellStatus = headerRow.createCell(5);
        cellStatus.setCellValue("Status");
        cellStatus.setCellStyle((CellStyle) styles.get("HeaderCenterAlign"));

        List<InterfaceIssue> issues = interfaceIssueRepository.findAllByOrderBySystemIdAsc();

        int rowCount = 0;

        for (InterfaceIssue issue : issues) {

            Row row = issuesSheet.createRow(++rowCount);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(issue.getSystemId());
            cell0.setCellStyle((CellStyle) styles.get("BodyCenterAlign"));

            Cell cell1 = row.createCell(1);
            if (issue.getCreationDate() instanceof Date) {
                cell1.setCellValue(issue.getCreationDate());
            }
            cell1.setCellStyle((CellStyle) styles.get("BodyLeftAlignDate"));

            Cell cell2 = row.createCell(2);
            if (issue.getIssueInterface().getInterfaceTitle() instanceof String) {
                cell2.setCellValue(issue.getIssueInterface().getInterfaceTitle());
            }
            cell2.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell3 = row.createCell(3);
            if (issue.getIssueTitle() instanceof String) {
                cell3.setCellValue(issue.getIssueTitle());
            }
            cell3.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell4 = row.createCell(4);
            if (issue.getIssueDescription() instanceof String) {
                cell4.setCellValue(ConvertHTML2Text(issue.getIssueDescription()));
            }
            cell4.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

            Cell cell5 = row.createCell(5);
            if (issue.getStatus().name() instanceof String) {

                if (null == issue.getStatus()) {

                    cell5.setCellValue(issue.getStatus().name().replace("INTERFACE_STATUS_", ""));
                    cell5.setCellStyle((CellStyle) styles.get("OrangeCenterAlign"));

                } else {
                    switch (issue.getStatus()) {

                        case INTERFACE_STATUS_CLOSED:

                            cell5.setCellValue("CLOSED");
                            cell5.setCellStyle((CellStyle) styles.get("GreenCenterAlign"));
                            break;

                        case INTERFACE_STATUS_OPEN:

                            cell5.setCellValue("OPEN");
                            cell5.setCellStyle((CellStyle) styles.get("RedCenterAlign"));
                            break;

                        default:
                            cell5.setCellValue(issue.getStatus().name().replace("INTERFACE_STATUS_", ""));
                            cell5.setCellStyle((CellStyle) styles.get("OrangeCenterAlign"));
                            break;
                    }
                }
            }
        }

        issuesSheet.setAutoFilter(CellRangeAddress.valueOf("F1:F1"));

        issuesSheet.createFreezePane(0, 1);

        return workbook;
    }

    private XSSFWorkbook addIssueCommentsWorksheet(XSSFWorkbook workbook) {

        styles = excelService.getExcelStyles(workbook);

        XSSFSheet issueCommentsSheet = workbook.createSheet("Issue Comments");

        // Column Widths
        issueCommentsSheet.setColumnWidth(0, 5000);       	// Issue ID
        issueCommentsSheet.setColumnWidth(1, 5000);      	// Creation Date
        issueCommentsSheet.setColumnWidth(2, 16500);       	// Comment
        issueCommentsSheet.setColumnWidth(3, 4000);      	// Comment By
        issueCommentsSheet.setColumnWidth(4, 4000);      	// Status
        issueCommentsSheet.setColumnWidth(5, 8000);      	// Action
        issueCommentsSheet.setColumnWidth(6, 4000);      	// Action By
        issueCommentsSheet.setColumnWidth(7, 5000);      	// Target Date

        Row headerRow = issueCommentsSheet.createRow(0);
        headerRow.setHeightInPoints(32);

        Cell cellId = headerRow.createCell(0);
        cellId.setCellValue("Issue ID");
        cellId.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellCreationDate = headerRow.createCell(1);
        cellCreationDate.setCellValue("Date Created");
        cellCreationDate.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellComment = headerRow.createCell(2);
        cellComment.setCellValue("Comment");
        cellComment.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellCommentBy = headerRow.createCell(3);
        cellCommentBy.setCellValue("Comment By");
        cellCommentBy.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellStatus = headerRow.createCell(4);
        cellStatus.setCellValue("Status");
        cellStatus.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellAction = headerRow.createCell(5);
        cellAction.setCellValue("Action");
        cellAction.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellActionBy = headerRow.createCell(6);
        cellActionBy.setCellValue("Action By");
        cellActionBy.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        Cell cellTargetDate = headerRow.createCell(7);
        cellTargetDate.setCellValue("Target Date");
        cellTargetDate.setCellStyle((CellStyle) styles.get("HeaderLeftAlign"));

        List<InterfaceIssue> issues = interfaceIssueRepository.findAllByOrderBySystemIdAsc();

        int rowCount = 0;

        for (InterfaceIssue issue : issues) {

            // Issue has comments
            if (issue.getComments().size() > 0) {

                List<InterfaceIssueComment> comments = commentRepository.findByInterfaceIssueOrderByCreationDateDesc(issue);

                for (InterfaceIssueComment comment : comments) {

                    Row row = issueCommentsSheet.createRow(++rowCount);

                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(issue.getSystemId());
                    cell0.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                    Cell cell1 = row.createCell(1);
                    if (comment.getTargetDate() instanceof Date) {
                        cell1.setCellValue(comment.getTargetDate());
                    }
                    cell1.setCellStyle((CellStyle) styles.get("BodyLeftAlignDate"));

                    Cell cell2 = row.createCell(2);
                    if (comment.getComment() instanceof String) {
                        cell2.setCellValue(ConvertHTML2Text(comment.getComment()));
                    }
                    cell2.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                    Cell cell3 = row.createCell(3);
                    if (comment.getCommentBy().getCompany().getCompanyCode() instanceof String) {
                        cell3.setCellValue(comment.getCommentBy().getCompany().getCompanyCode());
                    }
                    cell3.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                    Cell cell4 = row.createCell(4);
                    if (comment.getStatus().name() instanceof String) {
                        if (null == comment.getStatus()) {

                            cell4.setCellValue(comment.getStatus().name().replace("INTERFACE_STATUS_", ""));
                            cell4.setCellStyle((CellStyle) styles.get("OrangeCenterAlign"));

                        } else {
                            switch (comment.getStatus()) {

                                case INTERFACE_STATUS_CLOSED:

                                    cell4.setCellValue("CLOSED");
                                    cell4.setCellStyle((CellStyle) styles.get("GreenCenterAlign"));
                                    break;

                                case INTERFACE_STATUS_OPEN:

                                    cell4.setCellValue("OPEN");
                                    cell4.setCellStyle((CellStyle) styles.get("RedCenterAlign"));
                                    break;

                                default:
                                    cell4.setCellValue(comment.getStatus().name().replace("INTERFACE_STATUS_", ""));
                                    cell4.setCellStyle((CellStyle) styles.get("OrangeCenterAlign"));
                                    break;
                            }
                        }
                    }
                    cell4.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue("");
                    cell5.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                    Cell cell6 = row.createCell(6);
                    cell6.setCellValue("");
                    cell6.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));

                    Cell cell7 = row.createCell(7);
                    cell7.setCellValue("");
                    cell7.setCellStyle((CellStyle) styles.get("BodyLeftAlignWrapText"));
                }
            }
        }

        issueCommentsSheet.setAutoFilter(CellRangeAddress.valueOf("A1:H1"));

        issueCommentsSheet.createFreezePane(0, 1);

        return workbook;
    }

    @Override
    public String getNextSystemIdentifier(IntegrationEntity primary, IntegrationEntity secondary) {

        String identifier   = primary.getCompanyCode() + secondary.getCompanyCode();
        String format       = "%05d";
        Integer next        = 1;

        Interface last = interfaceRepository.findFirstByOrderByIdDesc();

        if (last != null) {

            Integer length = last.getSystemId().length();
            String lastNumeric = last.getSystemId().substring(length - 5);
            next = Integer.parseInt(lastNumeric) + 1;
        }

        identifier = identifier + String.format(format, next);

        return identifier;
    }

    private static String ConvertHTML2Text(String html) {

        String text = html.replaceAll("<br>", "\n").replaceAll("<br/>", "\n").replaceAll("</br>", "\n");

        // Remove the unnecessary first line (paragraph first tage) break
        if (text.startsWith("<p>")) {

            text = text.replaceAll("<p>", "");
        }

        // Clean the html tags out
        text = text.replaceAll("&nbsp;", " ");
        text = text.replaceAll("</p>", "\n\n").replaceAll("<p>", "\n");
        text = text.replaceAll("\\<[^>]*>", "");

        // Remove unnecessary last line break
        if (text.endsWith("\n")) {

            int start = text.lastIndexOf("\n");
            text = text.substring(0, start);
        }

        return text;
    }
}
