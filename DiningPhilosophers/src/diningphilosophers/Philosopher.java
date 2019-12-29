/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosophers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author muma10837
 */
public class Philosopher extends Thread{
    private int xSize = 75;
    private int ySize = 75;
    private static final int CHOW_TIME = 4;
    private static final int THINKING_TIME = 3;
    private static final int FORK_TIME = 2;
    private static final double TIME_SHIFT = 0.2;
    
    private Random random;
    private Color color;
    private Table table;
    private ChopSticks left;
    private ChopSticks right;
    private DiningPhilosophers main;
    
    private int locX;
    private int locY;
    private int id;
    private boolean running;
    
    
    public Philosopher (Table inTable, int size, int inX, int inY, ChopSticks inLeft, ChopSticks inRight, int inID, DiningPhilosophers driver) {
        xSize = (int)(size / 13.3);
        ySize = (int)(size / 13.3);
        table = inTable;
        locX = inX;
        locY = inY;
        left = inLeft;
        right = inRight;
        id = inID;
        running  = true;
        random = new Random(); 
        color = Color.BLACK;
        main = driver;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(locX-xSize/2, locY-ySize/2, xSize, ySize);
    }
    
    @Override
    public void run() {
        while(running) {
            try {
                color = Color.blue;
                table.repaint();
                main.textOutput("Philosopher " + (id + 1) + "is thinking\n");
                sleep((random.nextInt(THINKING_TIME) + 1) * 1000);
                color = Color.RED;
                table.repaint();
                main.textOutput("Philosopher " + (id + 1) + "is waiting\n");
                sleep((random.nextInt(FORK_TIME) + 1) * 1000);
                if (left.getStick(locX, locY)) {
                    if(right.getStick(locX, locY)) {
                        color = Color.GREEN;
                        table.repaint();
                        main.textOutput("Philosopher " + (id + 1) + "is eating\n");
                        sleep((random.nextInt(CHOW_TIME) + 1) * 1000);
                        color = Color.blue;
                        table.repaint();
                        right.releaseStick();
                    }
                    left.releaseStick();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        color = Color.blue;
        table.repaint();
    }
    
    public void setRunning(boolean inState) {
        running = inState;
    }    
}
