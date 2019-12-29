/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosophers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author muma10837
 */
public class ChopSticks {
    private Table table;
    private int xSize = 5;
    private int ySize = 60;
    private int restX;
    private int restY;
    private int deltaX;
    private int deltaY;
    private double degree;
    private Semaphore mutex = new Semaphore(1);
    
    public ChopSticks(Table inTable, int size, int inX, int inY, double inDegree) {
        xSize = (int)(size/160);
        ySize = (int)(size/16);
        table = inTable;
        restX = inX;
        restY = inY;
        deltaX = inX;
        deltaY = inY;
        degree = inDegree;
    }
    
    public void draw(Graphics g) {
        //Graphics2D g2d = (Graphics2D)g;
        //AffineTransform old = g2d.getTransform();
        g.setColor(Color.red);
        //g2d.rotate(Math.toRadians(degree), restX-X_SIZE/2 , restY-Y_SIZE/2 );
        g.fillOval(deltaX-xSize/2, deltaY-ySize/2, xSize, ySize);
        //g2d.setTransform(old);
    }
    
    public void clear() {
        Graphics g = table.getGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(restX-xSize/2, restY-ySize/2, xSize, ySize);
    }
    
    synchronized public boolean getStick(int inX, int inY) {
        boolean hold = mutex.tryAcquire();
        if(hold) {
            clear();
            deltaX = (int)((inX + restX)/2);
            deltaY = (int)((inY + restY)/2);
            table.repaint();
        }
        return hold;
    }
    
    public void releaseStick() {
        clear();
        deltaX = restX;
        deltaY = restY;
        table.repaint();
        mutex.release();
    }
    
    public void saveStick() {
        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(ChopSticks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
