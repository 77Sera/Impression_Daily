package com.example.impressiondaily.controller;

import com.example.impressiondaily.entity.Daily;
import com.example.impressiondaily.entity.User;
import com.example.impressiondaily.jpa.DailyJPA;
import com.example.impressiondaily.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class ManageController {

    @Autowired
    private UserJPA userJPA;

    @Autowired
    private DailyJPA dailyJPA;

    @RequestMapping(value="/manage")
    public String manage(HttpServletRequest request,Model model){
        User admin = (User)request.getSession().getAttribute("session_admin");
        if(admin == null){
            return "redirect:index";
        }
        List<Daily> dailys = dailyJPA.findAll();
        List<User> users = userJPA.findAll();
        model.addAttribute("user",admin);
        model.addAttribute("dailys",dailys);
        model.addAttribute("users",users);
        return "admin/manage";
    }

    @RequestMapping(value="/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam(value="myIds[]") Integer[] ids ){
        for(int i = 0; i < ids.length; i++ ){
            System.out.println("1111");
            userJPA.delete(new Long((long)ids[i]));
        }
        return "manage?op=0";
    }
}
