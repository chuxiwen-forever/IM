package com.liu.service.impl;

import com.liu.entity.ChatMsg;
import com.liu.mapper.ChatMsgMapper;
import com.liu.netty.NettyChatMsg;
import com.liu.netty.enums.MsgSignFlag;
import com.liu.service.ChatMsgService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMsgServiceImpl implements ChatMsgService {

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Override
    public String saveMsg(NettyChatMsg nettyChatMsg) {
        ChatMsg chatMsg = new ChatMsg();
        BeanUtils.copyProperties(nettyChatMsg,chatMsg);
        chatMsg.setSignFlag(MsgSignFlag.UNSIGNED.type);
        chatMsgMapper.insert(chatMsg);
        return chatMsg.getId();
    }

    @Override
    public void updateMsgSignedList(List<String> msgIdList) {
        chatMsgMapper.updateIdsIfRead(msgIdList);
    }
}
