/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatajase;

import java.io.Serializable;
import java.util.LinkedList;
/**
 *
 * @author gwion
 */
public class JodeList extends LinkedList<AbstractJode> implements Serializable {
    private boolean strict;
    private boolean assignor;
    
    private int hid = 0;
    private int giveId() {
        return hid++;
    }
    
    public void setAssignor() {
        this.assignor = true;
    }
    
    public JodeList(boolean strict) {
        this.strict = strict;
    }
    
    public JodeList allMatches(TestJode item) {
        JodeList matches = new JodeList(true);
        this.stream().filter((j) -> (item.matches(j))).forEach((j) -> {
            matches.add(j);
        });
        return matches;
    }
    
    public void merge(JodeList e) {
        for (AbstractJode j: this) {
            if (assignor) {
                j.id(giveId());
            }
            this.add(j);
        }
    }
    
    public JodeList allMatches(TestJode item, boolean deep) {
        JodeList matches = new JodeList(true);
        if (deep) {
            allMatchesDeep(item, matches);
        } else {
            this.stream().filter((j) -> (item.matches(j))).forEach((j) -> {
                matches.add(j);
            });
        }
        return matches;
    }
    
    public void allMatchesDeep(TestJode item, JodeList matches) {
        for (AbstractJode j: this) {
            if (item.matches(j)) matches.add(j);
            if (j.hasChildren()) j.childNodes().allMatchesDeep(item, matches);
        }
    }
    
    public boolean contains(JNode item) {
        for (AbstractJode node: this) {
            if (node.equals(item)) return true;
        }
        return false;
    }
    
    public boolean add(AbstractJode item) {
        if (!this.contains(item)) {
            if (assignor) {
                item.id(giveId());
            }
            this.addLast(item);
            return true;
        } else {
            return false;
        }
    }
    
    public AbstractJode remove(TestJode j) {
        for (AbstractJode i: this) {
            if (j.matches(i)) {
                this.remove(i);
                return i;
            }
        }
        return null;
    }
    
    public JodeList removeAll(TestJode i) {
        JodeList matches = this.allMatches(i);
        for (AbstractJode match: matches) {
            if (strict) {
                remove(match);
            } else {
                while (contains(match)) {
                    remove(match);
                }
            }
        }
        return matches;
    }
    
}
