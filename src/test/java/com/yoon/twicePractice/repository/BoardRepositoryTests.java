package com.yoon.twicePractice.repository;

import com.yoon.twicePractice.entity.Board;
import com.yoon.twicePractice.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,10).forEach(i->{
            Member member = Member.builder().email("user"+i+"@aaa.com").build();

            Board board = Board.builder()
                        .title("Tile.."+i)
                    .content("Content.."+i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }
}
