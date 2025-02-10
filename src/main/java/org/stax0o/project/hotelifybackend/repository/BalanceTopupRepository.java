package org.stax0o.project.hotelifybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.stax0o.project.hotelifybackend.entity.BalanceTopup;

import java.util.List;

public interface BalanceTopupRepository extends JpaRepository<BalanceTopup, Long> {
    List<BalanceTopup> findAllByUser_Id(Long id);
}
