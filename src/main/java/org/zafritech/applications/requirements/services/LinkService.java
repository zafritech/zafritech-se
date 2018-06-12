/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.services;

import org.zafritech.applications.requirements.data.dao.LinkCreateDao;
import org.zafritech.applications.requirements.data.dao.LinkDao;
import org.zafritech.applications.requirements.data.domain.Link;

/**
 *
 * @author LukeS
 */
public interface LinkService {
    
    LinkCreateDao getDaoForLinkCreation(Long itemId);
    
    Link saveRquirementsLink(LinkDao linkDao);
}
