package org.example.myspringboard.repository;

import org.example.myspringboard.domain.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface BoardRepository extends CrudRepository<Board, Long> {
}
