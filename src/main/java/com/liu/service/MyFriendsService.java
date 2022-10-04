package com.liu.service;

import com.liu.BO.SearchBO;
import com.liu.netty.enums.SearchFriendsStatus;

import java.util.Map;

public interface MyFriendsService {

    /**
     * 通过前端页面传入的数据获得想要查询的用户名id
     * @param searchBO 前端传入参数，个人id和好友名称
     * @return Map 中 为 枚举类型 {@link SearchFriendsStatus}和 用户类型{@link com.liu.entity.Users}
     */
    Map searchUserId(SearchBO searchBO);

}
