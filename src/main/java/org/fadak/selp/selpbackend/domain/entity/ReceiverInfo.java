/**
 * @담당자: 정재영
 */

package org.fadak.selp.selpbackend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverModifyRequestDto;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Entity
@Table(name = "RECEIVER_INFO")
@NoArgsConstructor
public class ReceiverInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECEIVER_INFO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID") // TODO : REGISTER_MEMBER_ID로 수정하기
    private Member member; // TODO : registerMember로 수정하기

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "GENDER")
    private String gender = "NONE";

    @Column(name = "RELATIONSHIP")
    private String relationship;
    // ex.
    // [가족] 엄마, 아빠, 아들, 딸
    // [친구] 동성 친구, 친한 친구
    // [애인] 여자친구, 남자친구
    // [회사] 회사 동료, 회사 상사, 회사 동기
    // 기타

    @Column(name = "PREFERENCES") // 취향
    private String preferences;

    @Column(name = "DETAIL") // 세부 사항
    private String detail;

    @Builder
    public ReceiverInfo(Member member, String nickname, Integer age, String gender,
        String relationship, String preferences, String detail) {

        this.member = member;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender != null ? gender : "NONE";
        this.relationship = relationship;
        this.preferences = preferences;
        this.detail = detail;
    }

    public void update(ReceiverModifyRequestDto request) {

        this.nickname = request.getNickname();
        this.age = request.getAge();
        this.gender = request.getGender();
        this.relationship = request.getRelationship();
        this.preferences = request.getPreferences();
        this.detail = request.getDetail();
    }
}