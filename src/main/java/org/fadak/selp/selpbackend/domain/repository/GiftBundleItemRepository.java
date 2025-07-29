package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.GiftBundleItem;
import org.fadak.selp.selpbackend.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GiftBundleItemRepository extends JpaRepository<GiftBundleItem, Long> {

    List<GiftBundleItem> findAllByGiftBundleIdIn(Collection<Long> giftBundleIds);

    List<GiftBundleItem> findAllByGiftBundleId(Long giftBundleId);

    @Query("SELECT gp.product FROM GiftBundleItem gp WHERE gp.giftBundle.id = :bundleId")
    List<Product> findAllProductsByBundleId(@Param("bundleId") Long bundleId);
}
