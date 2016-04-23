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
public abstract class AbstractJode implements Jode {
    
    private AttrList attributes;
    private JodeList children;
    private AbstractJode parent;
    int id;
    private JodeName name;
    
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
    
    public AbstractJode parent() {
        return this.parent;
    }
    
    public boolean hasChildren() {
        return this.children.size() > 0;
    }
    
    public JodeList childNodes() {
        return children;
    }
    
    public void parent(AbstractJode p) {
        this.parent = p;
    }

    @Override
    public <T> T attr(String n, Class<T> c) {
        return this.attributes.getAttribute(n, c);
    }
    
    public Attr attr(String n) {
        return this.attributes.get(n);
    }

    @Override
    public <T> boolean attr(String n, T t) {
        return this.attributes.setAttribute(n, t);
    }
    
    public String[] attr() {
        return this.attributes.attributes();
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public void id(int i) {
        this.id = i;
    }

    @Override
    public JodeName name() {
        return this.name;
    }

    @Override
    public void name(JodeName name) {
        this.name = name;
    }

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
    
    public boolean equals(Jode other, boolean strict) {
        if (strict) {
            return this.equals(other);
        } else {
            return (this.id == other.id() && name == other.name());
        }
    }
    
    public boolean appendChild(AbstractJode e) {
        e.parent(this);
        return this.children.add(e);
    }
    
    public String toString() {
        String output = "\n{";
        output += "\n\tNodeName: " + name.toString();
        output += "\n\tId: " + id;
        for (String a: attr()) {
            output += "\n\t\t[[" + a + ": " + attr(a).toString() + "]],";
        }
        return output.substring(0, output.length() - 1) + "\n}";
    }
    
}
