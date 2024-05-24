package org.example.myspringboard.service;

import lombok.RequiredArgsConstructor;
import org.example.myspringboard.domain.Board;
import org.example.myspringboard.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 글 목록 (페이징X)
    @Transactional(readOnly = true)
    public Iterable<Board> findAllBoards() {
        return boardRepository.findAll();
    }


    // 글 목록 (페이징O) findPaginated(page, size)


    // 글 상세 조회
    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }


    // 글 등록 & 글 수정
    @Transactional
    public Board saveBoard(Board board) {
        if (board.getCreatedAt() == null) { // 게시글 등록일 경우 등록일 지정도 함께
            board.setCreatedAt(LocalDateTime.now());
        }
        board.setUpdatedAt(LocalDateTime.now());    // 수정일 경우는 수정일만 새로 지정
        return boardRepository.save(board);
    }


    // 글 삭제




}
