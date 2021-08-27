package fon.njt.EvidencijaZavrsnihRadovaapi.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
