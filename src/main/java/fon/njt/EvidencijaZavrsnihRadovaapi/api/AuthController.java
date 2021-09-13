package fon.njt.EvidencijaZavrsnihRadovaapi.api;


import fon.njt.EvidencijaZavrsnihRadovaapi.dto.AuthenticationResponse;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.LoginRequest;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.RefreshTokenRequest;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.RegisterRequest;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.AuthService;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("auth/")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<String> signupStudent(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body("User Registration Successful");
    }

    @PostMapping("login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK); //HttpStatus.OK
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }


}
