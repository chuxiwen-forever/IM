package com.liu.netty;

import lombok.Data;

import java.io.Serializable;

@Data
public class NettyChatMsg implements Serializable {
    private static final long serialVersionUID = 26565173201388623L;

    private String sendUserId;

    private String acceptUserId;

    private String msg;

    private String msgId;
}
