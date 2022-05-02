package com.yoon.twicePractice.controller;

import com.yoon.twicePractice.dto.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
@Slf4j
public class SampleController {

    @GetMapping({"/ex1","/dateFormat"})
    public void ex1(Model model){
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream()
                        .mapToObj(i->{
                            SampleDTO dto = SampleDTO.builder()
                                    .sno(i)
                                    .first("first "+i)
                                    .last("last "+i)
                                    .regTime(LocalDateTime.now())
                                    .build();
                            return dto;
                        }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }

    @GetMapping("/exInline")
    public String exInline(RedirectAttributes redirectAttributes){
        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("first "+100)
                .last("last "+100)
                .regTime(LocalDateTime.now())
                .build();

        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex2";
    }

    @GetMapping({"/ex2","/exSidebar"} )
    public void ex2(){
        log.info("ex2");


    }

}
