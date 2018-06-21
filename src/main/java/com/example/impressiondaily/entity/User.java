package com.example.impressiondaily.entity;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username; //用户名

    private String password; //密码

    private String nickname; //用户昵称

    private String sex; //用户性别

    private String dailyname; //日记名称

    private String avatarpath; //用户头像的图片路径

    private String isadmin; //是否具有管理员权限 1具有，0不具有

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="text")
    private String signature;

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    public String getNickname(){ return nickname; }
    public void setNickname(String nickname){ this.nickname = nickname; }

    public String getSex(){ return sex; }
    public void setSex(String sex){ this.sex = sex; }

    public String getDailyname(){ return dailyname; }
    public void setDailyname(String dailyname){ this.dailyname = dailyname; }

    public String getAvatarpath(){ return avatarpath; }
    public void setAvatarpath(String avatarpath){ this.avatarpath = avatarpath; }

    public String getSignature(){ return signature; }
    public void setSignature(String signature){ this.signature = signature; }

    public String getIsadmin(){ return isadmin; }
    public void setIsadmin(String isadmin){ this.isadmin = isadmin; }
}
