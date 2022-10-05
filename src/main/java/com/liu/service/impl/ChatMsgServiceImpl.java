package com.liu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liu.entity.ChatMsg;
import com.liu.mapper.ChatMsgMapper;
import com.liu.netty.NettyChatMsg;
import com.liu.netty.enums.MsgSignFlag;
import com.liu.service.ChatMsgService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ChatMsg> getUnReadMsgList(String acceptUserId) {
        return chatMsgMapper.selectList(new LambdaQueryWrapper<ChatMsg>()
                .eq(ChatMsg::getAcceptUserId,acceptUserId)
                .eq(ChatMsg::getSignFlag,0));
    }
}
