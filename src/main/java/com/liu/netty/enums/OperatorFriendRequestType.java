package com.liu.netty.enums;

public enum OperatorFriendRequestType {
    IGNORE(0,"忽略"),
    PASS(1,"通过");

    public final Integer type;
    public final String msg;

    OperatorFriendRequestType(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
