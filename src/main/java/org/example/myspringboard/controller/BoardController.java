package org.example.myspringboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.myspringboard.domain.Board;
import org.example.myspringboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/writeform")
    public String writeForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/writeForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message", "게시글이 정상적으로 등록되었습니다.");
        return "redirect:/list";
    }


    // 글 수정


    // 글 삭제


}
