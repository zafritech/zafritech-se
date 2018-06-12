/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao.generic;

/**
 *
 * @author Luke Sibisi
 */
public class FiveValueDao {
    
    private Long Id;
    
    private String value1;
    
    private String value2;
    
    private String value3;
    
    private String value4;

    public FiveValueDao() {
        
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    @Override
    public String toString() {
        
        return "FiveValueDao{" + "Id=" + Id + ", value1=" + value1 
                + ", value2=" + value2 + ", value3=" + value3 
                + ", value4=" + value4 + '}';
    }
}
