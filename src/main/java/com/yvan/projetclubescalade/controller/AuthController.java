package com.yvan.projetclubescalade.controller;

import com.yvan.projetclubescalade.model.Member;
import com.yvan.projetclubescalade.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout) {
        var mav = new ModelAndView("login");
        if (error != null) {
            mav.addObject("errorMessage", "Email ou mot de passe incorrect.");
        }
        if (logout != null) {
            mav.addObject("successMessage", "Vous avez été déconnecté.");
        }
        return mav;
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email,
                                        RedirectAttributes redirectAttributes) {
        if (email == null || email.isBlank()){
            redirectAttributes.addFlashAttribute("errorMessage", "Email obligatoire");
            return "redirect:/forgot-password";
        }

        Optional<Member> memberOpt = memberService.findByEmail(email);
        if (memberOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Aucun compte trouvé avec cet email.");
            return "redirect:/forgot-password";
        }

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        Member member = memberOpt.get();
        member.setPassword(passwordEncoder.encode(tempPassword));
        memberService.save(member);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(member.getEmail());
            message.setSubject("Club Escalade - Récupération de mot de passe");
            message.setText("Bonjour " + member.getFirstname() + ",\n\n"
                    + "Votre nouveau mot de passe temporaire est : " + tempPassword + "\n\n"
                    + "Pensez à le changer après votre connexion.\n\n"
                    + "L'équipe Club Escalade");
            message.setFrom("noreply@club-escalade.fr");
            mailSender.send(message);
            log.info("Email de récupération envoyé à {}", email);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email à {}", email, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'envoi de l'email.");
            return "redirect:/forgot-password";
        }

        redirectAttributes.addFlashAttribute("successMessage",
                "Un email avec un mot de passe temporaire a été envoyé à " + email);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public ModelAndView registerPage(){
        return new ModelAndView("register", "member", new Member());
    }

    @PostMapping("/register")
    public ModelAndView handleRegister(@ModelAttribute @Valid Member member,
                                  BindingResult result,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()){
            return new ModelAndView("register");
        }

        if (memberService.findByEmail(member.getEmail()).isPresent()) {
            result.rejectValue("email", "email.exists", "Email déjà utilisé");
            return new ModelAndView("register");
        }

        String rawPassword = member.getPassword();
        memberService.register(member);

        try {
            request.login(member.getEmail(), rawPassword);
        } catch (Exception e) {
            log.error("Erreur lors de la connexion automatique", e);
        }

        return new ModelAndView("redirect:/");
    }
}