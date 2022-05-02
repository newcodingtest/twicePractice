package com.yoon.twicePractice.service;

import com.yoon.twicePractice.dto.GuestbookDTO;
import com.yoon.twicePractice.dto.PageRequestDTO;
import com.yoon.twicePractice.dto.PageResultDTO;
import com.yoon.twicePractice.entity.Guestbook;

public interface GuestbookService {
    void remove(Long gno);

    void modify(GuestbookDTO dto);

    GuestbookDTO read(Long gno);

    Long register(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, Guestbook>getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDTO(Guestbook entity){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
