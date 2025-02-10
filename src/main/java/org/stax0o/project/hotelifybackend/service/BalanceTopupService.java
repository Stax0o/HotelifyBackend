package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stax0o.project.hotelifybackend.entity.BalanceTopup;
import org.stax0o.project.hotelifybackend.repository.BalanceTopupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceTopupService {
    private final BalanceTopupRepository balanceTopupRepository;

    public BalanceTopup create(BalanceTopup balanceTopup) {
        return balanceTopupRepository.save(balanceTopup);
    }

    public List<BalanceTopup> findAllTopUpsByUserId(Long id) {
        return balanceTopupRepository.findAllByUser_Id(id);
    }
}
