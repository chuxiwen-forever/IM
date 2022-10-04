package com.liu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("friends_request")
public class FriendsRequest {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("send_user_id")
    private String sendUserId;

    @TableField("accept_user_id")
    private String acceptUserId;

    @TableField(value = "request_date_time",fill = FieldFill.INSERT)
    private Date requestDateTime;
}
