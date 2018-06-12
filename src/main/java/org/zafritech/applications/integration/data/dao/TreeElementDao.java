/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.zafritech.applications.integration.data.domain.Element;

/**
 *
 * @author lukes
 */
public class TreeElementDao {
    
    private Element element;
    
    private List<Element> children  = new ArrayList<>();

    public TreeElementDao() {
        
    }

    public TreeElementDao(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public List<Element> getChildren() {
        return children;
    }

    public void setChildren(List<Element> children) {
        this.children = children;
    }
}
