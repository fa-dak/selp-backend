/**
 * @담당자: 정재영
 */

package org.fadak.selp.selpbackend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fadak.selp.selpbackend.domain.constant.Gender;

@Getter
@Setter
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
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "RELATIONSHIP")
    private String relationship;

    @Column(name = "DETAIL") // 세부 사항
    private String detail;

    @OneToMany(mappedBy = "receiverInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Preference> preferences;

    @OneToMany(mappedBy = "receiverInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Event> events;

    @Builder
    public ReceiverInfo(Member member, String nickname, int age, Gender gender,
        String relationship, String detail) {

        this.member = member;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.relationship = relationship;
        this.detail = detail;
    }

    public void update(String nickname, int age, Gender gender,
        String relationship, String detail) {

        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.relationship = relationship;
        if (detail != null) {
            this.detail = detail;
        }
    }

    public void addPreference(Preference preference) {

        if (this.preferences == null) {
            this.preferences = new ArrayList<>();
        }
        this.preferences.add(preference);
        preference.setReceiverInfo(this); // 역방향도 설정
    }
}