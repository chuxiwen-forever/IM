package com.liu.netty;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataContent implements Serializable {
    private static final long serialVersionUID = 11244545435453L;

    // 动作类型
    private Integer action;

    // 用户聊天内容
    private NettyChatMsg chatMsg;

    // 扩展字段
    private String extend;
}
