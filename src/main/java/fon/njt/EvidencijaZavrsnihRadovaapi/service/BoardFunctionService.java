package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.BoardFunction;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.BoardFunctionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardFunctionService {
    private final BoardFunctionRepository boardFunctionRepository;


    public List<BoardFunction> findByFunctionAndProfessorId(String function, Long personId) {
        Optional<List<BoardFunction>> bfOptional = boardFunctionRepository.findAllByFunctionAndProfessorPersonId(function, personId);
        if (!bfOptional.isPresent()) {
            throw new NotPresentException("Board functions not found");
        }
        return bfOptional.get();
    }
}
