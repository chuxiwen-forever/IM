package com.liu.netty.enums;

public enum MsgActionMsg {

    CONNECT(1,"第一次初始化连接"),
    CHAT(2,"聊天信息"),
    SIGNED(3,"消息签收"),
    KEEPALIVE(4,"客户端保持心跳"),
    PULL_FRIEND(5,"重新拉取好友");

    public final Integer type;
    public final String content;

    MsgActionMsg(Integer type , String content){
        this.type = type;
        this.content = content;
    }
}
