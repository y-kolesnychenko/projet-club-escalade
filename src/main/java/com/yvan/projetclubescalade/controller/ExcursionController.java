package com.yvan.projetclubescalade.controller;

import com.yvan.projetclubescalade.model.Excursion;
import com.yvan.projetclubescalade.service.CategoryService;
import com.yvan.projetclubescalade.service.ExcursionService;
import com.yvan.projetclubescalade.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/excursions")
@RequiredArgsConstructor
public class ExcursionController {

    private final ExcursionService excursionService;
    private final CategoryService categoryService;
    private final MemberService memberService;

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, Principal principal) {
        var excursion = excursionService.findById(id);
        if (excursion.isEmpty()) {
            return new ModelAndView("redirect:/categories");
        }
        var mav = new ModelAndView("excursion-detail");
        mav.addObject("excursion", excursion.get());
        mav.addObject("isAuthenticated", principal != null);
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "keyword", required = false) String keyword,
            Pageable pageable) {

        var mav = new ModelAndView("excursion-search");
        mav.addObject("categories", categoryService.findAll());

        if (name != null || categoryId != null || startDate != null || endDate != null || keyword != null) {
            var results = excursionService.search(name, categoryId, startDate, endDate, keyword, pageable);
            mav.addObject("results", results);
            mav.addObject("searchName", name);
            mav.addObject("searchCategoryId", categoryId);
            mav.addObject("searchStartDate", startDate);
            mav.addObject("searchEndDate", endDate);
            mav.addObject("searchKeyword", keyword);
        }

        return mav;
    }

    @GetMapping("/my")
    public ModelAndView myExcursions(Principal principal) {
        var member = memberService.findByEmail(principal.getName());
        if (member.isEmpty()) {
            return new ModelAndView("redirect:/");
        }
        var mav = new ModelAndView("excursion-my");
        var excursions = excursionService.findByOrganizerId(member.get().getId());
        mav.addObject("excursions", excursions);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        var mav = new ModelAndView("excursion-form");
        mav.addObject("excursion", new Excursion());
        mav.addObject("categories", categoryService.findAll());
        mav.addObject("isNew", true);
        return mav;
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute Excursion excursion,
                                @RequestParam("categoryId") Long categoryId,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        var member = memberService.findByEmail(principal.getName());
        if (member.isEmpty()) {
            return "redirect:/";
        }
        var category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
            return "redirect:/excursions/create";
        }
        excursion.setOrganizer(member.get());
        excursion.setCategory(category.get());
        excursionService.save(excursion);
        redirectAttributes.addFlashAttribute("successMessage", "Sortie créée avec succès !");
        return "redirect:/excursions/my";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id, Principal principal) {
        var excursion = excursionService.findById(id);
        if (excursion.isEmpty()) {
            return new ModelAndView("redirect:/excursions/my");
        }
        if (!excursion.get().getOrganizer().getEmail().equals(principal.getName())) {
            return new ModelAndView("redirect:/excursions/my");
        }
        var mav = new ModelAndView("excursion-form");
        mav.addObject("excursion", excursion.get());
        mav.addObject("categories", categoryService.findAll());
        mav.addObject("isNew", false);
        return mav;
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable("id") Long id,
                              @ModelAttribute Excursion excursion,
                              @RequestParam("categoryId") Long categoryId,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        var existing = excursionService.findById(id);
        if (existing.isEmpty() || !existing.get().getOrganizer().getEmail().equals(principal.getName())) {
            return "redirect:/excursions/my";
        }
        var category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
            return "redirect:/excursions/edit/" + id;
        }
        excursion.setId(id);
        excursion.setOrganizer(existing.get().getOrganizer());
        excursion.setCategory(category.get());
        excursionService.save(excursion);
        redirectAttributes.addFlashAttribute("successMessage", "Sortie modifiée avec succès !");
        return "redirect:/excursions/my";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                          Principal principal,
                          RedirectAttributes redirectAttributes) {
        var excursion = excursionService.findById(id);
        if (excursion.isEmpty() || !excursion.get().getOrganizer().getEmail().equals(principal.getName())) {
            return "redirect:/excursions/my";
        }
        excursionService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Sortie supprimée.");
        return "redirect:/excursions/my";
    }
}