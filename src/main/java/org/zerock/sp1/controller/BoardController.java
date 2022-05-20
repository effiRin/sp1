package org.zerock.sp1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.sp1.dto.*;
import org.zerock.sp1.service.BoardService;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/board/")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping({"/read/{bno}"})
    public String read(@PathVariable("bno") Integer bno, ListDTO listDTO, Model model){

        log.info("=============================");

        log.info(bno);

        log.info(listDTO);

        model.addAttribute("dto", service.getOne(bno));

        return "/board/read2";

    }

    @GetMapping({"/modify/{bno}"})
    public String modifyGET(@PathVariable("bno") Integer bno, ListDTO listDTO, Model model){

        log.info("=============================");

        log.info(bno);

        log.info(listDTO);

        model.addAttribute("dto", service.getOne(bno));

        return "/board/modify";

    }

    @GetMapping("/")
    public String basic(){
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void list(ListDTO listDTO, Model model){

        log.info("board list........");

        log.info(this);

        log.info(listDTO);

        ListResponseDTO<BoardDTO> responseDTO  = service.getList(listDTO);

        model.addAttribute("dtoList", responseDTO.getDtoList());

        int total = responseDTO.getTotal();

        model.addAttribute("pageMaker", new PageMaker(listDTO.getPage(), total));

    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPOST(BoardDTO boardDTO, RedirectAttributes rttr){

        log.info("---------------------");
        log.info(boardDTO);
        //log.info(Arrays.toString(uploads));

        service.register(boardDTO);

        rttr.addFlashAttribute("result",123);

        return "redirect:/board/list";
    }

    @GetMapping({"/remove/{bno}"})
    public String getNotSupported(){
        return "redirect:/board/list";
    }

    @PostMapping("/remove/{bno}")
    public String removePost(@PathVariable("bno") Integer bno, RedirectAttributes rttr){

        log.info("----------------------");
        log.info("----------------------");
        log.info("remove" + bno);

        service.remove(bno);

        log.info("----------------------");

        rttr.addFlashAttribute("result", "removed");

        return "redirect:/board/list";

    }

    @PostMapping("/modify/{bno}")
    public String removePost(@PathVariable("bno") Integer bno, BoardDTO boardDTO, ListDTO listDTO, RedirectAttributes rttr ){

        log.info("----------------------");
        log.info("----------------------");
        boardDTO.setBno(bno);
        log.info("modify" + boardDTO);

        service.update(boardDTO);

        rttr.addFlashAttribute("result", "modified");

        log.info("----------------------");

        return "redirect:/board/read/"+bno+ listDTO.getLink();

    }

    @GetMapping("/files/{bno}")
    @ResponseBody
    public List<UploadResultDTO> getFiles(@PathVariable("bno") Integer bno){

        return service.getFiles(bno);
    }

}
