package com.liu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("chat_msg")
public class ChatMsg {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("send_user_id")
    private String sendUserId;

    @TableField("accept_user_id")
    private String acceptUserId;

    @TableField("msg")
    private String msg;

    @TableField("sign_flag")
    private Integer signFlag;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
}
