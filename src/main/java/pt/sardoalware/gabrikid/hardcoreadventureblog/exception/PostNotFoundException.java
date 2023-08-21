package pt.sardoalware.gabrikid.hardcoreadventureblog.exception;

public class PostNotFoundException extends Throwable {

    public PostNotFoundException() {
        super("The post was not found.");
    }

}