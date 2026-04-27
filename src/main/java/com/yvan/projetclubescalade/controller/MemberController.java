package com.yvan.projetclubescalade.controller;

import com.yvan.projetclubescalade.service.ExcursionService;
import com.yvan.projetclubescalade.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final ExcursionService excursionService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("")
    public ModelAndView account(Principal principal){
        var member = memberService.findByEmail(principal.getName());
        if (member.isEmpty()) {
            return new ModelAndView("redirect:/");
        }
        var excursions = excursionService.findByOrganizerId(member.get().getId());
        var mav = new ModelAndView("account");
        mav.addObject("member", member.get());
        mav.addObject("excursionsCount", excursions.size());
        return mav;
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("confirmPassword") String confirmPassword,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {
        var member = memberService.findByEmail(principal.getName());
        if (member.isEmpty()) {
            return "redirect:/";
        }

        if (!passwordEncoder.matches(currentPassword, member.get().getPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le mot de passe actuel est incorrect.");
            return "redirect:/account";
        }

        if (newPassword.length() < 4) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le nouveau mot de passe doit contenir au moins 4 caractères.");
            return "redirect:/account";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            return "redirect:/account";
        }

        member.get().setPassword(passwordEncoder.encode(newPassword));
        memberService.save(member.get());

        redirectAttributes.addFlashAttribute("successMessage", "Mot de passe modifié avec succès.");
        return "redirect:/account";
    }
}
