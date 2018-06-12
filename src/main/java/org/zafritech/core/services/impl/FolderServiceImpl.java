/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.FolderDao;
import org.zafritech.core.data.dao.FolderTreeDao;
import org.zafritech.core.data.dao.generic.ValuePairDao;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.services.FolderService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Service
public class FolderServiceImpl implements FolderService {

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private DocumentRepository documentRepository;
     
    @Autowired
    private ProjectRepository projectRepository;
   
    @Autowired
    private InformationClassRepository infoClassRepository;
   
    @Autowired
    private UserService userService;
     
    @Autowired
    private UserSessionService stateService;
    
    @Override
    public Folder createFolder(FolderDao folderDao) {
        
        Folder folder = folderRepository.save(new Folder(
                folderDao.getFolderName(),
                entityTypeRepository.findOne(folderDao.getFolderTypeId()),
                folderRepository.findOne(folderDao.getParentId()),
                projectRepository.findOne(folderDao.getProjectId()) 
        )); 
        
        return folder;
    }
    
    @Override
    public Folder renameFolder(ValuePairDao vpDao, Long id) {
        
        Folder folder = folderRepository.findOne(id); 
        folder.setFolderName(vpDao.getItemName()); 
        
        return folderRepository.save(folder);
    }
    
    @Override
    public List<FolderTreeDao> getProjectFolders(Project project) {
        
        List<Folder> folders = folderRepository.findByProjectOrderBySortIndexAsc(project);
        List<FolderTreeDao> folderTree = new ArrayList<>();
        
        for (Folder folder : folders) {
            
            if (folder.getFolderType().equals(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_PROJECT"))) { 
                
                folderTree.add(new FolderTreeDao(
                
                        folder.getId(),
                        (folder.getParent() != null) ? folder.getParent().getId() : 0L,
                        project.getProjectNumber() + " " + folder.getFolderName(),
                        (folder.getParent() == null),
                        true,
                        true,
                        "/images/icons/db-icon.png",
                        project.getId()
                ));
                
            } else {
                
                folderTree.add(new FolderTreeDao(
                
                        folder.getId(),
                        (folder.getParent() != null) ? folder.getParent().getId() : 0L,
                        folder.getFolderName(),
                        (folder.getParent() == null),
                        true,
                        true
                ));
            }
        }
        
        return folderTree;
    }

    @Override
    public List<FolderTreeDao> getProjectDocuments(Project project) {
        
        List<Document> docs = documentRepository.findByProject(project);
        List<FolderTreeDao> projectDocs = new ArrayList<>();
        
        docs.forEach((doc) -> {
            projectDocs.add(new FolderTreeDao(
                    
                    doc.getId() + 5000,             // Prevent TreeNodes id classes with folders
                    doc.getFolder().getId(),
                    doc.getIdentifier(),
                    false,
                    false,
                    true,
                    doc.getId()
            ));
        });
        
        return projectDocs;
    }

    @Override
    public Folder duplicateTree(Long id) {
        
        Folder folder = folderRepository.findOne(id);
        Folder parent = folder.getParent();
        
        return copyFolder(folder, parent);
    }
    
    private Folder copyFolder(Folder rootFolder, Folder parentFolder) {
        
        List<Document> documents = documentRepository.findByFolder(rootFolder); 
        List<Folder> folders = folderRepository.findByParentOrderBySortIndexAsc(rootFolder);
        
        Folder nFolder = saveCopiedFolder(rootFolder, parentFolder, documents);
        
        folders.forEach((folder) -> {
            copyFolder(folder, nFolder);
        });
        
        return nFolder;
    }
    
    @Override
    public List<FolderTreeDao> getProjectFoldersTree(Long projectId) {
        
        List<FolderTreeDao> foldersTree = new ArrayList<>();
        Project project = projectRepository.findOne(projectId);
        
        List<FolderTreeDao> folders = getProjectFolders(project);
        List<FolderTreeDao> docs = getProjectDocuments(project);
        
        foldersTree.addAll(folders);
        foldersTree.addAll(docs);
        
        return foldersTree;
    }
    
    @Override
    public List<FolderTreeDao> getOpenProjectFoldersTree() {
      List<FolderTreeDao> foldersTree = new ArrayList<>();
        
        List<Project> openProjects = stateService.getOpenProjects();
        
        openProjects.forEach((project) -> {
            List<FolderTreeDao> folders = getProjectFolders(project);
            List<FolderTreeDao> docs = getProjectDocuments(project);

            foldersTree.addAll(folders);
            foldersTree.addAll(docs);
        });
        
        return foldersTree;
    }
    
    private Folder saveCopiedFolder(Folder oFolder, Folder parentFolder, List<Document> documents) {
        
        Folder folder = folderRepository.save(new Folder(
        
                oFolder.getFolderName() + " - Copy",
                oFolder.getFolderType(),
                parentFolder,
                oFolder.getProject(),
                oFolder.getSortIndex()
        ));
        
        documents.stream().map((doc) -> new Document(
                
                doc.getIdentifier() + "-COPY",
                doc.getDocumentName() + " - Empty",
                "",
                doc.getDocumentType(),
                doc.getContentDescriptor(),
                doc.getProject(),
                doc.getWbs(),
                folder,
                infoClassRepository.findByClassCode("INFO_OFFICIAL"),
                "0A"
        )).map((newDoc) -> {
            newDoc.setOwner(userService.loggedInUser());
            return newDoc;            
        }).forEachOrdered((newDoc) -> {
            documentRepository.save(newDoc);
        });
        
        return folder;
    }

    @Override
    public List<FolderTreeDao> getLibraryFolders() {
        
        List<FolderTreeDao> foldersTree = new ArrayList<>();
        
        EntityType folderType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY");
        List<Folder> folders = folderRepository.findByFolderTypeOrderBySortIndexAsc(folderType);
        
        folders.forEach((folder) -> {
            if (folder.getParent() == null) { 
                
                foldersTree.add(new FolderTreeDao(
                
                        folder.getId(),
                        (folder.getParent() != null) ? folder.getParent().getId() : 0L,
                        folder.getFolderName(),
                        (folder.getParent() == null),
                        true,
                        true,
                        "/images/icons/books-icon.png",
                        null
                ));
                
            } else {

                foldersTree.add(new FolderTreeDao(

                        folder.getId(),
                        (folder.getParent() != null) ? folder.getParent().getId() : 0L,
                        folder.getFolderName(),
                        (folder.getParent() == null),
                        true,
                        true
                ));
            }
        });
       
        return foldersTree;
    }

    @Override
    public Integer getFolderContentsCount(Long folderId) {
        
        Folder parent =  folderRepository.findOne(folderId);
        List<Folder> folders = folderRepository.findByParent(parent);
        List<Document> documents = documentRepository.findByFolder(parent);
        
        return folders.size() + documents.size();
    }
}
