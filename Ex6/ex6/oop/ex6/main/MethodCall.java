package oop.ex6.main;

import java.util.ArrayList;

/**
 * the class represents a  method call
 */
public class MethodCall {

    private ArrayList<Variable> parameters;
    private String name;

    /**
     *Constructor of a method call
     */
    public MethodCall(ArrayList<Variable> parameters, String name) {
        this.parameters = parameters;
        this.name = name;
    }

    /**
     * the method gets the parameters of the method call
     * @return parameters
     */
    public ArrayList<Variable> getParameters() {
        return parameters;
    }

    /**
     * the function return the name of the method call
     * @return name
     */
    public String getName() {
        return name;
    }
}
