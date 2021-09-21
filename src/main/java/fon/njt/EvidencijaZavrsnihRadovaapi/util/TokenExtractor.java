package fon.njt.EvidencijaZavrsnihRadovaapi.util;

import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.InvalidHeaders;
import org.springframework.http.HttpHeaders;

public class TokenExtractor {

    public static String getTokenFromHeaders(HttpHeaders headers) {
        String token = headers.getFirst("Authorization") == null ? "" : headers.getFirst("Authorization").substring(7);
        if (token.isEmpty())
            throw new InvalidHeaders("Unauthorized!");

        return token;
    }
}
