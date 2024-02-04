package oop.ex6.main;

import oop.ex6.main.exception.GeneralException;
import oop.ex6.main.exception.MethodException;
import oop.ex6.main.exception.TypeException;

import java.util.ArrayList;

/**
 * the class represents a method
 */
public class Method {


    public static final String NUM_PARAMETERS_ERROR = "the call Method dont have the require number of parameters";
    public static final String VALUE_TYPE_ERROR = "the values type not correspond to the parameters";
    public static final String PARMETERS_WITH_NULL_VALUE = "parmeters with null value";
    private String name;
    private String returnType;
    private ArrayList<Variable> parameters;

    /**
     * constructor of methods
     */
    public Method(ArrayList<Variable> parameters, String name, String type){
        this.name=name;
        this.returnType=type;
        this.parameters =parameters;
    }

    /**
     * the method returns the name of the method
     * @return name of the method
     */
    public String getName() {
        return name;
    }

    /**
     * the method checks if the parameters types correspond to the parameters on the method call
     * @param params parameters of the method call
     * @return true if the types correspond
     * @throws GeneralException general exception that can appear in the code
     */
    public boolean checkCall(ArrayList<Variable> params) throws GeneralException {
        if (params.size()!=parameters.size()){
            throw new MethodException(NUM_PARAMETERS_ERROR);
        }
        for (int i = 0; i <parameters.size() ; i++) {
            String type = parameters.get(i).getType();
            if(!type.equals(params.get(i).getType())){
                if((type.equals(JavaVerifier.BOOL)||(type.equals(JavaVerifier.DOUBLE)))
                        && params.get(i).getType().equals(JavaVerifier.INT))
                {
                    if (params.get(i).getValue()==null){
                        throw new MethodException(PARMETERS_WITH_NULL_VALUE);
                }
                    return true;
                }
                throw new TypeException(VALUE_TYPE_ERROR);
            }

        }
        return true;
    }
}
