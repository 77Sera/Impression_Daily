package com.example.impressiondaily.controller;

import com.example.impressiondaily.entity.Daily;
import com.example.impressiondaily.entity.User;
import com.example.impressiondaily.jpa.DailyJPA;
import com.example.impressiondaily.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

    @Autowired
    private UserJPA userJPA;

    @Autowired
    private DailyJPA dailyJPA;

    @RequestMapping(value="/index")
    public String index(Model model, HttpServletRequest request,@RequestParam(value="aid",required=false) Long aid){
        if( aid != null ){
            Daily daily = dailyJPA.findOne(aid);
            if(daily != null){
                model.addAttribute("daily",daily);
            }
        }
        User user = (User)request.getSession().getAttribute("session_user");
        //List<Daily> dailys = dailyJPA.findAll();
        List<Daily> dailys = dailyJPA.findByUserid(user.getId().toString());
        model.addAttribute("dailys",dailys);
        model.addAttribute("user",user);
        return "admin/index";
    }

    @RequestMapping(value="/saveDaily")
    public String saveDaily(Daily daily){
        dailyJPA.save(daily);
        return "redirect:index";
    }

    @RequestMapping(value="/deleteDaily")
    @ResponseBody
    public String deleteDaily(@RequestParam(value="myIds[]") Integer[] ids ){
        for(int i = 0; i < ids.length; i++ ){
            System.out.println("1111");
            dailyJPA.delete(new Long((long)ids[i]));
        }
        return "index?op=0";
    }

    @RequestMapping(value="/editDaily")
    @ResponseBody
    public String editDaily(@RequestParam(value="myIds[]") Integer[] ids ){
        String id = String.valueOf(ids[0]);
        return "index?op=1&aid="+id;
    }

    @RequestMapping(value="/saveUserSettings")
    public String saveUserSettings(User user, HttpServletRequest request){
        User userEntity = (User)request.getSession().getAttribute("session_user");
        userEntity.setNickname(user.getNickname());
        userEntity.setSex(user.getSex());
        userEntity.setAvatarpath(user.getAvatarpath());
        userEntity.setSignature(user.getSignature());
        userEntity.setDailyname(user.getDailyname());
        userJPA.save(userEntity);
        return "redirect:index?op=2";
    }

    @PostMapping(value="/modifyPassword")
    public String modifyPassword(@RequestParam(value="new_pwd") String new_pwd,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("session_user");
        user.setPassword(new_pwd);
        userJPA.save(user);
        return "redirect:index";
    }
}
