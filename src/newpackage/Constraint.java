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
public class Constraint {
    
    private String name;
    private String type;
    private LinkedList<Variable> vars;
    
    public Constraint(String nam, String typ, Variable var1, Variable var2){
        name = nam;
        type = typ;
        vars = new LinkedList<>();
        vars.add(var1);
        vars.add(var2);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public LinkedList<Variable> getVars() {
        return vars;
    }
    
    public boolean reviseAllDiff(){
        boolean changedflag = false;
        int counter;
        for (int i = 0;i < (this.vars.get(0).sizeDomain()); i++){
            if (!this.vars.get(0).hasDiffValueSupport(i,this.vars.get(1))){
                this.vars.get(0).removeFromDomain(i);
                changedflag = true;
            }
        }
        
        for (int i = 0;i < (this.vars.get(1).sizeDomain()); i++){
            if (!this.vars.get(1).hasDiffValueSupport(i,this.vars.get(0))){
                this.vars.get(1).removeFromDomain(i);
                changedflag = true;
            }
        }     
        return changedflag;
    }
    
}
