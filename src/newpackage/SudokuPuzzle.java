/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.math.*;

/**
 *
 * @author Petro
 */
public class SudokuPuzzle {
    
    private int size;
    private int groupSize;
    private HashMap<String,Constraint> constraints;
    private Variable [][] grid;
    private SudokuDisplay display;
    LinkedList<Variable> initialValues = new LinkedList<Variable>();
    ArrayList<Variable> assignment;
    int domainElement[];

    
    public SudokuPuzzle(String fName){
        loadPuzzleFromFile(fName);
        constraints = new HashMap<String, Constraint>();
        String tempname;
        
        for (int row=0; row<size; row++){
            for(int col=0; col<size-1; col++){
                for (int compared=col+1; compared<size ; compared++){
                    tempname = grid[row][col].getName()+","+grid[row][compared].getName();
                    constraints.put(tempname, new Constraint(tempname,"alldiff",grid[row][col], grid[row][compared]));
                }
            }  
        }
        
        for (int row=0; row<size; row++){
            for(int col=0; col<size-1; col++){
                for (int compared=col+1; compared<size ; compared++){
                    tempname = grid[col][row].getName()+","+grid[compared][row].getName();
                    constraints.put(tempname, new Constraint(tempname,"alldiff",grid[col][row], grid[compared][row]));
                }
            }  
        }
        
        for (int grouprow=0; grouprow<size; grouprow+=groupSize){
            for (int groupcol=0; groupcol<size; groupcol+=groupSize){            
                for(int row=0+grouprow; row<groupSize+grouprow; row++){
                    for (int col=0+groupcol; col<groupSize+groupcol ; col++){
                        for(int rowcompared=0+grouprow; rowcompared<groupSize+grouprow; rowcompared++){
                            for (int colcompared=0+groupcol; colcompared<groupSize+groupcol ; colcompared++){
                                if(row==groupSize+grouprow-1){
                                    break;
                                }
                                else if((rowcompared>row) && (col!=colcompared)){
                                    tempname = grid[row][col].getName()+","+grid[rowcompared][colcompared].getName();
                                    constraints.put(tempname, new Constraint(tempname,"alldiff",grid[row][col], grid[rowcompared][colcompared]));
                                }
                            }
                        }
                    }
                }  
            }
        }
        
        
    }
    
    
    public void backtrack(){
        assignment = new ArrayList<Variable>();
        System.out.println("\n"+toString());
        
        for(int row=0; row<size; row++){
            for(int col=0; col<size; col++){
                if(grid[row][col].domain.size() == 1){
                    assignment.add(grid[row][col]);
                }
            }
        }       
        int min = assignment.size();
        
        for(int row=0; row<size; row++){
            for(int col=0; col<size; col++){
                if(grid[row][col].domain.size() > 1){
                    assignment.add(grid[row][col]);
                }
            }
        }       
        long initialTime = System.currentTimeMillis();   
        domainElement = new int [assignment.size()];  
        int iter = min;
        while(iter >= min && iter < assignment.size()){
            if(findSuccessfulAssignment(assignment.get(iter), iter)){
                display.paintImmediately(0, 0, 600, 600);
                
                iter++;
            }
            else{
                iter--;
                domainElement[iter]++;
            }
        }
        if(iter < min){
            System.out.println("No solution found");
        }
        else{
            System.out.println("Done");

        }
        long finalTime = System.currentTimeMillis();   
        long timeTaken = finalTime - initialTime;
        System.out.println(timeTaken);
        
    }
    
    public boolean findSuccessfulAssignment(Variable var, int index){
        if(domainElement[index] >= var.sizeDomain()){
            domainElement[index] = 0;
            var.setAssignment(-1);
            return false;
        }
        var.setAssignment(var.getDomainElement(domainElement[index]));
        while(!isConsistentAssignment(var, index)){
            domainElement[index]++;
            if(domainElement[index] >= var.sizeDomain()){
                domainElement[index] = 0;
                var.setAssignment(-1);
                return false;
            }
            var.setAssignment(var.getDomainElement(domainElement[index]));
        }
        return true;
    }
    
    public boolean isConsistentAssignment(Variable var, int index){
        String key;
        for (int row=0; row<getSize() ; row++){
            for (int col=0; col<getSize() ; col++){
                
                    
                        key = grid[row][col].getName()+","+var.getName();
                        if (constraints.containsKey(key)){
                            if(grid[row][col].getAssignment() > -1){
                                if(grid[row][col].getAssignment() == var.getAssignment()){
                                    return false;
                                }
                            }
                        }
                        key = var.getName()+","+grid[row][col].getName();
                        if (constraints.containsKey(key)){
                            if(grid[row][col].getAssignment() > -1){
                                if(grid[row][col].getAssignment() == var.getAssignment()){
                                    return false;
                                }
                            }
                        }
                    
                
            }
        }
        return true;
    }
    
    public void loadPuzzleFromFile(String fName){
        File inFile = new File(fName);
        try{
            Scanner fileScan = new Scanner(inFile);
            size = fileScan.nextInt();
            groupSize = fileScan.nextInt();
            grid = new Variable[getSize()][getSize()];
            
            for (int row = 0; row < getSize(); row++){
                for (int col = 0; col < getSize(); col++){
                    //grid[row][col] = fileScan.nextInt();  <--- removed
                    int newVal = fileScan.nextInt();
                    Variable newVar;
                    String name = ""+row+","+col;
                    if (newVal == 0){
                        newVar = new Variable(-1,1,size,name);
                    }
                    else{
                        newVar = new Variable(newVal,newVal,newVal,name);
                        initialValues.add(newVar);
                    }
                    grid[row][col] = newVar;
                    System.out.print(" " +grid[row][col]);
                }
                System.out.print("\n");
            }
            fileScan.close();
            
            
            
        }
        catch(IOException ioe){
            String mess = "ioexc while working with file"+fName;
            JOptionPane.showMessageDialog(null, mess, "error message", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public BigInteger sizeOfSearchSpace(){
        //int remaining = getSize() * getSize()-1;
        String siz, rem;
        siz = String.valueOf(getSize());
        //rem = String.valueOf(remaining);
        boolean firstflag = true;
        BigInteger biggee = new BigInteger(String.valueOf(size));
        BigInteger biggee2 = new BigInteger(String.valueOf(size));
        BigInteger bigzero = new BigInteger("1");
        
        for(int row = 0; row < getSize();  row++){
            for (int col = 0; col< getSize(); col++){
                if (grid[row][col].sizeDomain()>1){
                    if (firstflag){
                        biggee = new BigInteger(String.valueOf(grid[row][col].sizeDomain()));
                        firstflag = false;
                    }else {
                        biggee2 = new BigInteger(String.valueOf(grid[row][col].sizeDomain()));
                        biggee = biggee.multiply(biggee2);
                    }
                }
            }
        }                 
        

        //System.out.print("\n\n\n"+toString());
        return biggee;
    }
    
    public static void main(String [] args){
        SudokuPuzzle myPuzzle = new SudokuPuzzle("9x9_hardy.txt");
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the groupSize
     */
    public int getGroupSize() {
        return groupSize;
    }

    /**
     * @return the grid
     */
    public Variable getGridPosition(int row, int col) {
        return grid[row][col];
    }

    /**
     * @return the display
     */
    public SudokuDisplay getDisplay() {
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(SudokuDisplay display) {
        this.display = display;
    }
    
    public boolean checkForSingleton(Variable var){
        
        if (var.domain.size() == 1){
            var.setAssignment(var.domain.get(0));
            return true;
        }
        return false;
    }
    
    
    
    public void oneRoundArcConsistent(){
        boolean changed;
        
        String key;
        for (int row=0; row<getSize() ; row++){
            for (int col=0; col<getSize() ; col++){
                if (true){
                    while (true){
                        changed = false;
                        for (int rowcompared=0; rowcompared<getSize(); rowcompared++){
                            for (int colcompared=0; colcompared<getSize(); colcompared++){
                                key = grid[row][col].getName()+","+grid[rowcompared][colcompared].getName();
                                if (constraints.containsKey(key)){
                                    constraints.get(key).reviseAllDiff();
                                }
                                key = grid[rowcompared][colcompared].getName()+","+grid[row][col].getName();
                                if (constraints.containsKey(key)){
                                    constraints.get(key).reviseAllDiff();
                                }
                            }
                        }
                        if (changed == false){
                            break;
                        }
                    }
                }
            }
        }
        for (int row = 0; row<size; row++){
            for (int col = 0; col<size; col++){
                if (checkForSingleton(grid[row][col])){
                    display.paintImmediately(0, 0, 1200, 600);
                }
            }
        }
        
    }

    public String toString(){
        String stuff = "";
        for(int row=0;row<getSize();row++){
            stuff += "row: " + row + "\n\n";
            for (int col=0;col<getSize();col++){
                stuff += grid[row][col].toString() + "\n";
            }
        }
        return stuff;
    }

}
