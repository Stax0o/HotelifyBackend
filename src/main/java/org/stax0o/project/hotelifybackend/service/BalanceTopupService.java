package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stax0o.project.hotelifybackend.dto.BalanceTopupDTO;
import org.stax0o.project.hotelifybackend.entity.BalanceTopup;
import org.stax0o.project.hotelifybackend.entity.User;
import org.stax0o.project.hotelifybackend.mapper.BalanceTopupMapper;
import org.stax0o.project.hotelifybackend.repository.BalanceTopupRepository;
import org.stax0o.project.hotelifybackend.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceTopupService {
    private final BalanceTopupRepository balanceTopupRepository;
    private final BalanceTopupMapper balanceTopupMapper;
    private final UserRepository userRepository;

    @Transactional
    public BalanceTopupDTO create(BalanceTopupDTO balanceTopupDTO, User user) {

        BalanceTopup balanceTopup = balanceTopupMapper.toEntity(balanceTopupDTO);

        user.setBalance(user.getBalance() + balanceTopup.getAmount());
        user.setUpdatedAt(LocalDate.now());
        userRepository.save(user);

        balanceTopup.setUser(user);

        balanceTopup = balanceTopupRepository.save(balanceTopup);
        return balanceTopupMapper.toDTO(balanceTopup);
    }

    public List<BalanceTopupDTO> findAllTopUpsByUserId(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        return balanceTopupMapper.toDTOList(balanceTopupRepository.findAllByUserId(id));
    }
}
