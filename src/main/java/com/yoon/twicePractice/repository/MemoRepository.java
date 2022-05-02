package com.yoon.twicePractice.repository;

import com.yoon.twicePractice.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Long> {

    /* 원하는 mno 값에 포함되는 객체 구하기 */
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long fron, Long to);

    /* 쿼리메서드와 Pageable 파라미터 결합 사용 */
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    /* 파라미터 값보다 작은 데이터 삭제 */
    void deleteMemoByMnoLessThan(Long num);
}
