/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.applications.requirements.data.dao.LinkCreateDao;
import org.zafritech.applications.requirements.data.dao.LinkDao;
import org.zafritech.applications.requirements.data.domain.Item;
import org.zafritech.applications.requirements.data.domain.Link;
import org.zafritech.applications.requirements.data.domain.LinkGroup;
import org.zafritech.applications.requirements.data.repositories.ItemRepository;
import org.zafritech.applications.requirements.data.repositories.LinkGroupRepository;
import org.zafritech.applications.requirements.data.repositories.LinkRepository;
import org.zafritech.applications.requirements.services.LinkService;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;

/**
 *
 * @author LukeS
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkGroupRepository linkGroupRepository;
    
    @Override
    public LinkCreateDao getDaoForLinkCreation(Long itemId) {
        
        Item item = itemRepository.findOne(itemId);
        
        LinkCreateDao dao = new LinkCreateDao();
        dao.setSrcDocumentId(item.getDocument().getId()); 
        dao.setSrcItemId(itemId);
        dao.setDstItemId(null);
        dao.setDstDocuments(documentRepository.findByProjectOrderByDocumentNameAsc(item.getDocument().getProject())); 
        dao.setLinkTypes(entityTypeRepository.findByEntityTypeKeyOrderByEntityTypeNameAsc("LINK_TYPE_ENTITY")); 
        
        return dao;
    }

    @Override
    public Link saveRquirementsLink(LinkDao linkDao) {
        
        Document sourceDocument = documentRepository.findOne(linkDao.getSrcDocumentId());
        Document destinationDocument = documentRepository.findOne(linkDao.getDstDocumentId());
        Item sourceItem = itemRepository.findOne(linkDao.getSrcItemId());
        Item destinationItem = itemRepository.findOne(linkDao.getDstItemId());
                
        LinkGroup group = createAndGetLinkGroup(sourceDocument, destinationDocument);
        
        Link link = new Link(sourceItem,
                             sourceDocument,
                             destinationItem,
                             destinationDocument,
                             entityTypeRepository.findOne(linkDao.getLinkTypeId()),
                             linkDao.getLinkComment()); 
        
        link.setLinkGroup(group); 
        link = linkRepository.save(link);
        
        sourceItem.getOutLinks().add(link);
        itemRepository.save(sourceItem);
        
        destinationItem.getInLinks().add(link);
        itemRepository.save(destinationItem);
        
        return link;
    }
    
    private LinkGroup createAndGetLinkGroup(Document source, Document destination) {
        
        LinkGroup group = linkGroupRepository.findFirstBySourceDocumentAndDestinationDocument(source, destination);
        
        if (group == null) {
            
            group = new LinkGroup(source, destination);
            linkGroupRepository.save(group);
        }
        
        return group;
    }
}
