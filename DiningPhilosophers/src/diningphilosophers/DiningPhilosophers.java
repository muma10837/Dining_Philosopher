/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosophers;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author muma10837
 */
public class DiningPhilosophers{
    final static int FRAME_SIZE = 400;
    
    private JTextArea textField;
    private JScrollPane scrollText;
    
    public void initialize() {
        JFrame frame = new JFrame("Dining Philosophers Problem");
        frame.setBounds(0, 0, FRAME_SIZE * 2, FRAME_SIZE);
        frame.getContentPane().setLayout(new GridLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Table table = new Table(FRAME_SIZE, this);
        
        JPanel outputPanel= new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 0, 0));
        JPanel outputTextPanel = new JPanel(new GridLayout(0, 1, 0, 0));
        textField = new JTextArea();
        textField.setEditable(false);
        textField.setSize(250,250);
        textField.setColumns(50);
        scrollText = new JScrollPane(textField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outputTextPanel.add(scrollText);
        outputPanel.add(outputTextPanel, BorderLayout.CENTER);
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton clear = new JButton("Clear");
        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(clear);
        start.setEnabled(true);
        stop.setEnabled(false);
        clear.setEnabled(false);
        outputPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        start.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
                table.start();
                start.setEnabled(false);
                stop.setEnabled(true);
                if(clear.isEnabled()){
                    clear.setEnabled(false);
                }
            }
        });
        
        stop.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                table.stop();
                start.setEnabled(true);
                stop.setEnabled(false);
                clear.setEnabled(true);
            }
        });
        
        clear.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
                start.setEnabled(true);
                stop.setEnabled(false);
                clear.setEnabled(false);
            }
       });
        
        frame.getContentPane().add(table);
        frame.getContentPane().add(outputPanel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void textOutput(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textField.append(text);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DiningPhilosophers main = new DiningPhilosophers();
        main.initialize();
    }
    
}


