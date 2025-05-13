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

import javax.swing.*;

public class SudokuWindow extends JFrame{
    
    int win_hei = 600;
    int win_wid = 1200;
    
    SudokuPuzzle puzzle;
    SudokuDisplay display;
    public SudokuWindow(String fileName){
        this.setTitle("My Puzzle");
        this.setSize(win_wid, win_hei);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        puzzle = new SudokuPuzzle(fileName);
        display = new SudokuDisplay(puzzle);
        puzzle.setDisplay(display);
        
        this.add(display);
        this.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        SudokuWindow mywin = new SudokuWindow("9x9_hard.txt");

    }
    
}
