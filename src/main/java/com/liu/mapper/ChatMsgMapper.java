package com.liu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.entity.ChatMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMsgMapper extends BaseMapper<ChatMsg> {

    void updateIdsIfRead(@Param("ids") List<String> ids);
}
