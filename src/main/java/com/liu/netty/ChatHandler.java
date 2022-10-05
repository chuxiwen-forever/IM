package com.liu.netty;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.liu.SpringUtil;
import com.liu.netty.enums.MsgActionMsg;
import com.liu.service.ChatMsgService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description: 处理消息的handler
 *
 * TextWebSocketFrame: 在netty中，是用于为WebSocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所以用户端的Channel
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取信息
        String content = msg.text();
        // 获取当前channel
        Channel currentChannel = ctx.channel();
        // 将消息转换成对象
        DataContent dataContent = JSON.parseObject(content, DataContent.class);
        Integer action = dataContent.getAction();
        // 判断消息类型
        if(action.equals(MsgActionMsg.CONNECT.type)){
            // 1.1. 当webSocket第一次open的时候，初始化channel，把userId和channel关联起来
            String senderId = dataContent.getChatMsg().getSendUserId();
            UserAndChannel.put(senderId,currentChannel);
        }else if(action.equals(MsgActionMsg.CHAT.type)){
            // 1.2. 聊天类型信息，把聊天记录保存到数据库，标记签收状态
            NettyChatMsg chatMsg = dataContent.getChatMsg();
            // 1.2.1 将消息保存到数据库，标记为未签收
            ChatMsgService chatMsgService = (ChatMsgService) SpringUtil.getBean("chatMsgServiceImpl");
            String msgId = chatMsgService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);
            DataContent targetContent = new DataContent();
            targetContent.setChatMsg(chatMsg);

            // 1.2.2发送消息
            // 1.2.2.1 获取签收者id
            String acceptUserId = chatMsg.getAcceptUserId();
            // 1.2.2.2 找到接收者channel
            Channel acceptUserChannel = UserAndChannel.get(acceptUserId);
            if(acceptUserChannel == null){
                // channel 为空 说明用户离线
            }else {
                // channel 不为空时，去channelGroup中查找
                Channel findChannel = users.find(acceptUserChannel.id());
                if(findChannel != null){
                    // 说明用户在线
                    acceptUserChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(targetContent)));
                }else{
                    // 用户离线 推送消息
                }
            }

        }else if(action.equals(MsgActionMsg.SIGNED.type)){
            // 1.3. 签收消息类型，针对消息进行签收，修改数据库中的对应消息的状态
            ChatMsgService chatMsgService = (ChatMsgService) SpringUtil.getBean("chatMsgServiceImpl");
            // 拓展字段在signed类型的消息中，代表签收的用户id，逗号隔开
            String extend = dataContent.getExtend();
            if(!ObjectUtils.isEmpty(extend)){
                String[] split = extend.split(",");
                List<String> msgIdList =
                        Arrays.stream(split).filter(StringUtils::isNotBlank).collect(Collectors.toList());
                if(!ObjectUtils.isEmpty(msgIdList) && !msgIdList.isEmpty() && msgIdList.size() > 0){
                    // 批量签收
                    chatMsgService.updateMsgSignedList(msgIdList);
                }
            }
        }else if (action.equals(MsgActionMsg.KEEPALIVE.type)){
            // 1.4. 心跳类型
        }

    }

    // 连接后
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 等客户端连接服务端后，获取channel，并放到Group中进行管理
        users.add(ctx.channel());
    }

    // 断开连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发送异常关闭channel
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
