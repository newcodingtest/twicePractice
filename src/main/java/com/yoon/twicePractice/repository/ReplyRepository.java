package com.yoon.twicePractice.repository;

import com.yoon.twicePractice.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying //JPQL을 통해서 UPDATE, DELETE를 실행하기 위해선 해당 어노테이션 같이 사용해야함
    @Query("delete from Reply r where r.board.bno= :bno")
    void deleteByBno(Long bno);
}
