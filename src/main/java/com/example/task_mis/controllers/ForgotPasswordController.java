package com.example.task_mis.controllers;

import com.example.task_mis.dto.Utility;
import com.example.task_mis.entities.User;
import com.example.task_mis.services.interfaces.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping({"api"})
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;


    @PostMapping("/forgot_password")
    public void processForgotPassword(HttpServletRequest request) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);

        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException(ex.toString());

        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("info@mehreennajm.me", "Customer Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p style='color:gray;font-size:20px;'>Hello Dear User,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\" >Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);


        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public User showResetPasswordForm(@Param(value = "token") String token) {
        User user = userService.getByResetPasswordToken(token);
        if (user == null) {
           throw new IllegalStateException("Invalid Token");
        }
        return user ;
    }

    @PostMapping("/reset_password")
    public void processResetPassword(HttpServletRequest request) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);

        if (user == null) {
           throw new IllegalStateException("User not found");
        } else {
            userService.updatePassword(user, password);



        }

    }


}