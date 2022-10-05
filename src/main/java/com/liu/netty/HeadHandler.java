package com.liu.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeadHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 用于触发用户事件 读空闲，写空闲，读写空闲
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            // 处理读写空闲，说明用户长时间没有操作
            if(event.state().equals(IdleState.ALL_IDLE)){
                Channel channel = ctx.channel();
                channel.close();
            }

        }
    }
}
