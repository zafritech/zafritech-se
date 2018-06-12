/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.applications.requirements.data.dao.SearchDao;
import org.zafritech.applications.requirements.search.ItemSearch;
import org.zafritech.applications.requirements.services.ItemSearchService;

/**
 *
 * @author LukeS
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ItemSearch itemSearch;
    
    @Override
    public SearchDao search(String q, Integer size, Integer page) {
            
        return itemSearch.search(q, size, page);
    }
}
