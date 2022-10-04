package com.liu.service;

import com.liu.BO.ChooseBO;
import com.liu.BO.RequestFriendBO;
import com.liu.VO.RequestVO;

import java.util.List;

public interface FriendsRequestService {

    /**
     * 发送成为好友请求
     * @param requestFriendBO 请求人的id和被请求人的用户名
     */
    void sendFriendRequest(RequestFriendBO requestFriendBO);

    /**
     * 根据被接受人的id获取其加好友列表
     * @param acceptUserId 被接受人的id
     * @return 邀请人的数据集合
     */
    List<RequestVO> selectRequestByAcceptId(String acceptUserId);


    /**
     * 通过操作符判断是否成为好友
     * @param chooseBO 接收方和发送方以及操作形式
     * @return 同意为1 忽略为2 错误为0
     */
     Integer ifAgreeBecomeFriend(ChooseBO chooseBO);
}
