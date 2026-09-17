package com.Trading.tradeservice.respositories;

import com.Trading.tradeservice.models.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    boolean existsByCode(String code);
}
