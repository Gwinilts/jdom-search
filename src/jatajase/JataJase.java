/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatajase;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 *
 * @author gwion
 */
public class JataJase extends Thread {

    private volatile LinkedList<Thread> operations;
    private volatile boolean finished;
    private volatile Thread user;
    private volatile TreeMap<String, Jocument> db;
    
    public synchronized boolean canUse(Thread t) {
        return t == user;
    }
    
    public synchronized void done(Thread t) throws WaitYourTurnException {
        if (t == user) {
            finished = true;
        } else {
            throw new WaitYourTurnException("You're not using this thread!");
        }
    }
    
    public synchronized void requestAccess(Thread t) throws WaitYourTurnException {
        if (operations.contains(t)) {
            throw new WaitYourTurnException("You're already pending an op, you should be sleeping!");
        } else {
            operations.addLast(t);
        }
    }
    
    
    
    public synchronized boolean isWaiting(Thread t) {
        return operations.contains(t);
    }
    
    public JataJase() {
        operations = new LinkedList();
        finished = true;
        db = new TreeMap();
    }
    
    public void run() {
        Thread ac;
        while (true) {
            if (finished && !operations.isEmpty()) {
                ac = operations.removeFirst();
                user = ac;
                finished = false;
            }
        }
    }
    
}
