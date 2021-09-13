package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.VisibilityStatus;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.InvalidHeaders;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.ThesisMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.GraduateThesisRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.security.JwtProvider;
import io.jsonwebtoken.Jwt;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GraduateThesisService {
    private final GraduateThesisRepository graduateThesisRepository;
    private final ThesisMapper thesisMapper;
    private final JwtProvider tokenService;


    public List<GraduateThesis> getAllPublished() {
        return graduateThesisRepository.findAllByVisibilityStatus(VisibilityStatus.PUBLISHED);
    }

    public List<GraduateThesis> getAll() {
        return graduateThesisRepository.findAll();
    }

    public void save(ThesisDto thesisDto) {
        graduateThesisRepository.save(thesisMapper.thesisDtoToThesis(thesisDto));
    }

    public GraduateThesis get(HttpHeaders headers) {
        String studentUsername = getOwnerUsernameFromHeaders(headers);
        Optional<GraduateThesis> thesis = graduateThesisRepository.findByStudent_UserProfileUsername(studentUsername);
        if(!thesis.isPresent()){
                throw new NotPresentException("thesis not found");
        }
        return thesis.get();
    }


    private String getOwnerUsernameFromHeaders(HttpHeaders headers) {
        String token = headers.getFirst("Authorization") == null ? "" : headers.getFirst("Authorization").substring(7);
        if (token.isEmpty())
            throw new InvalidHeaders("Unauthorized!");

        return tokenService.getUsernameFromJwt(token);
    }
}
