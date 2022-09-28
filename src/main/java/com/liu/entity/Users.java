package com.liu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class Users {

    @TableField("id")
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("face_image")
    private String faceImage;

    @TableField("face_image_big")
    private String faceImageBig;

    @TableField("nick_name")
    private String nickName;

    @TableField("qrcode")
    private String qrCode;

    @TableField("cid")
    private Integer cid;
}
