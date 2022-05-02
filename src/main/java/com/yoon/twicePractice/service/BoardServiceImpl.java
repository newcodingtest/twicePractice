package com.yoon.twicePractice.service;

import com.yoon.twicePractice.dto.BoardDTO;
import com.yoon.twicePractice.dto.PageRequestDTO;
import com.yoon.twicePractice.dto.PageResultDTO;
import com.yoon.twicePractice.entity.Board;
import com.yoon.twicePractice.entity.Member;
import com.yoon.twicePractice.repository.BoardRepository;
import com.yoon.twicePractice.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {

        //댓글부터 삭제
        replyRepository.deleteByBno();
    }

    @Override
    public Long register(BoardDTO dto) {

        Board board = dtoToEntity(dto);

        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        Function<Object[],BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public BoardDTO get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }
}
