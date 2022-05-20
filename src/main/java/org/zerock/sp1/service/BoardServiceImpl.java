package org.zerock.sp1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.sp1.domain.AttachFile;
import org.zerock.sp1.domain.Board;
import org.zerock.sp1.dto.BoardDTO;
import org.zerock.sp1.dto.ListDTO;
import org.zerock.sp1.dto.ListResponseDTO;
import org.zerock.sp1.dto.UploadResultDTO;
import org.zerock.sp1.mapper.BoardMapper;
import org.zerock.sp1.mapper.FileMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;
    private final ModelMapper modelMapper;

    @Override
    public void register(BoardDTO boardDTO) {

        Board board = modelMapper.map(boardDTO, Board.class);

        List<AttachFile> files =
                boardDTO.getUploads().stream().map(uploadResultDTO -> modelMapper.map(uploadResultDTO, AttachFile.class)
                ).collect(Collectors.toList());

        log.info("========================");

        log.info("========================");

        log.info(board);
        log.info(files);

        boardMapper.insert(board);

        files.forEach(file -> fileMapper.insert(file));


        log.info("========================");

        log.info("========================");

    }

    @Override
    public ListResponseDTO<BoardDTO> getList(ListDTO listDTO) {

        List<Board> boardList = boardMapper.selectList(listDTO);

        List<BoardDTO> dtoList =
                boardList.stream()
                        .map(board -> modelMapper.map(board, BoardDTO.class))
                        .collect(Collectors.toList());

        return ListResponseDTO.<BoardDTO>builder()
                .dtoList(dtoList)
                .total(boardMapper.getTotal(listDTO))
                .build();
    }

    @Override
    public BoardDTO getOne(Integer bno) {

        Board board = boardMapper.selectOne(bno);

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void update(BoardDTO boardDTO) {

        log.info("==================================================");

        log.info("==================================================");

        log.info(boardDTO);


        log.info("==================================================");

        log.info("==================================================");

        //기존 파일들 모두 삭제
        fileMapper.delete(boardDTO.getBno());
//
        boardMapper.update(Board.builder()
                        .bno(boardDTO.getBno())
                        .title(boardDTO.getTitle())
                        .content(boardDTO.getContent())
                        .mainImage(boardDTO.getMainImage())
                .build());

        for (UploadResultDTO uploadDTO : boardDTO.getUploads()) {

            AttachFile attachFile = modelMapper.map(uploadDTO, AttachFile.class);
            attachFile.setBno(boardDTO.getBno());

            fileMapper.insertBoard(attachFile);
        }

    }

    @Override
    public void remove(Integer bno) {
        boardMapper.delete(bno);
    }

    @Override
    public List<UploadResultDTO> getFiles(Integer bno) {

        List<AttachFile> attachFiles = boardMapper.selectFiles(bno);

        return attachFiles.stream()
                .map(attachFile -> modelMapper.map(attachFile, UploadResultDTO.class))
                .collect(Collectors.toList());
    }
}






