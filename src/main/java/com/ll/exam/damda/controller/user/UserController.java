package com.ll.exam.damda.controller.user;

import com.ll.exam.damda.config.user.SignupEmailDuplicatedException;
import com.ll.exam.damda.config.user.SignupNicknameDuplicatedException;
import com.ll.exam.damda.config.user.SignupUsernameDuplicatedException;
import com.ll.exam.damda.dto.user.MailDto;
import com.ll.exam.damda.dto.user.MessageDto;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.form.user.FindIdForm;
import com.ll.exam.damda.form.user.FindPwForm;
import com.ll.exam.damda.form.user.UserCreateForm;
import com.ll.exam.damda.form.user.UserEditForm;
import com.ll.exam.damda.repository.user.UserRepository;
import com.ll.exam.damda.service.user.MailService;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getPassword_check())) {
            bindingResult.rejectValue("password_check", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getNickname(),
                    userCreateForm.getEmail(), userCreateForm.getPassword());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "signup_form";
        } catch (SignupNicknameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "signup_form";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/access")
    public String user() {
        return "user_access";
    }


    @GetMapping("/my_page")
    public String mypage(Principal principal, UserEditForm userEditForm) {
        SiteUser siteUser = userService.getUser(principal.getName());

        userEditForm.setNickname(siteUser.getNickname());
        userEditForm.setEmail(siteUser.getEmail());

        return "my_page_form";
    }

    @PostMapping("/my_page")
    public String mypage(Principal principal, Model model, @Valid UserEditForm userEditForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "my_page_form";
        }
        SiteUser siteUser = userService.getUser(principal.getName());

        if (!userEditForm.getPassword().equals(userEditForm.getPassword_check())) {
            bindingResult.rejectValue("password_check", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "my_page_form";
        }

        try {
            userService.edit(siteUser, userEditForm.getNickname(), userEditForm.getEmail(), userEditForm.getPassword());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "my_page_form";
        } catch (SignupNicknameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "my_page_form";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "my_page_form";
        }

        MessageDto message = new MessageDto("정보 변경이 완료되었습니다.", "/user/my_page", RequestMethod.POST, null);
        return showMessageAndRedirect(message, model);
    }

    @GetMapping("/find_id")
    public String findid(FindIdForm findIdForm) {
        return "find_id_form";
    }

    @PostMapping("/find_id")
    public String findid(Model model, @Valid FindIdForm findIdForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "find_id_form";
        }
        String alert = "일치하는 아이디를 찾을 수 없습니다.";
        String redirectUri = "/user/find_id";
        if (userService.getUserRepository().findByEmail(findIdForm.getEmail()) != null)
        {
            String findId = userService.getUserRepository().findByEmail(findIdForm.getEmail()).getUsername();
            alert = "아이디는 " + findId + "입니다.";
            redirectUri = "/user/login";
        }
        MessageDto message = new MessageDto(alert, redirectUri, RequestMethod.POST, null);
        return showMessageAndRedirect(message, model);
    }

    @GetMapping("/find_pw")
    public String findpw(FindPwForm findPwForm) {
        return "find_pw_form.html";
    }

    @PostMapping("/find_pw")
    public String findpw(Model model, @Valid FindPwForm findPwForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "find_pw_form";
        }

        String alert = "일치하는 아이디를 찾을 수 없습니다.";
        String redirectUri = "/user/find_pw";
        if (userService.getUserRepository().findByUsernameAndEmail(findPwForm.getUsername(), findPwForm.getEmail()) != null)
        {
            SiteUser user = userService.getUserRepository().findByUsernameAndEmail(findPwForm.getUsername(), findPwForm.getEmail());
            String newPw = userService.getTempPassword();
            String findPwMsg = "임시 비밀번호는 " + newPw + " 입니다.";
            userService.edit(user,newPw);

            MailDto mailDto = new MailDto();
            mailDto.setAddress(findPwForm.getEmail());
            mailDto.setTitle("DAMDA 임시 비밀번호 발급입니다");
            mailDto.setMessage(findPwMsg);
            mailService.mailSend(mailDto);

            alert = "임시 비밀번호가 이메일로 발송되었습니다";
            redirectUri = "/user/login";
        }

        MessageDto message = new MessageDto(alert, redirectUri, RequestMethod.POST, null);
        return showMessageAndRedirect(message, model);
    }

    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "user/messageRedirect";
    }

}