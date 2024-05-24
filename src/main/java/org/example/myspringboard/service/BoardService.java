package org.example.myspringboard.service;

import lombok.RequiredArgsConstructor;
import org.example.myspringboard.domain.Board;
import org.example.myspringboard.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    // 글 목록 (페이징X)
//    @Transactional(readOnly = true)
//    public Iterable<Board> findAllBoards() {
//        return boardRepository.findAll();
//    }

    // 글 목록 (페이징O) findPaginated(page, size)
    @Transactional(readOnly = true)
    public Page<Board> findAllBoards(Pageable pageable) {
        Pageable sortedByDescId = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));

        return boardRepository.findAll(sortedByDescId);
    }

    // 글 상세 조회
    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // 글 등록 & 글 수정
    @Transactional
    public Board saveBoard(Board board) {
        String encodePassword = passwordEncoder.encode(board.getPassword());
        board.setPassword(encodePassword);

        if (board.getCreatedAt() == null) { // 게시글 등록일 경우 등록일 지정도 함께
            board.setCreatedAt(LocalDateTime.now());
        }
        board.setUpdatedAt(LocalDateTime.now());    // 수정일 경우는 수정일만 새로 지정
        return boardRepository.save(board);
    }

    // 글 삭제
    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    // 글 수정 & 삭제 시 password 확인
    public boolean verifyPassword(Long id, String password) {
        Board board = boardRepository.findById(id).orElse(null);
        if (passwordEncoder.matches(password, board.getPassword()))
            return true;

        return false;
    }



}
