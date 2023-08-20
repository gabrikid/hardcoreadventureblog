package pt.sardoalware.gabrikid.hardcoreadventureblog.exception;

public class AuthorNotFoundException extends Throwable {

    public AuthorNotFoundException() {
        super("The author was not found.");
    }

}