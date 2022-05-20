package org.zerock.sp1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.zerock.sp1.domain.Sauth;
import org.zerock.sp1.domain.Smember;

public interface SmemberMapper {

    @Insert("insert into tbl_smember (mid,mpw,mname,nickname) values (#{mid}, #{mpw}, #{mname}, #{nickname})")
    void register(Smember smember);

    @Insert("insert into tbl_sauth (mid,rolename) values (#{mid}, #{roleName})")
    void addAuth(Sauth sauth);

    Smember selectOne(String mid);

}