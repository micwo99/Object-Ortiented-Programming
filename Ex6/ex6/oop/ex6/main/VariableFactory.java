package oop.ex6.main;

import oop.ex6.main.Variable;

public class VariableFactory
{


    public static final String CONSTANT = "constant";
    public static final String VARIABLE = "variable";
    public static final String PARAMETER = "parameter";
    public static final String NOT_ASSIGN_VARIABLE = "not assign variable";
    public static final String NOT_ASSIGN_GLOBAL_VARIABLE = "not assign global variable";


    public static Variable getVariable(String var, String value, String name, String type, boolean fin)
    {
        Variable variable;
        switch (var){
            case CONSTANT:
                variable= new Variable(value);
                break;
            case PARAMETER:
                variable=new Variable(name,type,fin, true);
                break;
            case NOT_ASSIGN_GLOBAL_VARIABLE:
                variable =new Variable(name,type,null,false,true);
                break;
            case NOT_ASSIGN_VARIABLE:
                variable= new Variable(name,type);
                break;
            default:
                variable=new Variable(name,type,value,fin,false);
                break;
        }
        return variable;

    }
}
