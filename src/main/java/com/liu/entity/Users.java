package com.liu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("users")
public class Users {

    @TableId
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField(value = "face_image",fill = FieldFill.INSERT)
    private String faceImage;

    @TableField(value = "face_image_big",fill = FieldFill.INSERT)
    private String faceImageBig;

    @TableField("nick_name")
    private String nickname;

    @TableField("qrcode")
    private String qrcode;

    @TableField("cid")
    private String cid;
}
