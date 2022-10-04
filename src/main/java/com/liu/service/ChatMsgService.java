package com.liu.service;

import com.liu.netty.NettyChatMsg;

import java.util.List;

public interface ChatMsgService {
    /**
     * 保存消息到数据库
     * @param chatMsg 接受者，发送者，消息
     * @return
     */
    String saveMsg(NettyChatMsg chatMsg);

    /**
     * 批量更新消息的状态
     * @param msgIdList 消息id列表
     */
    void updateMsgSignedList(List<String> msgIdList);
}
