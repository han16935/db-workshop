package exception.followException;

public class DuplicateFollowRelationshipException extends RuntimeException{
    public DuplicateFollowRelationshipException(String message) {
        super(message);
    }
}
