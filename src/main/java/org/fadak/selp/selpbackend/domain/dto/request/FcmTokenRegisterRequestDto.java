package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fadak.selp.selpbackend.domain.constant.DeviceType;

@Getter
@Setter
@Builder
public class FcmTokenRegisterRequestDto {
    private String token;
    private DeviceType deviceType;
}
