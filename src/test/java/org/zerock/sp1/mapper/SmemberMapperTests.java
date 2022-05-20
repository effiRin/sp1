package org.zerock.sp1.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.sp1.domain.Sauth;
import org.zerock.sp1.domain.Smember;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/root-context.xml")
public class SmemberMapperTests {

    @Autowired(required = false)
    private SmemberMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {

        for (int i = 0; i < 100; i++) {

            Smember smember = Smember.builder()
                    .mid("user"+i)
                    .mpw(passwordEncoder.encode("111"))
                    .mname("USER"+i)
                    .nickname("사용자"+i)
                    .build();

            mapper.register(smember);

            if(i > 80){
                Sauth sauth = Sauth.builder().mid("user"+i).roleName("ADMIN").build();

                mapper.addAuth(sauth);

            }

            Sauth sauth = Sauth.builder().mid("user"+i).roleName("MEMBER").build();

            mapper.addAuth(sauth);


        }//end for
    }

    @Test
    public void testSelectOnt(){
        String mid = "user50";
        Smember smember = mapper.selectOne(mid);
        log.info(smember);
    }

}