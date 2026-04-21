package com.yvan.projetclubescalade.controller;

import com.yvan.projetclubescalade.service.CategoryService;
import com.yvan.projetclubescalade.service.ExcursionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ExcursionService excursionService;

    @GetMapping("")
    public ModelAndView list(){
        var mav = new ModelAndView("categories-list");
        mav.addObject("categories", categoryService.findAll());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, Pageable pageable){
        var category = categoryService.findById(id);
        if (category.isEmpty()) {
            return new ModelAndView("redirect:/categories");
        }
        var excussionPage = excursionService.findByCategoryId(id, pageable);
        var mav = new ModelAndView("category-detail");
        mav.addObject("category", category.get());
        mav.addObject("excursionsPage", excussionPage);
        return mav;
    }

}
