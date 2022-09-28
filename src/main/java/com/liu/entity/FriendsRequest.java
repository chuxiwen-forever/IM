package com.liu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("friends_request")
public class FriendsRequest {

    @TableField("id")
    private String id;

    @TableField("send_user_id")
    private String sendUserId;

    @TableField("accept_user_id")
    private String acceptUserId;

    @TableField("request_date_time")
    private Date requestDateTime;
}
