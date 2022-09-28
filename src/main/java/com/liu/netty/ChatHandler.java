package com.liu.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;


/**
 * @Description: 处理消息的handler
 *
 * TextWebSocketFrame: 在netty中，是用于为WebSocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所以用户端的Channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端的消息
        String content = msg.text();
        System.out.println("接受到的数据" + content);

        for (Channel channel : clients) {
            channel.writeAndFlush(
                    new TextWebSocketFrame("[服务器在" + LocalDateTime.now() +"接收到消息] , 消息为 : "
                    + content));
        }

        // clients.writeAndFlush(new TextWebSocketFrame(content));  等同于 ↑ for循环
    }

    // 连接后
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 等客户端连接服务端后，获取channel，并放到Group中进行管理
        clients.add(ctx.channel());
    }

    // 断开连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        clients.remove(ctx.channel());
        System.out.println("长id:" + ctx.channel().id().asLongText());
        System.out.println("短id:" + ctx.channel().id().asShortText());
    }
}
