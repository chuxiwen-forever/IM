package com.liu.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * 绑定用户id和channel
 */
public class UserAndChannel {
    private static HashMap<String , Channel> manager = new HashMap<>();

    public static void put(String senderId ,Channel channel){
        manager.put(senderId,channel);
    }

    public static Channel get(String senderId){
        return manager.get(senderId);
    }
}
