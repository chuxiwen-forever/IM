package com.liu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.VO.RequestVO;
import com.liu.entity.FriendsRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendsRequestMapper extends BaseMapper<FriendsRequest> {

    List<RequestVO> getFriendRequestList(@Param("acceptId") String acceptId);
}
