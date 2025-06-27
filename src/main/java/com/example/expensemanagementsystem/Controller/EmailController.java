package com.example.expensemanagementsystem.Controller;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensemanagementsystem.Model.Notification;

import jakarta.mail.internet.MimeMessage;

@RestController
public class EmailController {
    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/public/mail/send/employee/add/expense")
    public String sendEmail(@RequestBody Notification notiData){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(notiData.getEmployeeEmailId());
            message.setSubject("Expense record submitted");
            message.setText("Your expense record is submitted");

            javaMailSender.send(message);

            SimpleMailMessage messageManager=new SimpleMailMessage();
            messageManager.setTo(notiData.getManagerEmailId());
            messageManager.setSubject("Your reportee has submitted the expense record");
            messageManager.setText("Your reportee has submitted the expense record");

            javaMailSender.send(messageManager);

            SimpleMailMessage messageAdmin=new SimpleMailMessage();
            messageAdmin.setTo(notiData.getAdminEmailId());
            messageAdmin.setSubject("Your reportee has submitted the expense record");
            messageAdmin.setText("Your reportee has submitted the expense record");

            javaMailSender.send(messageAdmin);
            return "Email success";
        }
        catch(Exception e){
            return e.getMessage();
        }
    }


    @RequestMapping("/public/mail/send/htmltemplate")
    public String sendEmailTemplate(){
        try{
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom("shaliniselvarani2902@gmail.com");
            helper.setTo("shalinizoya01@gmail.com");
            helper.setSubject("Email notification testing");
            try(var inputStream=Objects.requireNonNull(EmailController.class.getResourceAsStream("/templates/employee.html")))
            {
                helper.setText(new String(inputStream.readAllBytes(),StandardCharsets.UTF_8),true);
            }

            javaMailSender.send(message);
            return "Email success";
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
}
