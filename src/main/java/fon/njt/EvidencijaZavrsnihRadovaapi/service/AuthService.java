package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.*;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.*;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.AppException;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.UserNotFoundException;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.*;
import fon.njt.EvidencijaZavrsnihRadovaapi.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {


    private final UserProfileRepository userProfileRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final AcademicRankRepository academicRankRepository;
    private final TitleRepository titleRepository;
    private final AuthenticationManager authenticationManager;
    private final fon.njt.EvidencijaZavrsnihRadovaapi.service.MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final GraduateThesisRepository thesisRepository;

    private String generateVerificationToken(UserProfile user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUserProfile(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new AppException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());

    }

    @Transactional
    protected void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUserProfile().getUsername();
        UserProfile user = userProfileRepository.findByUsername(username).orElseThrow(() -> new AppException("User not found " +
                username));
        user.setEnabled(true);
        userProfileRepository.save(user);
    }


    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationMillis()))
                .username(loginRequest.getUsername())
                .role(userProfileRepository.findByUsername(loginRequest.getUsername()).get().getRole().getName())
                .build();
    }

    @Transactional
    public UserProfile getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userProfileRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with name - " + principal.getUsername()));
    }

    public String getCurrentUserUsername() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public void signup(RegisterRequest registerRequest) {
        UserProfile user = new UserProfile();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        if (registerRequest.getIndex() != null) {
            registerStudent(registerRequest, user);
        } else {
            registerProfessor(registerRequest, user);
        }

        String token = generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(), "http://localhost:8080/auth/accountVerification/" + token));
    }

    private void registerProfessor(RegisterRequest registerRequest, UserProfile user) {
        Professor p = new Professor();
        p.setName(registerRequest.getName());
        p.setSurname(registerRequest.getSurname());
        p.setBirthDate(registerRequest.getBirthDate());

        p.setAcademicRank(academicRankRepository.getByName(registerRequest.getAcademicRank()));
        p.setTitle(titleRepository.getByName(registerRequest.getTitle()));

        p.setUserProfile(user);
        user.setRole(roleRepository.findByName("PROFESSOR").get());
        userProfileRepository.save(user);
        professorRepository.save(p);
    }

    private void registerStudent(RegisterRequest registerRequest, UserProfile user) {
        Student s = new Student();
        s.setName(registerRequest.getName());
        s.setSurname(registerRequest.getSurname());
        s.setBirthDate(registerRequest.getBirthDate());

        s.setDegreeOfStudy(registerRequest.getDegreeOfStudy());
        s.setDepartment(departmentRepository.getByName(registerRequest.getDepartment()));
        s.setIndexNumber(registerRequest.getIndex());

        s.setUserProfile(user);
        user.setRole(roleRepository.findByName("STUDENT").get());
        userProfileRepository.save(user);
        s = studentRepository.save(s);
        GraduateThesis thesis = GraduateThesis.builder().student(s)
                .faculty("Fakultet Organizacionih Nauka")
                .progressStatus(ProgressStatus.INITIAL)
                .visibilityStatus(VisibilityStatus.PRIVATE)
                .build();
        thesis = thesisRepository.save(thesis);
    }

}
