/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosophers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author muma10837
 */
public class Table extends JPanel{
    private ChopSticks[] stick;
    private Philosopher[] philosopher;
    private int height;
    private int width; 
    private DiningPhilosophers main;

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int x = 0; x < 5; x++) {
            stick[x].draw(g);
            philosopher[x].draw(g);
        }
        for (int x = 0; x < 5; x++) {
           double angle = Math.PI/2 + 2*Math.PI/5*x;
           g.setColor(Color.yellow);
           g.fillOval((int) (width/2 + width/7.0 * Math.cos(angle)), 
                   (int) (height/2 - height/7.0 * Math.sin(angle)), (int)(height / 20), (int)(height / 20)); 
        }
        
        g.setColor(Color.BLACK);
        g.drawOval((int)(height / 3), (int)(height / 3), (int)(height / 2.5),(int)(height / 2.5));
    }
    
    public Table(int inSize, DiningPhilosophers driver) {
        stick = new ChopSticks[5];
        philosopher = new Philosopher[5];
        height = inSize;
        width = inSize;
        main = driver;
        setPreferredSize(new Dimension(height, width));
        
        this.add(new JLabel("blue - thinking     red - waiting      green - eating"));
        
        for (int x = 0; x < 5; x++) {
            double angle = Math.PI/2 + 2*Math.PI/5*(x-0.5);
            stick[x] = new ChopSticks(this, height, (int) (width/2.0 + width/7.0 * Math.cos(angle)),
                (int) (height/1.9 - height/7.0 * Math.sin(angle)), 
                -48.75 * Math.pow(x, 4) + 362.5 * Math.pow(x, 3) + -761.25 * Math.pow(x, 2) + 387.5 * x + 210);
        }
        for (int x = 0; x < 5; x++) { 
            double angle = Math.PI/2 + 2*Math.PI/5*x;
            philosopher[x] = new Philosopher(this, height, (int) (width/2.0 + width/3.0 * Math.cos(angle)),
                (int) (height/2.0 - height/3.0 * Math.sin(angle)), stick[x], stick[(x+1) % 5], x, main);
        }
    }
    
    public void start() {
        stick = new ChopSticks[5];
        philosopher = new Philosopher[5];
        
        for (int x = 0; x < 5; x++) {
            double angle = Math.PI/2 + 2*Math.PI/5*(x-0.5);
            stick[x] = new ChopSticks(this, height, (int) (width/2.0 + width/7.0 * Math.cos(angle)),
                (int) (height/1.9 - height/7.0 * Math.sin(angle)), 
                -48.75 * Math.pow(x, 4) + 362.5 * Math.pow(x, 3) + -761.25 * Math.pow(x, 2) + 387.5 * x + 210);
        }
        for (int x = 0; x < 5; x++) { 
            double angle = Math.PI/2 + 2*Math.PI/5*x;
            philosopher[x] = new Philosopher(this, height, (int) (width/2.0 + width/3.0 * Math.cos(angle)),
                (int) (height/2.0 - height/3.0 * Math.sin(angle)), stick[x], stick[(x+1) % 5], x, main);
            philosopher[x].start();
        }
    }
    
    public void stop() {
        for(int x = 0; x < philosopher.length; x++) {
            philosopher[x].setRunning(false);
        }
    }
}
