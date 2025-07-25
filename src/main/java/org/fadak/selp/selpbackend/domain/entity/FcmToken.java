package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.constant.DeviceType;


@Getter
@Entity
@Table(name = "FCM_TOKEN")
public class FcmToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FCM_TOKEN_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(name = "TOKEN", nullable = false, length = 512)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEVICE_TYPE")
    private DeviceType deviceType;

    public static FcmToken of(Member member, String token, DeviceType deviceType) {
        FcmToken fcmToken = new FcmToken();
        fcmToken.member = member;
        fcmToken.token = token;
        fcmToken.deviceType = deviceType;
        return fcmToken;
    }

    public void updateToken(String token, DeviceType deviceType) {
        this.token = token;
        this.deviceType = deviceType;
    }
}