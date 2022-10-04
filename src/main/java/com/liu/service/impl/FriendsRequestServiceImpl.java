package com.liu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liu.BO.ChooseBO;
import com.liu.BO.RequestFriendBO;
import com.liu.VO.RequestVO;
import com.liu.entity.FriendsRequest;
import com.liu.entity.MyFriends;
import com.liu.entity.Users;
import com.liu.mapper.FriendsRequestMapper;
import com.liu.mapper.MyFriendsMapper;
import com.liu.mapper.UsersMapper;
import com.liu.netty.enums.OperatorFriendRequestType;
import com.liu.service.FriendsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class FriendsRequestServiceImpl implements FriendsRequestService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Override
    @Transactional
    public void sendFriendRequest(RequestFriendBO requestFriendBO) {
        // 获取朋友信息
        Users friend = usersMapper.selectOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, requestFriendBO.getFriendName()));
        FriendsRequest request = friendsRequestMapper.selectOne(new LambdaQueryWrapper<FriendsRequest>()
                .eq(FriendsRequest::getSendUserId, requestFriendBO.getUserId())
                .eq(FriendsRequest::getAcceptUserId, friend.getId()));
        if(ObjectUtils.isEmpty(request)){
            // 将用户申请操作注入实体类
            FriendsRequest friendsRequest = new FriendsRequest();
            friendsRequest.setSendUserId(requestFriendBO.getUserId());
            friendsRequest.setAcceptUserId(friend.getId());
            friendsRequestMapper.insert(friendsRequest);
        }
    }

    @Override
    @Transactional
    public List<RequestVO> selectRequestByAcceptId(String acceptUserId) {
        return friendsRequestMapper.getFriendRequestList(acceptUserId);
    }

    @Override
    @Transactional
    public Integer ifAgreeBecomeFriend(ChooseBO chooseBO) {
        Integer status = chooseBO.getStatus();
        String acceptUserId = chooseBO.getAcceptUserId();
        String sendUserId = chooseBO.getSendUserId();
        // 如果进行忽略操作 , 删除数据库记录
        if(OperatorFriendRequestType.IGNORE.type.equals(status)){
            deleteRequest(chooseBO);
            return 2;
        }else if(OperatorFriendRequestType.PASS.type.equals(status)){
            // 如果进行通过操作，在好友表表中添加，然后请求表删除记录
            // 好友同意后，双方都成为彼此的好友
            insert(acceptUserId,sendUserId);
            insert(sendUserId,acceptUserId);
            deleteRequest(chooseBO);
            return 1;
        }else {
            return 0;
        }
    }

    private void deleteRequest(ChooseBO chooseBO){
        friendsRequestMapper.delete(new LambdaQueryWrapper<FriendsRequest>()
                .eq(FriendsRequest::getSendUserId,chooseBO.getSendUserId())
                .eq(FriendsRequest::getAcceptUserId,chooseBO.getAcceptUserId()));
    }

    private void insert(String people1 ,String people2){
        MyFriends friend = new MyFriends();
        friend.setMyUserId(people1);
        friend.setMyFriendUserId(people2);
        myFriendsMapper.insert(friend);
    }
}
