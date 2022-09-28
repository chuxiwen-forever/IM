package com.liu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("my_friends")
public class MyFriends {

    @TableField("id")
    private String id;

    @TableField("my_user_id")
    private String myUserId;

    @TableField("my_friend_user_id")
    private String myFriendUserId;
}
