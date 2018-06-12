/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.applications.requirements.data.dao.CommentDao;
import org.zafritech.applications.requirements.data.domain.Item;
import org.zafritech.applications.requirements.data.domain.ItemComment;
import org.zafritech.applications.requirements.data.repositories.ItemCommentRepository;
import org.zafritech.applications.requirements.data.repositories.ItemRepository;
import org.zafritech.applications.requirements.services.CommentService;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCommentRepository commentRepository;
    
    @Override
    public ItemComment saveRquirementsComment(CommentDao commentDao) {
        
        Item item = itemRepository.findOne(commentDao.getItemId());
        User author = userService.loggedInUser();
        
        ItemComment comment = new ItemComment(itemRepository.findOne(commentDao.getItemId()), commentDao.getComment(), author);
        comment = commentRepository.save(comment);
        
        item.getComments().add(comment);
        itemRepository.save(item);
        
        return commentRepository.save(comment);
    }
    
}
