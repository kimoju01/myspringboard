package org.example.myspringboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.myspringboard.domain.Board;
import org.example.myspringboard.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
//    @GetMapping("/list")
//    public String boards(Model model) {
//        model.addAttribute("boards", boardService.findAllBoards());
//        return "board/list";
//    }

    // 글 목록 (페이징O)
    @GetMapping("/list")
    public String boards(Model model,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        // page는 0부터 시작하기 때문에 사용자가 1부터 시작하는 페이지 번호를 입력헀을 때 맞추기 위해 page - 1 사용

        Page<Board> boards = boardService.findAllBoards(pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", page);
        return "board/list";
    }


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
    public String write(@Valid @ModelAttribute Board board,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "board/writeForm";
        }
        boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message", "게시글이 정상적으로 등록되었습니다.");
        return "redirect:/list";
    }

    // 글 수정
    @GetMapping("/updateform")
    public String updateForm(@RequestParam(name="id") Long id, Model model) {
        Board board = boardService.findBoardById(id);
        model.addAttribute("board", board);
        return "board/updateForm";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute Board board,
                         BindingResult bindingResult,
                         @RequestParam(name="password") String password,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "board/updateForm";
        }

        if (boardService.verifyPassword(board.getId(), password)) {
            boardService.saveBoard(board);
            redirectAttributes.addFlashAttribute("message", "게시글이 정상적으로 수정되었습니다.");
            return "redirect:/view?id=" + board.getId();
        } else {
            // redirectAttributes는 리다이렉트 이후에만 유효해서 model에 추가
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "board/updateForm";  // 수정하려 했던 내용 유지 하면서 폼 다시 보여주기
        }
    }


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
