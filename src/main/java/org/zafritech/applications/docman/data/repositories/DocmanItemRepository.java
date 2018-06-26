/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.docman.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.applications.docman.data.domain.DocmanItem;

/**
 *
 * @author LukeS
 */
public interface DocmanItemRepository extends CrudRepository<DocmanItem, Long> {
    
    DocmanItem findByUuId(String uuid);
    
    List<DocmanItem> findByFolder(Folder folder);
    
    List<DocmanItem> findByFolderOrderByItemTitleAsc(Folder folder);
}
