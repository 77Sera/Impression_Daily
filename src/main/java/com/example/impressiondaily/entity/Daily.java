package com.example.impressiondaily.entity;


import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
public class Daily {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String content;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String htmlcontent;

    private String userid;

    private String time; // yyyy-mm-dd

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content; }

    public String getHtmlcontent(){ return htmlcontent; }
    public void setHtmlcontent(String htmlcontent){ this.htmlcontent = htmlcontent; }

    public String getUserid(){ return userid; }
    public void setUserid(String userid){ this.userid = userid; }

    public String getTime(){ return time; }
    public void setTime(String time){ this.time = time; }
}
