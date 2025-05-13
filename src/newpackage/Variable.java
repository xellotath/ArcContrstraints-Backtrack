/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;
import java.util.*;
/**
 *
 * @author McNeese
 */
public class Variable {
   
    private int assignment;
    LinkedList<Integer> domain;
    private String name;

    public Variable(int initVal, int startRange, int endRange, String nam){
        name = nam;
        assignment = initVal;
        domain = new LinkedList<Integer>();
        for (int rang = startRange; rang<=endRange; rang++){
            domain.add(rang);    
        }
    }
    
    
    public String getName() {
        return name;
    }

    public int getAssignment() {
        return assignment;
    }

    public void setAssignment(int assignment) {
        this.assignment = assignment;
    }
    
    public int getDomainElement(int dex){
        return domain.get(dex);
    }
    
    public int removeFromDomain(int num){
        return domain.remove(num);
    }
    
    public int sizeDomain(){
        return domain.size();
    }
    
    public boolean hasDiffValueSupport(int d1, Variable var){
        for (int i = 0;i < (var.sizeDomain()); i++){ 
            if(this.getDomainElement(d1) != var.getDomainElement(i) ){
                return true;
            }
        }
        return false;
    }
 
    public String toString(){
        String stuff = getName()+": ("+getAssignment()+") domain: ";
        for(int rang = 0; rang< domain.size(); rang++){
            stuff+= domain.get(rang)+" ";
        }
        stuff+= ",";
        return stuff;
    }
    



 
 


    
}