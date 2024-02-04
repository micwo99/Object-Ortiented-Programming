package oop.ex6.main;

import oop.ex6.main.exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaVerifier {


    public static final String INT = "int";
    public static final String INT_REGEX ="(-?)(\\d+)";
    public static final String DOUBLE_REGEX= INT_REGEX+"((\\.\\d*)?)";
    public static final String STRING_REGEX = "\".*\"";
    public static final String CHAR_REGEX = "'.'";
    public static final String STRING = "String";
    public static final String DOUBLE = "double";
    public static final String BOOL = "boolean";
    public static final String BOOL_REGEX = "(true|false|"+INT_REGEX+"|"+DOUBLE_REGEX+")";
    public static final String CHAR = "char";
    private static final int GLOBAL_VARIABLES = 0;
    private static final String PREFIX_COMMENT = "//";
    private static final String EMPTY_PARAMETERS = "()";
    private static final String COMMENT_ERROR = "There is an error of comment";
    private static final String ASSIGNMENT_ERROR = "The value not correspond to the type";
    private static final String METHODS_NAME_ERROR = "The name of the method already exist";
    private static final String VAR_NAME_ERROR = "The name of the local variable already exist";
    private static final String NAME_NOT_EXIST_ERROR = "You assign a value to a variable that doesn't exist";
    private static final String CONDITION_ERROR ="\"The condition isn't valid\"";
    public static final String FINAL_DEC_ERROR = "The assigment isn't valid";
    public static final String RETURN_ERROR="return error";
    public static final String END_METHOD_ERROR = "The method didn't end with a return statement";
    public static final String METHOD_EXIST_ERROR = "call a function that doesn't exist";
    public static final String BRACKET_ERROR = "There is a bracket that is missing";
    public static final String SyntaxError = "Bad Syntax Error";
    public static final String ASSIGN_NULL_VALUE_ERROR = "assignment of a null value";
    private static final String FINAL = "final";
    private static final String COMMA = ",";
    private static final String EQUAL = "=";
    private static final String END_LINE = ";";
    private static final String RETURN_STATEMENT="return(\\s*);";
    private static final String OPEN_PARENTHESES = "(\\(\\s*)";
    private static final String CLOSED_PARENTHESES = "(\\)\\s*)";
    private static final String OPEN_BRACKET = "\\s*\\{\\s*";
    private static final String CLOSE_BRACKET = "\\s*}\\s*";
    private static final String OR_AND_REG="\\|{2}|&&";
    private static final String NAME_REG = "\\s*(?:_\\w|[a-zA-Z])+(?:\\w*\\s*)\\s*";
    private static final String VAR_REG = "(final\\s+)?(int|double|String|char|boolean)\\s+";
    private static final String CALL_PARENTHESES = "\\s*("+BOOL_REGEX+"|"+NAME_REG+"|"
            +DOUBLE_REGEX+"|"+CHAR_REGEX+"|"+STRING_REGEX+")\\s*";
    private static final String ASSIGNATION_REG = "(\\s*=\\s*"+CALL_PARENTHESES+")?\\s*";
    private static final String ONE_ASSIGNATION_REG = VAR_REG + NAME_REG + ASSIGNATION_REG + ";\\s*";
    private static final String CONDITION_REG =  "("+BOOL_REGEX+"|"+NAME_REG+"|"+DOUBLE_REGEX+")";
    private static final String IF_WHILE_REG ="(if|while)\\s*\\((\\s*"+CONDITION_REG+"\\s*("+OR_AND_REG+")\\s*)*(\\s*"+
            CONDITION_REG+"\\s*)\\)(\\s*\\{)";
    private static final String DECLARATION_REG =VAR_REG+NAME_REG+";\\s*";
    private static final String JUST_DECLARATION = "(" + NAME_REG + ASSIGNATION_REG + ",\\s*)*" +
            "(" + NAME_REG + ASSIGNATION_REG + ")(\\s*;\\s*)";
    private static final String LINE_VAR_REG = VAR_REG + JUST_DECLARATION;
    private static final String NAME_METHOD_REG = "(([a-zA-Z])+(\\w*\\s*))";
    private static final String START_METHOD_REG = "(void\\s+)" + NAME_METHOD_REG +
            OPEN_PARENTHESES + "(" + VAR_REG + NAME_REG + ",\\s*)*" + "(" + VAR_REG + NAME_REG + ")?" +
            CLOSED_PARENTHESES + OPEN_BRACKET;

    private static final String CALL_METHOD =NAME_METHOD_REG+OPEN_PARENTHESES+"("+CALL_PARENTHESES+"\\s*,\\s*)*"+
            "("+CALL_PARENTHESES+")?"+CLOSED_PARENTHESES+";\\s*";
    public static final String VALUE_DONT_EXIST = "The value of the variable doesn't exist";
    public static final String CONDITION_TYPE = "conditionType";

    private  int method;
    private int numLine;
    private int currentLine;
    private int scopeNum;
    private ArrayList<String> codeLines;
    private HashMap<Integer, ArrayList<Variable>> scopeVar;
    private ArrayList<Method>  methodsList;
    private ArrayList<String> methodsName;
    private ArrayList<MethodCall> callUnknownMethods;
    private ArrayList<Variable> callUnknownVariable;

    /**
     * Constructor of the java verifier
     * @param codeLines lines of the sJava code
     */
    JavaVerifier(ArrayList<String> codeLines) {
        this.codeLines = codeLines;
        this.currentLine = 0;
        this.numLine = codeLines.size();
        this.scopeVar = new HashMap<>();
        this.methodsList = new ArrayList<>();
        this.methodsName = new ArrayList<>();
        this.callUnknownMethods = new ArrayList<>();
        this.callUnknownVariable= new ArrayList<>();
        this.scopeNum = GLOBAL_VARIABLES;
        this.method = 0;
    }
    /**
     *function that verify if a sJava code is valid
     * @throws GeneralException error that can appear in the code
     */
    public void codesVerifier() throws GeneralException {
        while (currentLine < numLine) {
            lineVerifier(codeLines.get(currentLine));
            currentLine++;
        }
        checkCallBeforeDeclaration();
        checkGlobalVariable();
        if(scopeNum !=GLOBAL_VARIABLES){
            throw new BracketException(BRACKET_ERROR);
        }
    }
    /**
     * checks if  all the global variables that has been called exist
     * @throws VariableAssignmentException exception that occurs if there is called of a variable that don't exist
     */
    private void checkGlobalVariable() throws GeneralException {
        if(callUnknownVariable.size()!=0){
            for(Variable  var: callUnknownVariable){
                boolean flag=false;
                if(scopeVar.containsKey(GLOBAL_VARIABLES)){
                    for(Variable global:scopeVar.get(GLOBAL_VARIABLES)){
                        if(var.getType().equals(CONDITION_TYPE)&&
                                var.getName().equals(global.getName())
                                && (global.getType().equals(INT)||
                                global.getType().equals(DOUBLE)||
                                global.getType().equals(BOOL))){
                            flag=true;
                            var.setValue(global.getValue());
                            break;
                        }
                        if(var.getName().equals(global.getName())&& var.getType().equals(global.getType()))
                        {
                            flag=true;
                            var.setValue(global.getValue());
                            break;
                        }
                    }
                    if(flag && var.getValue()==null){
                        throw new VariableAssignmentException(ASSIGN_NULL_VALUE_ERROR);

                    }
                }
                if(!flag){throw new VariableAssignmentException(ASSIGNMENT_ERROR);}
            }
        }

    }
    /**
     * checks if all the method calls are valid,which means that we check  the name of the method and
     * if all the types parameters correspond
     * @throws GeneralException general exception that can appear in the code
     */
    private void checkCallBeforeDeclaration() throws GeneralException {
        for(MethodCall call: this.callUnknownMethods){
            if(!this.methodsName.contains(call.getName())){
                throw new MethodException(METHOD_EXIST_ERROR);
            }
            for(Method method1:methodsList){
                if(call.getName().equals(method1.getName())){
                    method1.checkCall(call.getParameters());
                }
            }
        }
    }
    /**
     * The method checks if a line of the code is valid
     * @param line line of the code
     * @throws GeneralException general exception that can appear in the code
     */
    private void lineVerifier(String line) throws GeneralException {
        String commentLine=line;
        line =line.trim();
        if (line.matches(RETURN_STATEMENT) && scopeNum !=GLOBAL_VARIABLES) {
            checkReturnLine();
        }
        else if (commentLine.contains(PREFIX_COMMENT)&& !commentLine.startsWith(" ")) {
            checksCommentLine(line);
        }
        else if (line.matches(LINE_VAR_REG)) {
            checksVariableLine(line);
        } else if (line.matches(JUST_DECLARATION)) {
            checkAssigmentValueLine(line);
        } else if (line.matches(START_METHOD_REG) && method==0) {
            checksMethodsLine(line);
        }
        else if (line.matches(CLOSE_BRACKET) && scopeNum !=GLOBAL_VARIABLES) {
            checkEndLine();
        } else if (line.matches(IF_WHILE_REG) && scopeNum !=GLOBAL_VARIABLES){
            checkIfOrWhileLine(line);
        }
        else if(line.matches(CALL_METHOD)&& scopeNum !=GLOBAL_VARIABLES){
            checkCallMethodsLine(line);
        }
        else {
            throw new IllegalSyntaxException(SyntaxError);
        }
    }
    /**
     * The function verifies if the call method line is valid
     * @param line call method line valid or not valid
     * @throws GeneralException general exception that can appear in the code
     */
    private void checkCallMethodsLine(String line) throws GeneralException {
        Pattern p =Pattern.compile(CALL_METHOD);
        Matcher m = p.matcher(line);
        m.matches();
        String name = m.group(1).trim();
        String []  parentheses=line.substring(line.indexOf("(")+1,line.indexOf(")")).split(COMMA);
        ArrayList<Variable> parameters= new ArrayList<>();
        if(!parentheses[0].equals("")){
            for(String parameter:parentheses){
            Variable variable=checkVarInLastScope(parameter.trim());
            if(variable!=null)
            {
                if(variable.getValue()==null){
                    boolean flag=false;
                    if(scopeVar.containsKey(GLOBAL_VARIABLES)&& scopeVar.get(GLOBAL_VARIABLES).contains(variable)){
                        flag=true;
                    }
                    if(!flag){
                    throw new MethodException("the cethod call has a null parameter");
                    }
                }
                parameters.add(variable);
            }
            else{
                parameters.add( VariableFactory.getVariable(VariableFactory.CONSTANT,parameter.trim(),null,null,false));
            }
            }
        }
        MethodCall methodCall=new MethodCall(parameters,name);
        if(!this.methodsName.contains(name)){

            callUnknownMethods.add(methodCall);
        }
        else
        {
            checkMethodsParameters(methodCall);
        }
    }
    /**
     * The method verifies if a method call has corresponding parameters types
     * @param methodCall A method call
     * @throws GeneralException general exception that can appear in the code
     */
    private void checkMethodsParameters(MethodCall methodCall) throws GeneralException {
        for(Method method:methodsList){
            if (methodCall.getName().equals(method.getName())){
                method.checkCall(methodCall.getParameters());
            }
        }
    }
    /**
     * the method verifies if a while/if line is valid or not
     * @param line a while/if line is valid or not
     * @throws GeneralException general exception that can appear in the code
     */
    private void checkIfOrWhileLine(String line) throws GeneralException {

        String[] conditions= line.substring(line.indexOf("(")+1,line.indexOf(")")).split(OR_AND_REG);
        for(String cond :conditions){
            Variable var =checkVarInLastScope(cond.trim());
            if (cond.trim().matches(BOOL_REGEX) || var!=null && !var.getType().matches(CHAR+"|"+STRING)&& (var.getValue()!=null && !var.isParameter())){
                continue;
            }
            else if(var==null && cond.trim().matches(NAME_REG)){
                this.callUnknownVariable.add(VariableFactory.getVariable(VariableFactory.NOT_ASSIGN_GLOBAL_VARIABLE,null,cond.trim(), CONDITION_TYPE,false));
            }
            else{

                throw new IllegalConditionException(CONDITION_ERROR);
            }
        }
        scopeNum++;


    }
    /**
     * the method verifies if a end line is valid or not
     * @throws GeneralException general exception that can appear in the code
     */
    private void checkEndLine() throws GeneralException {
        if(this.method== scopeNum && this.method!=0){
            if(!this.codeLines.get(currentLine -1).trim().matches(RETURN_STATEMENT)){
                throw new ReturnErrorException(END_METHOD_ERROR);
            }
            else{
                this.method=0;
                if(scopeVar.containsKey(GLOBAL_VARIABLES)){
                    for(Variable var: scopeVar.get(GLOBAL_VARIABLES))
                    {
                        if(var.isReset())
                        {
                            var.setValue(null);
                        }
                    }
                }
            }
        }
        this.scopeVar.remove(scopeNum);
        this.scopeNum--;
    }
    /**
     * the method verifies if a return line is valid or not
     * @throws GeneralException general exception that can appear in the code
     */
    private void checkReturnLine() throws GeneralException {
        if(scopeNum ==0){
            throw new ReturnErrorException(RETURN_ERROR);
        }
    }
    /**
     * the method verifies if a method line is valid or not
     * @param line a method line valid or no
     * @throws GeneralException general exception that can appear in the code
     */
    private void checksMethodsLine(String line) throws GeneralException {

        Pattern p = Pattern.compile(START_METHOD_REG);
        Matcher m = p.matcher(line);
        m.matches();
        scopeNum += 1;
        method= scopeNum;
        String methodName = m.group(2).trim();
        if (checkMethodsName(methodName)) {
            if (!line.contains(EMPTY_PARAMETERS)) {
                ArrayList<Variable> varList = new ArrayList<>();
                String par =line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                String[] parameters = par.split(COMMA);
                if(!par.matches("\\s*"))
                {
                    for (String parm : parameters) {
                    String type = extractType(parm).trim();
                    parm = parm.replace(type, "").trim();
                    boolean fin = false;
                    if (type.contains(FINAL)) {
                        fin = true;
                        type = type.split(FINAL)[1].trim();
                    }
                    checkNameVarInScope(parm);
                    Variable parameter = VariableFactory.getVariable(VariableFactory.PARAMETER,null,parm,type,fin);
                    varList.add(parameter);
                    this.add_var_in_scope(parameter);
                }
                }
                Method method = new Method(varList, methodName, "void");
                this.methodsName.add(methodName);
                methodsList.add(method);
            }
            else {
                Method method = new Method(new ArrayList<>(), methodName, "void");
                this.methodsName.add(methodName);
                methodsList.add(method);

            }
        }
    }
    /**
     * the method verifies if a comment line is valid or not
     * @param line a comment line valid or no
     * @throws GeneralException general exception that can appear in the code
     */
    private void checksCommentLine(String line) throws GeneralException {
        if (!line.startsWith(PREFIX_COMMENT)) {
            throw new CommentException(COMMENT_ERROR);
        }
    }
    /**
     * the method verifies if a variable line is valid or not
     * @param lineStr a variable line valid or no
     * @throws GeneralException general exception that can appear in the code
     */
    private void checksVariableLine(String lineStr) throws GeneralException {
        String[] line = lineStr.split(COMMA);
        String type = extractType(line[0]).trim();
        boolean fin = false;
        if (type.contains(FINAL)) {
            fin = true;
            type = type.split(FINAL)[1].trim();
        }

        for (int i = 0; i < line.length; i++) {
            if (!line[i].contains(END_LINE)) {
                line[i] += END_LINE;
            }
            if (i != 0) {
                line[i] = type +" "+ line[i];
            }
        }

        createVar(type, line, fin);

    }
    /**
     * the method verifies if a assigment value line is valid or not
     * @param line a assigment value line valid or no
     * @throws GeneralException general exception that can appear in the code
     */
    private void checkAssigmentValueLine(String line) throws GeneralException {

        line = line.replace(END_LINE, "");
        String[] all_var = line.split(COMMA);
        for (String var : all_var) {

            String var_name = var.split(EQUAL)[0].trim();
            String var_value=null;
            if(var.contains(EQUAL)){
                var_value = var.split(EQUAL)[1].trim();
            }
            Variable variable = null;
            if(scopeVar.containsKey(GLOBAL_VARIABLES)) {
                for (Variable globalVar : this.scopeVar.get(0)) {
                    if (var_name.equals(globalVar.getName())) {
                        variable = globalVar;
                    }
                }
            }
            if(scopeVar.containsKey(scopeNum)){
                for (Variable local : this.scopeVar.get(scopeNum)) {
                    if (var_name.equals(local.getName())) {
                        variable = local;
                    }
                }
            }

            if (variable == null) {
                throw new VariableAssignmentException(NAME_NOT_EXIST_ERROR);
            }
            checkValue(var_name,variable.getType(),var_value);
            variable.setValue(var_value);
        }

    }

    /**
     * the function returns the type of variable
     * @param var variable type
     * @return the type
     */
    private String extractType(String var) {
        Pattern p = Pattern.compile(VAR_REG);
        Matcher m = p.matcher(var);
        m.find();
        String type = var.substring(m.start(), m.end());
        return type;
    }

    /**
     * the function verifies and create a new variable
     * @param type type of variable
     * @param line lie variable
     * @param fin if the variable is a final one
     * @throws GeneralException general exception that can appear in the code
     */
    private void createVar(String type, String[] line, boolean fin) throws GeneralException {
        for (String elem : line) {
            if (elem.matches(DECLARATION_REG)) {
                if (fin) {
                    throw new VariableAssignmentException(FINAL_DEC_ERROR);
                }
                String name = elem.replace(type, "").replace(END_LINE, "").trim();
                if (checkNameVarInScope(name)){
                    createVariableNot_assign(type, name);
                }

            } else if (elem.matches(ONE_ASSIGNATION_REG)) {
                Pattern p = Pattern.compile(VAR_REG);
                Matcher m = p.matcher(elem);
                m.find();
                elem = elem.substring(m.end()).trim();
                elem = elem.replace(END_LINE, "");
                setVarValue(type, elem, fin);

            }
        }
    }

    /**
     * the function adds to the map scopeVar a variable
     * @param variable variable we add to the scopeVar map
     */
    private void add_var_in_scope(Variable variable) {
        if (this.scopeVar.get(scopeNum) == null) {
            ArrayList<Variable> varList = new ArrayList<>();
            varList.add(variable);
            this.scopeVar.put(scopeNum, varList);
        } else {
            this.scopeVar.get(scopeNum).add(variable);
        }
    }

    /**
     * the functions creates a variable without a value
     * @param type type of the variable
     * @param elem name of the variable
     */
    private void createVariableNot_assign(String type, String elem) {
        if(scopeNum ==GLOBAL_VARIABLES) {
            add_var_in_scope(VariableFactory.getVariable(VariableFactory.NOT_ASSIGN_GLOBAL_VARIABLE,
                    null,elem,type,false));
        }
        else {
            add_var_in_scope(VariableFactory.getVariable(VariableFactory.NOT_ASSIGN_VARIABLE,
                    null,elem,type,false));
        }
    }

    /**
     * the function creates ta variable with a value
     * @param type type of the variable
     * @param elem line
     * @param fin if the variable is a final one
     * @throws GeneralException general exception that can appear in the code
     */
    private void setVarValue(String type, String elem, boolean fin) throws GeneralException {
        String[] splitDec = elem.split(EQUAL);
        String name = splitDec[0].trim();
        String val = splitDec[1].trim();
        String finalValue =checkValue(name,type,val);
        if (finalValue==null){
            finalValue =val;
        }
        if(checkNameVarInScope(name))
        {

            Variable variable = VariableFactory.getVariable(VariableFactory.VARIABLE,finalValue,name,type,fin);

            add_var_in_scope(variable);
        }
    }

    /**
     * the function verifies if there is no methods with this name
     * @param name name of the method
     * @return true if there isn't a  method with the same name
     * @throws GeneralException general exception that can appear in the code
     */
    private boolean checkMethodsName(String name) throws GeneralException {
        if (methodsName.contains(name)) {
            throw new MethodException(METHODS_NAME_ERROR);
        }
        return true;
    }

    /**
     * the function checks if it exists a variable with the same name in the current scope
     * @param name name of the variable
     * @return return true if there isn't a variable with the same name
     * @throws GeneralException general exception that can appear in the code
     */
    private boolean checkNameVarInScope(String name) throws GeneralException {
        if(scopeVar.containsKey(scopeNum)){
            for (Variable local : scopeVar.get(scopeNum)) {
                if (name.equals(local.getName())) {
                    throw new VarInitializedException(VAR_NAME_ERROR);
                }
            }
        }
        return true;
    }

    /**
     * the method checks if the variable with the name: name exists
     * @param name name of the variable
     * @return the variable
     */
    private Variable checkVarInLastScope(String name) {
        if(method!=0)
        {
            int i = scopeNum;
            while(i>=method){
                if(scopeVar.containsKey(i) && scopeVar.get(i).size()!=0 ){
                    for (Variable var: scopeVar.get(i)){
                        if(var.getName().equals(name)){
                            return var;
                        }
                    }
                }
                i--;
            }
        }
        if(scopeVar.containsKey(GLOBAL_VARIABLES)&&scopeVar.get(GLOBAL_VARIABLES).size()!=0){
            for(Variable var: scopeVar.get(GLOBAL_VARIABLES))
            {
                if(var.getName().equals(name) && var.getValue()!=null){
                    return var;
                }
            }
        }
        return null;
    }

    /**
     * the methods checks if the value correspond to the type
     * @param name name of the variable
     * @param type type of the variable
     * @param value value of the variable
     * @return the value
     * @throws GeneralException general exception that can appear in the code
     */
    private String checkValue(String name,String type, String value) throws GeneralException {
        String[] types = {INT, STRING, CHAR, DOUBLE,BOOL};
        String[] typesRegex = {INT_REGEX, STRING_REGEX, CHAR_REGEX, DOUBLE_REGEX,BOOL_REGEX};
        int check=5;
        for(int i=0;i<5;i++){
            if(type.equals(types[i])){
                check=i;
                break;
            }
        }
        if(!value.matches(typesRegex[check])){
            for(String reg: typesRegex){
                if(value.matches(reg)){
                    throw new VariableAssignmentException(ASSIGNMENT_ERROR);
                }
            }
            if(value.matches(NAME_REG)){
                Variable val=checkAssignmentByVar(name,type,value);

                if(val!=null){
                    if(val.getValue()==null && !val.isParameter()){
                        throw new VarInitializedException(ASSIGN_NULL_VALUE_ERROR);
                    }
                    return val.getValue();
                }
            }
            else{

                throw new VariableAssignmentException(VALUE_DONT_EXIST);
            }
        }
        return null;
    }

    /**
     * the method checks the assignment of a variable
     * @param name name of the variable
     * @param type type of the variable
     * @param value value of the variable
     * @return the variable
     * @throws GeneralException general exception that can appear in the code
     */
    private Variable checkAssignmentByVar(String name ,String type, String value) throws GeneralException {
        if(method!=0){
            int i = scopeNum;
            while(i>=method){
                if(scopeVar.containsKey(i) && scopeVar.get(i).size()!=0 ){
                    for(Variable local: this.scopeVar.get(i)){
                        if(type.equals(local.getType())&& value.equals(local.getName())){
                            return local;
                        }
                        if(type.equals(DOUBLE)&& local.getType().equals(INT)){
                            return local;
                        }
                        if(type.equals(BOOL)&& (local.getType().equals(INT) ||local.getType().equals(DOUBLE))){
                            return local;
                        }
                    }
                }
             i--;
            }
        }
        if (scopeVar.containsKey(GLOBAL_VARIABLES)){
            for (Variable global : this.scopeVar.get(GLOBAL_VARIABLES)) {
                if(scopeNum ==GLOBAL_VARIABLES && value.equals(global.getName()))
                {
                    if(type.equals(BOOL)&& (global.getType().equals(INT) ||global.getType().equals(DOUBLE))){
                        return global;
                    }
                    if(type.equals(DOUBLE)&& global.getType().equals(INT)){
                        return global;
                    }
                    if(!type.equals(global.getType())){
                        throw new VarInitializedException(ASSIGNMENT_ERROR);
                    }
                    if(global.getValue()==null){
                        throw new VarInitializedException(ASSIGN_NULL_VALUE_ERROR);
                    }
                }
                if (type.equals(global.getType()) && value.equals(global.getName())) {
                    return global;
                }
            }
        }
        if (scopeNum ==GLOBAL_VARIABLES)
        {
            boolean flag=false;
            if(scopeVar.containsKey(GLOBAL_VARIABLES)){
                for (Variable global : this.scopeVar.get(GLOBAL_VARIABLES)){
                if(value.equals(global.getName())){
                    flag=true;
                    break;
                }
            }
            }
            if (!flag){
                throw new VariableAssignmentException(VALUE_DONT_EXIST);
            }
        }
        if(name.equals(value)){
            throw new VariableAssignmentException(ASSIGN_NULL_VALUE_ERROR);
        }
        Variable varUnknown =VariableFactory.getVariable(VariableFactory.NOT_ASSIGN_VARIABLE,null,value,type,false);
        callUnknownVariable.add(varUnknown);

        return null;
    }

}
