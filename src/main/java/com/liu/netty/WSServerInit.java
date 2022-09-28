package com.liu.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // websocket 基于Http协议 Http编码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流进行支持
        pipeline.addLast(new ChunkedWriteHandler());
        // http的聚合器，聚合成FullHttpRequest 或 FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // webSocket 客户端的指定路由  处理握手动作
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义
        pipeline.addLast(new ChatHandler());
    }
}
