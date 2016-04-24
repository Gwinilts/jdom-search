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
public class Attr<T> implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private T value;
    
    public Attr(T v) {
        this.value = v;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public Class<?> getType() {
        return value.getClass();
    }
    
    public boolean equals(Attr other) {
        if (this.getType() != other.getType()) return false;
        return this.getType().cast(other.value).equals(this.value);
    }
    
    public boolean equals(Attr other, boolean strict) {
        if (strict) return equals(other);
        return this.getType().cast(other.value).equals(this.value);
    }
    
    public String toString() {
        return this.value.toString();
    }
}
