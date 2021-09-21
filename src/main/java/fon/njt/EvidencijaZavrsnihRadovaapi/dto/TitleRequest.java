package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TitleRequest {
    private Long studentId;
    private Long professorId;
    private String proposedTitle;
}
