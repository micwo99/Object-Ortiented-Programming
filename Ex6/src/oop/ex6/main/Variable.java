package oop.ex6.main;

import oop.ex6.main.exception.GeneralException;
import oop.ex6.main.exception.VariableAssignmentException;

/**
 * the class corresponds to a variable
 */
public class Variable{

    private String name;
    private String type;
    private String value;
    private boolean fin;
    private boolean reset;
    private boolean parameter=false;

    /**
     * constructor of the variable
     * @param name name of the variable
     * @param type type of the variable
     * @param value value of the variable
     * @param fin final or not
     * @param reset global value or not
     */
    public Variable(String name, String type, String value, boolean fin, boolean reset){
        this.name=name;
        this.value=value;
        this.type =type;
        this.fin=fin;
        this.reset=reset;
    }

    /**
     * constructor of the variable
     * @param value value of the variable
     */
    public Variable(String value){
        this.value=value;
        String[] types = {JavaVerifier.INT, JavaVerifier.STRING, JavaVerifier.CHAR, JavaVerifier.DOUBLE,JavaVerifier.BOOL};
        String[] typesRegex = {JavaVerifier.INT_REGEX, JavaVerifier.STRING_REGEX, JavaVerifier.CHAR_REGEX, JavaVerifier.DOUBLE_REGEX,JavaVerifier.BOOL_REGEX};
        for (int i = 0; i < 5; i++) {
            if(value.matches(typesRegex[i])){
                this.type=types[i];
                break;
            }
        }

    }

    /**
     * constructor of the variable
     * @param name name of the variable
     * @param type type of the variable
     */
    public Variable(String name, String type){
        this.name=name;
        this.value=null;
        this.type =type;
        this.fin=false;
    }

    /**
     * constructor of a variabel
     * @param name naem
     * @param type type
     * @param fin true or false
     * @param parameter true or false
     */
    public Variable(String name,String type,boolean fin,boolean parameter){

        this.name = name;
        this.type = type;
        this.fin = fin;
        this.parameter = parameter;
    }

    /**
     * the method returns the name
     * @return name of variable
     */
    public String getName() {
        return name;
    }
    /**
     * the method returns the type
     * @return type of the variable
     */
    public String getType() {
        return type;
    }

    /**
     * the method returns the value of the variable
     * @return value of the variable
     */
    public String getValue() {
        return value;
    }

    /**
     * the method sets the value of the variable
     * @param value value to set
     * @throws GeneralException general exception that can appear in the code
     */
    public void setValue(String value) throws GeneralException {
        if (fin){
            throw new VariableAssignmentException(JavaVerifier.FINAL_DEC_ERROR);
        }

        this.value = value;
    }

    /**
     * the method sets if the variable is final
     * @param fin true/false
     */
    public void setFin(boolean fin) {
        this.fin = fin;
    }

    /**
     * the method returns if the value has to rest
     * @return reset
     */
    public boolean isReset() {
        return reset;
    }

    /**
     * the methods return if the value os a parameter or not
     * @return true or false
     */
    public boolean isParameter() {
        return parameter;
    }

    /**
     * the method sets if the variable is a parameter or not
     * @param parameter true or false
     */
    public void setParameter(boolean parameter) {
        this.parameter = parameter;
    }
}




