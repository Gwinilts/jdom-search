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
public class TestAccessThread extends Thread {
    private JataJase j;
    private String name;
    public TestAccessThread(JataJase j, String name) {
        this.j = j;
        this.name = name;
    }
    
    public synchronized void run() {
        while (true) {
            if (j.isWaiting(this)) {
                if (j.canUse(this)) {
                    System.out.println("I am thread " + name + " and it is my turn!");
                    try {
                        j.done(this);
                    } catch (WaitYourTurnException e) {
                        
                    }
                }
            } else {
                try {
                    j.requestAccess(this);
                } catch (WaitYourTurnException e) {
                    
                }
            }
        }
    }
}
