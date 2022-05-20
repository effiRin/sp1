package org.zerock.sp1.mapper;


import org.apache.ibatis.annotations.Insert;

public interface TimeMapper {

    String getTime();

    @Insert("insert into tbl_a (text) values (#{text})")
    void insertA(String text);

    @Insert("insert into tbl_b (text) values (#{text})")
    void insertB(String text);
}
