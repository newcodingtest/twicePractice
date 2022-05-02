package com.yoon.twicePractice.controller;

import com.yoon.twicePractice.dto.GuestbookDTO;
import com.yoon.twicePractice.dto.PageRequestDTO;
import com.yoon.twicePractice.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@RequiredArgsConstructor
@Slf4j
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){

        return "redirect:/guestbook/list";
    }
    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("result", service.getList(pageRequestDTO));

        return "/guestbook/list";
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){

        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(long gno, @ModelAttribute("requestDTO")PageRequestDTO requestDTO, Model model){

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO")PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        service.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/guestbook/read";
    }
}
