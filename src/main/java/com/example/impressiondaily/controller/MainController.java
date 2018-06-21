package com.example.impressiondaily.controller;

import com.example.impressiondaily.entity.Daily;
import com.example.impressiondaily.entity.User;
import com.example.impressiondaily.jpa.DailyJPA;
import com.example.impressiondaily.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping
public class MainController {

    @Autowired
    private UserJPA userJPA;

    @Autowired
    private DailyJPA dailyJPA;

    // 日记首页
    @RequestMapping(value="/index")
    public String index(@RequestParam(value="id",required=false) Long id, HttpServletRequest request, Model model){
        // 获取当前用户登录信息
        User user = (User)request.getSession().getAttribute("session_user");
        // 获取登录用户的所有日记
        List<Daily> dailys = dailyJPA.findByUserid(String.valueOf(user.getId()));

        if(id != null){
            Daily daily = dailyJPA.findOne(id);
            model.addAttribute("daily",daily);
        }
        model.addAttribute("dailys",dailys);
        model.addAttribute("user",user);
        return "index";
    }

    // 登录页
    @RequestMapping(value="/login")
    public String login(User user, Model model, HttpServletRequest request){
        String error_msg = "";

        if(user.getUsername() != null){
            //根据用户名找到对应行的user数据
            User userEntity = userJPA.findByUsername(user.getUsername());
            if(userEntity == null){
                error_msg = "Oops! 用户名不存在!";
            }else{ //如果用户名存在，则检查密码
                if(userEntity.getPassword().equals(user.getPassword())){
                    // 密码正确，则跳转到首页，并设置一个session
                    if(userEntity.getIsadmin().equals("1")){ //如果是管理员
                        request.getSession().setAttribute("session_admin",userEntity);
                        return "redirect:admin/manage";
                    }else{
                        request.getSession().setAttribute("session_user",userEntity);
                        return "redirect:index";
                    }

                }else{ //密码不正确，向模板中加入错误信息
                    error_msg = "Oops! 密码错误!";
                }
            }
        }


        //用户名不存在/密码错误，则跳回到login页面，并且输出密码错误的提示
        model.addAttribute("error_msg",error_msg);
        return "login";
    }

    // 测试页
    @GetMapping(value="/test")
    public String test(Model model){
        Daily daily = dailyJPA.findOne(new Long((long)2));
        model.addAttribute("daily",daily);
        return "test";
    }

    // 注册页
    @RequestMapping(value="/register")
    public String register(User user, Model model, HttpServletRequest request){
        String error_msg = "";

        if(user.getUsername() != null){
            User userEntity = userJPA.findByUsername(user.getUsername());
            if(userEntity == null){ //如果用户名不存在
                user.setIsadmin("0");
                user.setAvatarpath("/images/default.png");
                user.setNickname(user.getUsername());
                user.setDailyname(user.getUsername()+"的日记");
                user.setSex("男");
                userJPA.save(user); //向数据库中存储此注册用户数据；

                //设置一个session，并跳转到index页面
                request.getSession().setAttribute("session_user",user);
                return "redirect:index";
            }else{ //如果用户存在，加入错误信息
                error_msg = "Oops! 用户名已存在!";
            }
        }

        model.addAttribute("error_msg",error_msg); //向模板中加载数据
        return "register";
    }

    // 退出账户，即将当前用户的session值设为null
    @RequestMapping(value="/exit")
    public String exit(HttpServletRequest request){
        request.getSession().setAttribute("session_user",null);
        request.getSession().setAttribute("session_admin",null);
        return "redirect:login";
    }

    @RequestMapping(value="/404")
    public String error(){
        return "error";
    }

    /*// 处理登录页
    @PostMapping(value="/processLogin")
    public String processLogin(User user, Model model, HttpServletRequest request){
        String error_msg = "";

        //根据用户名找到对应行的user数据
        User userEntity = userJPA.findByUsername(user.getUsername());
        if(userEntity == null){
            error_msg = "Oops! 用户名不存在!";
        }else{ //如果用户名存在，则检查密码
            if(userEntity.getPassword().equals(user.getPassword())){
                // 密码正确，则跳转到首页，并设置一个session
                request.getSession().setAttribute("session_user",userEntity);
                return "redirect:index";
            }else{ //密码不正确，向模板中加入错误信息
                error_msg = "Oops! 密码错误!";
            }
        }
        //用户名不存在/密码错误，则跳回到login页面，并且输出密码错误的提示
        model.addAttribute("error_msg",error_msg);
        return "redirect:login";
    }*/

    /*// 处理注册页
    @PostMapping(value="/processRegister")
    public String processRegister(User user, Model model, HttpServletRequest request){
        String error_msg = "";

        User userEntity = userJPA.findByUsername(user.getUsername());
        if(userEntity == null){ //如果用户名不存在
            userJPA.save(user); //向数据库中存储此注册用户数据；

            //设置一个session，并跳转到index页面
            request.getSession().setAttribute("session_user",user);
            return "redirect:index";
        }else{ //如果用户存在，加入错误信息
            error_msg = "Oops! 用户名已存在!";
        }

        model.addAttribute("error_msg",error_msg); //向模板中加载数据
        return "register";
    }*/
}
