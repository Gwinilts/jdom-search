/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatajase;

import java.io.Serializable;

/**
 *
 * @author gwion
 * 
 * Really this is a DOM, but we called it the Jocument, lol
 * 
 */
public class Jocument extends AbstractJode implements Serializable {
    
    // The root element is the database
    private AbstractJode rootElement;
    
    
    /**
     * the Jocument is a specialized DOM designed for storing data
     */
    public Jocument() {
        rootElement = new JNode(JodeName.JRoot);
        rootElement.parent(this);
    }
    
    /**
     * getElementById will probably never be used as ids are distributed from zero from an assignor JodeList
     * @param id the id to find
     * @return The first element that had this id
     */
    
    public AbstractJode getElementById(int id) {
        TestJode search = new TestJode();
        search.id(id);
        
        if (search.matches(rootElement)) return rootElement;
        
        JodeList searchMatches = rootElement.childNodes().allMatches(search, true);
        
        return searchMatches.getFirst();
    }
    
    /**
     * getElementsByName, useful for finding only rows or tables etc
     * @param name a JodeName
     * @return A JodeLiet containing all elements that had name
     */
    
    public JodeList getElementsByName(JodeName name) {
        TestJode search = new TestJode();
        search.name(JodeName.JTable);
        
        JodeList matches = rootElement.childNodes().allMatches(search, true);
        
        if (search.matches(rootElement)) matches.add(rootElement);
        
        return matches;
    }
    
    /**
     * search finds all elements that match a matcher
     * @param tableName the table to search under
     * @param matcher a TestJode used to match elements
     * @return a jodeList containing the matches
     */
    
    public JodeList search(String tableName, TestJode matcher) {
        JodeList tables = getElementsByName(JodeName.JTable);
        JodeList matches = new JodeList(false);
        
        for (AbstractJode j: tables) {
            if (j.attr("tableName").equals(new Attr(tableName))) {
                System.out.println("An element was found");
                matches = j.childNodes().allMatches(matcher, true);
            }
        }
        
        return matches;
    }
    
    public AbstractJode get(String tableName, TestJode matcher) {
        return search(tableName, matcher).getFirst();
    }
    
    public AbstractJode getTable(String tableName) {
        JodeList tables = getElementsByName(JodeName.JTable);
        for (AbstractJode j: tables) {
            if (j.attr("tableName") != null) {
                if (j.attr("tableName").equals(new Attr(tableName))) return j;
            }
        }
        return null;
    }
    
    
    public void addTable(String tableName) {
        AbstractJode j;
        rootElement.appendChild((j = new JNode(JodeName.JTable)));
        j.attr("tableName", tableName);
    }
    
    public void addTable(String tableName, boolean autoInc) {
        if (autoInc) {
            AbstractJode j;
            rootElement.appendChild((j = new JNode(JodeName.JTable)));
            j.attr("tableName", tableName);
            j.childNodes().setAssignor();
        } else {
            addTable(tableName);
        }
    }
    
    public void add(String tableName, AbstractJode newElement) {
        System.out.println("An element is added to " + tableName);
        AbstractJode table = getTable(tableName); 
        System.out.println(table.appendChild(newElement));
    }
    
    
}
