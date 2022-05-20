package org.zerock.sp1.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.sp1.domain.AttachFile;
import org.zerock.sp1.domain.Board;

import java.util.List;

public interface BoardMapper extends GenericMapper<Board,Integer> {


    void updateReplyCount(@Param("bno") Integer bno, @Param("amount")  int amount);

    List<AttachFile> selectFiles(Integer bno);

}
