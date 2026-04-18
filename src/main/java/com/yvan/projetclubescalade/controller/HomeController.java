package com.yvan.projetclubescalade.controller;

import com.yvan.projetclubescalade.service.CategoryService;
import com.yvan.projetclubescalade.service.ExcursionService;
import com.yvan.projetclubescalade.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;
    private final ExcursionService excursionService;
    private final MemberService memberService;

    @GetMapping("/")
    public ModelAndView home() {
        var mav = new ModelAndView("home");
        mav.addObject("categoriesCount", categoryService.findAll().size());
        mav.addObject("excursionsCount", excursionService.findAll().size());
        mav.addObject("membersCount", memberService.findAll().size());
        return mav;
    }
}