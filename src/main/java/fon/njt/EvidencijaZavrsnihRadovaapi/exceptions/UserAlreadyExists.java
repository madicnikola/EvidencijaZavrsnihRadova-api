package fon.njt.EvidencijaZavrsnihRadovaapi.exceptions;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String message) {
        super(message);
    }
}
