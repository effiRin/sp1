package org.zerock.sp1.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.sp1.domain.Board;
import org.zerock.sp1.dto.ListDTO;

import java.util.List;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/root-context.xml")
public class BoardMapperTests {

    @Autowired(required = false)
    private BoardMapper boardMapper;

    @Test
    public void testInsert() {

        Board board = Board.builder().title("1111제목").content("내용").writer("user1").build();

        boardMapper.insert(board);



        log.info(board);

    }

    @Test
    public void testSelectList(){

        ListDTO listDTO = new ListDTO();
        listDTO.setType("tcw");
        listDTO.setKeyword("11");

        List<Board> boardList = boardMapper.selectList(listDTO);

        boardList.forEach(board -> log.info(board));

    }

    @Test
    public void testSelectOne() {

        int bno = 100;

        Board board = boardMapper.selectOne(bno);

        log.info(board);
    }
    @Test
    public void testDelete(){
        int bno = 100;

        boardMapper.delete(bno);

    }


}
