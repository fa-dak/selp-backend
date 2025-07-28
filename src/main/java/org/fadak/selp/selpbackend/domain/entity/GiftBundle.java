package org.fadak.selp.selpbackend.domain.entity; /**
 * @담당자: 원승현, 이지연
 */

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "GIFT_BUNDLE")
public class GiftBundle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GIFT_BUNDLE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    @OneToMany(mappedBy = "giftBundle")
    private List<GiftBundleItem> giftBundleItems = new ArrayList<>();

    @Builder
    public GiftBundle(Member member, Event event) {
        this.member = member;
        this.event = event;
    }
}