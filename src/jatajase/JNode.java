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
public class JNode extends AbstractJode implements Serializable {
    public static final long serialVersionUID = 1L;
    
    public JNode(JodeName name) {
        name(name);
    }
   
    
    
}
