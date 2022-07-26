package com.clxm.auth;

import com.clxm.domain.Player;
import com.clxm.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        log.info("loadUserByUsername = {}", id);

        Player player = playerRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new UsernameNotFoundException("선수를 찾을 수 없습니다"));

        return new PlayerDetails(player);
    }
}
