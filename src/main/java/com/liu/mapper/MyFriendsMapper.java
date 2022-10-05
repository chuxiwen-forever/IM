package com.liu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.VO.MyFriendVO;
import com.liu.entity.MyFriends;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyFriendsMapper extends BaseMapper<MyFriends> {

    List<MyFriendVO> getAllFriendMsgByUserId(@Param("userId") String userId);
}
