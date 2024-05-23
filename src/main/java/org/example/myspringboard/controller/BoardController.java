package org.example.myspringboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.myspringboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


    // 글 등록


    // 글 수정


    // 글 삭제


}
