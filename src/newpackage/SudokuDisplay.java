/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

/**
 *
 * @author Petro
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class SudokuDisplay extends JPanel{
    
    SudokuPuzzle puzzle;
    int cellSize=50;
    int start_x = 50;
    int start_y = 50;
    int offset_x = 15;
    int offset_y = 35;
    boolean firstrun;
    Font numberFont = new Font("Arial", 1, 30);
    Font searchSpaceFont = new Font("Arial", 1, 15);
    Color digitColor = Color.blue;
    
    public SudokuDisplay(SudokuPuzzle puz){
        puzzle = puz;
        firstrun=true;
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                if(me.getX() > 1000){
                    System.err.print("mouse clicked: backtrack\n");
                    backtrackSearch();
                }else{
                    System.err.print("mouse clicked: one round of arc\n");
                    nextMove();
                    System.err.print("search space:"+puzzle.sizeOfSearchSpace()+"\n");
                }
            }
        });
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(numberFont);
        for(int row = 0; row< puzzle.getSize();row++){
            for (int col = 0;col < puzzle.getSize();col++){
                g.drawRect(start_x + col*cellSize, start_y + row*cellSize, cellSize, cellSize);
                if(puzzle.getGridPosition(row, col).getAssignment() > 0){
                    g.setColor(digitColor);
                    if (puzzle.initialValues.contains(puzzle.getGridPosition(row, col))){
                        g.setColor(Color.black);
                    }
                    g.drawString(""+puzzle.getGridPosition(row,col).getAssignment(), start_x + col*cellSize + offset_x, start_y + row*cellSize+offset_y);
                    g.setColor(Color.black);
                }
            }
        }
        
        g.setFont(searchSpaceFont);
        g.drawString("Search Space: "+puzzle.sizeOfSearchSpace(), start_x , start_y + 500);

            
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));
        
        int block = puzzle.getGroupSize();
        for(int row = 0; row < puzzle.getSize(); row += block){
            for (int col = 0; col< puzzle.getSize(); col+=block){
                g.drawRect(start_y + row*cellSize, start_x + col*cellSize, cellSize*block, cellSize*block);
            }
        }
    }
    
    public void nextMove(){
        
        this.puzzle.oneRoundArcConsistent();
    }
    
    public void backtrackSearch(){
        this.puzzle.backtrack();
    }
}
