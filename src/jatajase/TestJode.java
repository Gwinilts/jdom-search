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
 */
public class TestJode extends AbstractJode implements Serializable, Jode {
    
    boolean hasId = false;
    
    public TestJode() {
      
    }
    
    @Override
    public void id(int i) {
        hasId = true;
        this.attr("id", i);
    }
    
    @Override
    public int id() {
        return this.attr("id", Integer.class);
    }
    
    public boolean matches(AbstractJode other) {
        // Rather than us being equal
        // Lets test that other has the same properties as me,
        // The same name if mine is not null
        // And my id as an attr (if not null)
        boolean matches = true;
        String cv, vc;
        
        for (String attr: this.attr()) {
            if (!attr.equals("id") && !attr.equals("name")) {
                if (other.attr(attr) != null) { 
                    try {
                        cv = (String)other.attr(attr, String.class); // Target
                        vc = (String)this.attr(attr, String.class); // Match String
                        if (vc.contains(":like:")) {
                            vc = vc.replaceAll(":like:", "");
                            vc = vc.replaceAll(":%:", "[a-zA-Z0-9 ]+");
                            
                            System.out.println("Matching special regex /" + vc + "/");
                            
                            matches &= cv.matches(vc);
                        } else {
                            matches &= cv.matches(vc);
                        }
                    } catch (Exception e) {
                        other.attr(attr).equals(this.attr(attr));
                    }
                } else {
                    // I have an attr that other doesn't have
                    System.err.println("Other doesn't have " + attr);
                    return false;
                }
            }
        }
        
        if (this.name() != null) {
            matches &= this.name() == other.name();
        }
        
        if (hasId) {
            System.out.println(this.id() + " " + other.id());
            System.out.println(this.id() == other.id());
            matches &= this.id() == other.id();
        }
        
        return matches;
    }
    
}
