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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RECEIVER_INFO")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
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
    private int age;

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

    @Column(name = "DETAIL") // 세부 사항
    private String detail;

    @OneToMany(mappedBy = "receiverInfo", fetch = FetchType.LAZY)
    private List<Preference> preferences;

    @Builder
    public ReceiverInfo(Member member, String nickname, int age, String gender,
        String relationship, String detail, List<Preference> preferences) {

        this.member = member;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.relationship = relationship;
        this.detail = detail;
        this.preferences = preferences;
    }

    public void update(String nickname, int age, String gender,
        String relationship, String detail, List<Preference> preferences) {

        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.relationship = relationship;
        if (detail != null) {
            this.detail = detail;
        }
        this.preferences = preferences;
    }
}