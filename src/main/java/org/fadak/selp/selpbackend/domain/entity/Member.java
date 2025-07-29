/**
 * @담당자: 박찬혁
 */

package org.fadak.selp.selpbackend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBER")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "PROVIDER")
    private String provider = "KAKAO";

    @Builder
    public Member(String email, String nickname, String provider) {

        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
    }
}