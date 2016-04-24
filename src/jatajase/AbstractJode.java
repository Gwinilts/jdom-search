/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatajase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author gwion
 * @version see serialVersionUID
 */
public abstract class AbstractJode implements Jode, Serializable {
    
    /*
    If we start upgrading this class
    We need to change the serialVersionUID to stop the wierd errors arising from missing properties in
    newer or older versions of this class
    */
    
    public static final long serialVersionUID = 1L;
    
    private AttrList attributes;
    private JodeList children;
    private AbstractJode parent;
    int id;
    private JodeName name;
    
    /**
     * The abstract jode
     */
    
    public AbstractJode() {
        this.attributes = new AttrList();
        this.children = new JodeList(true);
        this.parent = null;
    }
    
    public AbstractJode(AbstractJode parent) {
        this.attributes = new AttrList();
        this.children = new JodeList(true);
        this.parent = parent;
    }
    
    /**
     * parent() gets the parent of this node
     * @return the node's parent
     */
    
    public AbstractJode parent() {
        return this.parent;
    }
    
    /**
     * hasChildren determines whether or not this node has children
     * @return True if the node has at least one child, false otherwise.
     */
    
    public boolean hasChildren() {
        return this.children.size() > 0;
    }
    
    /**
     * returns a reference to the collection of children of this node
     * @return a JodeList containing the children of this node.
     */
    
    public JodeList childNodes() {
        return children;
    }
    
    /**
     * sets the parent of this node
     * @param p the parent node
     */
    
    public void parent(AbstractJode p) {
        this.parent = p;
    }
    
    /**
     * gets an attribute of this node
     * @param <T> the type of this attribute
     * @param n the name of this attribute
     * @param c the value of this attribute
     * @return the attribute casted to whatever type was specified
     */

    @Override
    public <T> T attr(String n, Class<T> c) {
        return this.attributes.getAttribute(n, c);
    }
    
    /**
     * gets an attribute of this node
     * @param n the name of the attribute
     * @return the attribute
     */
    
    public Attr attr(String n) {
        return this.attributes.get(n);
    }
    
    /**
     * sets an attribute of this node
     * @param <T> the type of the attribute
     * @param n the name of this attribute
     * @param t the value of the attribute
     * @return the attribute
     */

    @Override
    public <T> boolean attr(String n, T t) {
        return this.attributes.setAttribute(n, t);
    }
    
    /**
     * gets the attribute names of this node
     * @return a string[] containing all the attribute names of this node
     */
    
    public String[] attr() {
        return this.attributes.attributes();
    }
    
    /**
     * gets the id of this node
     * @return the id of this node
     */

    @Override
    public int id() {
        return this.id;
    }
    
    /**
     * sets the if of this node
     * @param i the id to give this node
     */

    @Override
    public void id(int i) {
        this.id = i;
    }
    
    /**
     * gets the nodeName of this node
     * @return the nodename of this node
     */

    @Override
    public JodeName name() {
        return this.name;
    }
    
    /**
     * sets the nodeName of this node
     * @param name the nodeName to set
     */

    @Override
    public void name(JodeName name) {
        this.name = name;
    }
    
    /**
     * An AbstractJode equals another AbstractJode when the attribute lists have the same contents, and the id and node name are the same
     * @param other The node we're comparing to.
     * @return True if the nodes are the same, false otherwise.
     */

    @Override
    public boolean equals(Jode other) {
        boolean equal = true;
        equal &= name == other.name();
        equal &= id == other.id();
        
        Attr x;
        
        for (String a: attr()) {
            equal &= (x = (this.attr(a))).equals(other.attr(a, x.getType()));
        }
        return equal;
        
    }
    
    /**
     * An abstract node strictly equals another node then the node name and ids are the same.
     * @param other The node we're comparing to.
     * @param strict Calling this method with strict=false is the equivelant of calling this method with no arguments.
     * @return True if the nodes were the same, false otherwise.
     */
    
    public boolean equals(Jode other, boolean strict) {
        if (strict) {
            return this.equals(other);
        } else {
            return (this.id == other.id() && name == other.name());
        }
    }
    
    /**
     * AppendChild tries to add a node to the this nodes collection of Children
     * @param e The node to add
     * @return True if the node was added, false otherwise
     */
    
    public boolean appendChild(AbstractJode e) {
        e.parent(this);
        return this.children.add(e);
    }
    
    /**
     * Stringify produces a string representation of this object from which it can be restored using parse
     * @return A string representing the contents of the object
     */
    
    public String stringify() {
        String output = "{NN:" + name.toString();
        output += ",ID:" + id;
        output += ",AT:[";
        for (String a: attr()) {
            output += "{" + a + ":" + attr(a).toString() + "},";
        }
        if (output.charAt(output.length() - 1) == ',') {
            output = output.substring(0, output.length() -1);
        }
        output += "],CH:[";
        for (AbstractJode child: children) {
            output += child.stringify() + ",";
        }
        if (output.charAt(output.length() - 1) == ',') {
            output = output.substring(0, output.length() -1);
        }
        output += "]}";
        return output;
    }
    
    /**
     * toString produces a human readable string representation of this node
     * @return a string representing the contents of this node
     */
    
    public String toString() {
        String output = "\n{";
        output += "\n\tNodeName: " + name.toString();
        output += "\n\tId: " + id;
        for (String a: attr()) {
            output += "\n\t\t[[" + a + ": " + attr(a).toString() + "]],";
        }
        output += "\n\tChildren: {{";
        for (AbstractJode child: children) {
            output += child.toString();
        }
        output += "\n\t}}";
        return output.substring(0, output.length() - 1) + "\n}";
    }
    
    /**
     * parse sets the properties of this object using a string produced by stringify
     * @param data the string to parse
     */
    
    public void parse(String data) {
        // Strip opening and closing set
        System.out.println(data);
        data = data.substring(data.indexOf("{") + 1, data.lastIndexOf("}"));
        
        // Strip children
        String childs = data.substring(data.indexOf("[", data.indexOf("]")), data.lastIndexOf("]") + 1);
        data = data.replace(",CH:" + childs, "");
        childs = childs.substring(childs.indexOf("[") + 1, childs.lastIndexOf("]"));
        
        // Strip the attributes
        String attr = data.substring(data.indexOf("["), data.lastIndexOf("]") + 1);
        data = data.replace(",AT:" + attr, "");
        attr = attr.substring(attr.indexOf("[") + 1, attr.lastIndexOf("]"));
        
        // Do my name and id
        String[] base = data.split(",");
        String[] cpr;
        for (String item: base) {
            cpr = item.split(":");
            if (cpr[0].toUpperCase().equals("NN")) {
                name(JodeName.valueOf(cpr[1]));
            }
            if (cpr[0].toUpperCase().equals("ID")) {
                id(Integer.parseInt(cpr[1]));
            }
        }
        
        // Parse my attributes
        
        for (String term: parseObjectList(attr)) {
            if (term.charAt(0) == ',') term = term.substring(1);
            term = term.substring(term.indexOf("{") + 1, term.lastIndexOf("}"));
            cpr = term.split(":");
            attr(cpr[0], cpr[1]);
        }
        
        // Make a template child
        
        JNode template;
        
        // Parse my children
        
        for (String term: parseObjectList(childs)) {
            template = new JNode(JodeName.JRow);
            template.parse(term);
            appendChild(template);
        }
        
    }
    
    /**
     * parseObjectList parses a series of objects encapsulated by {'s and }'s
     * @param data The object as a string
     * @return An array of strings representing the objects parsed
     */
    
    private String[] parseObjectList(String data) {
        // An object list is a series of objects encapsulated by {'s and }'s
        
        // A term is a seperated object
        ArrayList<String> terms = new ArrayList();
        
        // Objects can contain objects
        // depth is the object depth
        int depth = 0;
        
        // The current stringified object
        String term = "";
        
        
        for (char t: data.toCharArray()) {
            // If we're at the opening or closing segment
            if (t == '{' || t == '}') {
                // If we're closing on a level 1 object
                if (t == '}' && depth == 1) {
                    // Add it and init the term.
                    // Add an extra } because the read happens after the check
                    terms.add(term + "}");
                    term = "";
                }
                // Control the depth
                if (t == '}') depth--;
                if (t == '{') depth++;
            }
            
            // If there's nothing in the term and the char is '}' then ignore it, see above for why
            if (term.length() < 1 && t == '}') {
                // Do nothing
            } else {
                // Other wise it's part of the current term
                term += "" + t;
            }
            
        }
        
        return terms.toArray(new String[0]);
    }
    
    /**
     * toString produces a human readable version of this node
     * @param tab The amount of tab characters to place after each new line
     * @return The string representation of this node.
     * @see toString()
     */
    
    public String toString(int tab) {
        String tb = "";
        for (int i = 0; i < tab; i++) {
            tb += "\t";
        }
        String output = "\n" + tb + "{";
        output += "\n" + tb + "\tNodeName: " + name.toString();
        output += "\n" + tb + "\tId: " + id;
        output += "\n" + tb + "\tAttributes: {";
        for (String a: attr()) {
            output += "\n" + tb + "\t\t[[" + a + ": " + attr(a).toString() + "]],";
        }
        output += "\n" + tb + "\t}";
        output += "\n" + tb + "\tChildren: {";
        for (AbstractJode child: children) {
            output += child.toString(tab + 2);
        }
        output += "\n" + tb + "\t}";
        return output + "\n" + tb + "}";
    }
    
}
