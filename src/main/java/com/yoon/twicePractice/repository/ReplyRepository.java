package com.yoon.twicePractice.repository;

import com.yoon.twicePractice.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying //update, delete 2개의 트랜잭션 실행을 위해 사용
    @Query("delete from Reply r where r.board.bno= :bno")
    void deleteByBno(Long bno);
}
