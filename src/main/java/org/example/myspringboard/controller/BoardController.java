package org.example.myspringboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.myspringboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 글 목록 (페이징X)
    @GetMapping("/list")
    public String boards(Model model) {
        model.addAttribute("boards", boardService.findAllBoards());
        return "board/list";
    }

    // 글 목록 (페이징O)


    // 글 상세 조회
    @GetMapping("/view")
    public String detailBoard(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "board/view";
    }

    // 글 등록


    // 글 수정


    // 글 삭제


}
