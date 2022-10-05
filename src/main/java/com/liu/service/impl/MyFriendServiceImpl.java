package com.liu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liu.BO.SearchBO;
import com.liu.VO.MyFriendVO;
import com.liu.entity.MyFriends;
import com.liu.entity.Users;
import com.liu.mapper.MyFriendsMapper;
import com.liu.mapper.UsersMapper;
import com.liu.netty.enums.SearchFriendsStatus;
import com.liu.service.MyFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyFriendServiceImpl implements MyFriendsService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String ,Object> searchUserId(SearchBO searchBO) {
        Map<String, Object> result = new HashMap<>();
        Users friend = usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, searchBO.getFriendUsername()));
        if (ObjectUtils.isEmpty(friend)) {
            // 如果系统中没有该用户，就返回 用户不存在
            result.put("msg", SearchFriendsStatus.USER_NOT_EXIST.msg);
            return result;
        } else {
            MyFriends existFriend = myFriendsMapper
                    .selectOne(new LambdaQueryWrapper<MyFriends>()
                            .eq(MyFriends::getMyUserId, searchBO.getMyUserId())
                            .eq(MyFriends::getMyFriendUserId, friend.getId()));
            if (friend.getId().equals(searchBO.getMyUserId())) {
                // 如果传入的用户名和自己的id一样 返回不能添加自己
                result.put("msg", SearchFriendsStatus.NOT_YOURSELF.msg);
                return result;
            } else if (!ObjectUtils.isEmpty(existFriend)) {
                // 如果传入的用户名和朋友名已经存与数据库，返回已经存在该好友
                result.put("msg", SearchFriendsStatus.ALREADY_FRIENDS.msg);
                return result;
            }
        }
        // 返回成功
        result.put("msg",SearchFriendsStatus.SUCCESS.msg);
        result.put("friend",friend);
        return result;
    }

    @Override
    @Transactional
    public List<MyFriendVO> getAllFriendsByUserId(String userId) {
        return myFriendsMapper.getAllFriendMsgByUserId(userId);
    }


}
