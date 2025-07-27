package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.GiftBundleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GiftBundleItemRepository extends JpaRepository<GiftBundleItem, Long> {

    List<GiftBundleItem> findAllByGiftBundleIdIn(Collection<Long> giftBundleIds);
}