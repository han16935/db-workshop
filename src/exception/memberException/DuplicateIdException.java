package exception.memberException;

public class DuplicateIdException extends RuntimeException{
    public DuplicateIdException(String message){
        super(message);
    }
}
