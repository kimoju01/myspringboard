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
    @GetMapping("/deleteform")
    public String deleteForm(@RequestParam(name="id") Long id, Model model) {
        // @RequestParam으로 /deleteform?id= 에서 id 값 받아옴
        model.addAttribute("id", id);
        // 받아온 id 값 deleteform으로 넘기고 th:value=${"id"}로 값 저장 -> 왜? 삭제 처리할 때 쓸 수 있도록
        return "board/deleteForm";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name="id") Long id,
                         @RequestParam(name="password") String password,
                         RedirectAttributes redirectAttributes) {
        // @RequestParam으로 deleteform에서 hidden으로 name="id" 넘긴거 받아와서 id 변수에 주입,
        // password도 name="password"로 받아와서 변수에 주입

        if (boardService.verifyPassword(id, password)) {
            // password 확인 결과 true여야 삭제
            boardService.deleteBoard(id);
            redirectAttributes.addFlashAttribute("message", "게시글이 정상적으로 삭제되었습니다.");
            return "redirect:/list";
        } else {    // password 불일치 시 삭제 폼으로 redirect
            redirectAttributes.addFlashAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "redirect:/deleteform?id=" + id;
        }
    }


}
