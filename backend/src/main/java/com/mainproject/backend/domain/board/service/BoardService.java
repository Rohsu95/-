package com.mainproject.backend.domain.board.service;

import com.mainproject.backend.domain.board.entity.Board;
import com.mainproject.backend.domain.board.entity.DislikeBoard;
import com.mainproject.backend.domain.board.entity.LikeBoard;
import com.mainproject.backend.domain.board.repositoty.BoardRepository;
import com.mainproject.backend.domain.board.repositoty.DislikeBoardRepository;
import com.mainproject.backend.domain.board.repositoty.LikeBoardRepository;
import com.mainproject.backend.domain.users.entity.User;
import com.mainproject.backend.global.exception.BoardNotFoundException;
import com.mainproject.backend.global.exception.BusinessLogicException;
import com.mainproject.backend.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/*
*
*
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final static String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private final static String SUCCESS_DISLIKE_BOARD = "좋아요 처리 완료";
    private final static String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private final BoardRepository boardRepository;
    private final LikeBoardRepository likeBoardRepository;
    private final DislikeBoardRepository dislikeBoardRepository;
    //유저 서비스

    //게시글 등록
    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    //게시글 수정
    public Board updateBoard(Board board) {
        Board findBoard = findVerifiedBoard(board.getBoardSeq());
        //로그인한 유저 정보가 등록한 유저정보와 같은지 확인 후 같지 않으면 에러 메세지 호출

        Optional.ofNullable(board.getCategory())
                .ifPresent(findBoard::setCategory);
        Optional.ofNullable(board.getTitle())
                .ifPresent(findBoard::setTitle);
        Optional.ofNullable(board.getContent())
                .ifPresent(findBoard::setContent);

        Board updateBoard = boardRepository.save(findBoard);

        return updateBoard;
    }

    //특정 게시글 보기 & 조회수
    public Board findBoardAndPlusViewCount(Long boardSeq) {
        Board findBoard = findVerifiedBoard(boardSeq);
        findBoard.plusViewCount();

        return findBoard;
    }

    public Page<Board> findAllBoard(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page -1 , size, Sort.by("boardSeq").descending()));
    }

    //게시글 찾기
    public Board findVerifiedBoard(Long boardSeq) {
        Optional<Board> optionalBoard = boardRepository.findById(boardSeq);
        Board findBoard = optionalBoard.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

    //제목 검색
    public Page<Board> findAllBySearch(String keyword, int page, int size) {
        return boardRepository.findAllByTitleContaining(keyword, PageRequest.of(page - 1, size,
                Sort.by("boardSeq").descending()));
    }

    //게시글 삭제
    public void deleteBoard(Long boardSeq) {
        Board findBoard = findVerifiedBoard(boardSeq);
        boardRepository.delete(findBoard);
    }

    @Transactional
    public String updateLikeOfBoard(Long boardSeq, User user) {
        Board board = boardRepository.findById(boardSeq).orElseThrow(BoardNotFoundException::new);
        if (!hasLikeBoard(board, user))
            board.increaseLikeCount();
            return createLikeBoard(board, user);
    }

    @Transactional
    public String updateDislikeOfBoard(Long boardSeq, User user) {
        Board board = boardRepository.findById(boardSeq).orElseThrow(BoardNotFoundException::new);
        if (!hasDislikeBoard(board, user))
            board.increaseDislikeCount();
        return createDislikeBoard(board, user);
    }


    public boolean hasLikeBoard(Board board, User user) {
        return likeBoardRepository.findByBoardAndUser(board, user).isPresent();
    }

    public boolean hasDislikeBoard(Board board, User user) {
        return dislikeBoardRepository.findByBoardAndUser(board, user).isPresent();
    }

    //추천 기능
    public String createLikeBoard(Board board, User user) {
        LikeBoard likeBoard = new LikeBoard(board, user); // true 처리
        likeBoardRepository.save(likeBoard);
        return SUCCESS_LIKE_BOARD;
    }
    //추천 기능
    public String createDislikeBoard(Board board, User user) {
        DislikeBoard dislikeBoard = new DislikeBoard(board, user); // true 처리
        dislikeBoardRepository.save(dislikeBoard);
        return SUCCESS_DISLIKE_BOARD;
    }
}