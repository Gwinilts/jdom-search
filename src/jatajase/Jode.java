/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatajase;

/**
 *
 * @author gwion
 */
public interface Jode {
    
    public <T> T attr(String n, Class<T> c);
    public <T> boolean attr(String n, T t);
    public int id();
    public void id(int i);
    public JodeName name();
    public void name(JodeName name);
    
    public boolean equals(Jode other);
    
}
