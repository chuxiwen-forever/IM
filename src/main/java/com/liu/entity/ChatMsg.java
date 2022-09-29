package com.liu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @TableField("create_time")
    private Date createTime;
}
