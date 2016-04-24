/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatajase;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author gwion
 */
public class AttrList extends HashMap<String, Attr> implements Serializable {
    public <T> boolean setAttribute(String name, T value) {
        return (this.put(name, new Attr(value)) != null);
        
    }
    public <T> T getAttribute(String name, Class<T> c) throws ClassCastException {
        return c.cast(this.get(name).getValue());
    }
    public Attr getAttribute(String name) throws ClassCastException {
        return this.get(name);
    }
    public String[] attributes() {
        return this.keySet().toArray(new String[0]);
    }
}
