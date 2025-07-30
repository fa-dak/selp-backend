package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter // JSON 매핑
@ToString
@NoArgsConstructor
//@AllArgsConstructor
public class PayRequestDto {

    private String impUid;
}
