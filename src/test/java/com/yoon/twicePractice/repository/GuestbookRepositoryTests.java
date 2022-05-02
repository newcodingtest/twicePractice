package com.yoon.twicePractice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.yoon.twicePractice.entity.Guestbook;
import com.yoon.twicePractice.entity.Memo;
import com.yoon.twicePractice.entity.QGuestbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
/*
*  querydsl 복잡한 조건 검색 테스트
* */
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestRepository;

    @Test
    @DisplayName("단일 조건 검색 테스트")
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; //Q도메인 클래스 생성

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); // BooleanBuilder: where문에 들어가는 조건들을 넣어주는 컨테이너

        BooleanExpression expression = qGuestbook.title
                .contains(keyword);

        builder.and(expression);

        Page<Guestbook> result = guestRepository.findAll(builder,pageable); //BooleanBuilder 는 findAll에 사용 가능

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    @DisplayName("다중 조건 검색 테스트")
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; //Q도메인 클래스 생성

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); // BooleanBuilder: where문에 들어가는 조건들을 넣어주는 컨테이너

        BooleanExpression exTitle = qGuestbook.title
                .contains(keyword);

        BooleanExpression exContent = qGuestbook.content
                .contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestRepository.findAll(builder,pageable); //BooleanBuilder 는 findAll에 사용 가능

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

}
