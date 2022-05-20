package org.zerock.sp1.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTests {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() {
        String msg ="1111";

        String enStr = passwordEncoder.encode(msg);

        System.out.println(enStr);
        //$2a$10$Arbv3X9rOeFbv5CnPDCd6umiIszsewHOg31FYxq0vjFDgzwVahh3m
        //$2a$10$CFGxXjKVdJxTJV.cTxQ2RuY1j984hT0IzzlUeWjEh9ITKDSh2N3sK
        //$2a$10$1wYTQpLkjZCZ1DQwIkOqp.bb/6S1Vvf4gDsSo7yrInjOrCa64wrde
    }


    @Test
    public void test1(){
        log.info(sampleService);
    }

    @Test
    public void testLink(){

        String mainImage = "/view?fileName=2022/05/06/s_db656b38-55b4-498f-af82-23118a518035_20200324_124238.jpg";

        int idx = mainImage.indexOf("s_");
        String first = mainImage.substring(0, idx);
        String second = mainImage.substring(idx+2);

        log.info(first);
        log.info(second);

        log.info(first+second);


    }

}
