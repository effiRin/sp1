package org.zerock.sp1.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
public class SmemberDTO extends User { // 회원조회 (로그인)할 때만 사용할 것

    private String mid, mpw, mname, nickname;
    private LocalDateTime regDate, updateDate;

    public SmemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.mid = username;
        this.mpw = password;
    }   // 여기 패스워드가 있지만, 패스워드의 경우 외부에서 노출이 안되도록 잠궈둬서 패스워드를 볼 수 없음. 웹 시큐리티는 기본적으로 세션을 쓴다.
        //  로그인을 하면 세션 저장소에 얘가 DB에 저장됨
}
