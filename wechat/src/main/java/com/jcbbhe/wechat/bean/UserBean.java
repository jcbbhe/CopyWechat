package com.jcbbhe.wechat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jcbbhe on 17/1/14.
 */
@Entity
public class UserBean {

    /**
     * username : 往事的怅然
     * headphoto : ./Uploads/2C50EEF3BDAF0B4164CE179E576F2B2D.png
     * buserid : 2
     * userid : 1
     */

    private String username;
    private String headphoto;
    @Id
    private Long buserid;
    private Long userid;

    @Generated(hash = 2090885509)
    public UserBean(String username, String headphoto, Long buserid, Long userid) {
        this.username = username;
        this.headphoto = headphoto;
        this.buserid = buserid;
        this.userid = userid;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHeadphoto() {
        return this.headphoto;
    }
    public void setHeadphoto(String headphoto) {
        this.headphoto = headphoto;
    }
    public Long getBuserid() {
        return this.buserid;
    }
    public void setBuserid(Long buserid) {
        this.buserid = buserid;
    }
    public Long getUserid() {
        return this.userid;
    }
    public void setUserid(Long userid) {
        this.userid = userid;
    }

}
