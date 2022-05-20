package org.zerock.sp1.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.sp1.domain.Smember;
import org.zerock.sp1.dto.SmemberDTO;
import org.zerock.sp1.mapper.SmemberMapper;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SmemberMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("===========================");
        log.info("===========================");
        log.info(username);

        Smember smember = mapper.selectOne(username);

        log.info(smember);

        log.info("===========================");
        log.info("===========================");

        List<SimpleGrantedAuthority> authList = smember.getSauthList().stream()
                .map(sauth ->new SimpleGrantedAuthority("ROLE_"+sauth.getRoleName()))
                .collect(Collectors.toList());

        //username, password, Authority List
        SmemberDTO smemberDTO = new SmemberDTO(smember.getMid(), smember.getMpw(),authList);
        smemberDTO.setNickname(smember.getNickname());

        return smemberDTO;
    }
}