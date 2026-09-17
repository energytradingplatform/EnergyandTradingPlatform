package com.Trading.tradeservice.respositories;

import com.Trading.tradeservice.models.CounterParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterpartyRepository extends JpaRepository<CounterParty, Long> {
    boolean existsByCode(String code);
}
