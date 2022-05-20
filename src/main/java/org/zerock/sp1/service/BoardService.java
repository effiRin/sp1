package org.zerock.sp1.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.sp1.dto.BoardDTO;
import org.zerock.sp1.dto.ListDTO;
import org.zerock.sp1.dto.ListResponseDTO;
import org.zerock.sp1.dto.UploadResultDTO;

import java.util.List;

@Transactional
public interface BoardService {

    void register(BoardDTO boardDTO);

    ListResponseDTO<BoardDTO> getList(ListDTO listDTO);

    BoardDTO getOne(Integer bno);

    void update(BoardDTO boardDTO);

    void remove(Integer bno);

    List<UploadResultDTO> getFiles(Integer bno);
}
