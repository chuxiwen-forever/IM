package com.liu.netty.enums;

public enum MsgSignFlag {
    UNSIGNED(0,"未签收"),
    SIGNED(1,"已签收");

    public final Integer type;
    public final String content;

    MsgSignFlag(Integer type,String content){
        this.type = type;
        this.content = content;
    }
}
