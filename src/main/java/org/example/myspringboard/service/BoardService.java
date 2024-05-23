package org.example.myspringboard.service;

import lombok.RequiredArgsConstructor;
import org.example.myspringboard.domain.Board;
import org.example.myspringboard.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 글 등록
    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }


    // 글 수정


    // 글 삭제




}
