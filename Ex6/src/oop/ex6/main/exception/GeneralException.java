package oop.ex6.main.exception;

public class GeneralException extends Exception {
    private final  String message;
    public GeneralException(String msg){
        this.message=msg;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
