/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.services;

import org.zafritech.applications.requirements.data.dao.SearchDao;

/**
 *
 * @author LukeS
 */
public interface ItemSearchService {
    
    SearchDao search(String q, Integer size, Integer page);
}
