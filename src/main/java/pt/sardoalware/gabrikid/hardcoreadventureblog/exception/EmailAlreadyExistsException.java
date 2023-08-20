package pt.sardoalware.gabrikid.hardcoreadventureblog.exception;

public class EmailAlreadyExistsException extends Throwable {

    public EmailAlreadyExistsException() {
        super("The email address is already in use.");
    }

}