package com.yoon.twicePractice.service;

import com.yoon.twicePractice.dto.GuestbookDTO;
import com.yoon.twicePractice.dto.PageRequestDTO;
import com.yoon.twicePractice.dto.PageResultDTO;
import com.yoon.twicePractice.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTest {

    @Autowired
    private GuestbookService service;
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
    }
}
