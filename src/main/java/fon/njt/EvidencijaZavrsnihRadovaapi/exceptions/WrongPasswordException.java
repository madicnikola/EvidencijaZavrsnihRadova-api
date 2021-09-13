package fon.njt.EvidencijaZavrsnihRadovaapi.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String message) {
        super(message);
    }
}
