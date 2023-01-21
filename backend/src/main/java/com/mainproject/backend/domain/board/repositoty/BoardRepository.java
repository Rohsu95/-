package com.mainproject.backend.domain.board.repositoty;

import com.mainproject.backend.domain.board.entity.Board;
import com.mainproject.backend.domain.board.entity.Bookmark;
import com.mainproject.backend.domain.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

//    Page<Board> findAll(Pageable pageable);
    List<Board> findAllByUser(User user);



    //제목 검색 기능
    Page<Board> findAllByTitleContaining(String keyword, Pageable pageable);

    //유저 검색 기능
//    Page<Board> findAllByUserContainting(String keyword, Pageable pageable);

    Page<Board> findAllByBoardStatus(Pageable pageable, Board.BoardStatus boardStatus);

}
