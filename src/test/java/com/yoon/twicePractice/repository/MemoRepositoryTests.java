package com.yoon.twicePractice.repository;

import com.yoon.twicePractice.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
/*
* 페이징 테스트
* */
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testPageDefault(){

        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println("총 몇 페이지? : " + result.getTotalPages());
        System.out.println("전체 갯수? : " + result.getTotalElements());
        System.out.println("현재 페이지 번호 0부터 시작? : " + result.getNumber());
        System.out.println("페이지 당 데이터 개수? : " + result.getSize());
        System.out.println("다음 페이지 존재 여부? : " + result.hasNext());
        System.out.println("시작 페이지(0) 여부? : " + result.isFirst());

        System.out.println(" ======================================= " );
        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    /*
    * 정렬 테스트
    * */
    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0,10,sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.forEach(memo -> {
            System.out.println(memo);
        });
    }

    /***
     * 쿼리메서드+Pageable 파라미터
     * 
     */
    @Test
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L,pageable);

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }
}
