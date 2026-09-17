package com.Trading.tradeservice.respositories;

import com.Trading.tradeservice.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByCode(String code);
}
