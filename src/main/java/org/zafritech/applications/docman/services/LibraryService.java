/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.docman.services;

import java.io.IOException;
import java.text.ParseException;
import org.zafritech.applications.docman.data.dao.LibraryItemDao;
import org.zafritech.applications.docman.data.domain.LibraryItem;

/**
 *
 * @author LukeS
 */
public interface LibraryService {
    
    LibraryItem createLibraryItem(LibraryItemDao dao) throws IOException, ParseException;
}
