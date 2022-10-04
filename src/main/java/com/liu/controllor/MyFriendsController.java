package com.liu.controllor;

import com.liu.BO.SearchBO;
import com.liu.VO.UserVO;
import com.liu.entity.Users;
import com.liu.netty.enums.SearchFriendsStatus;
import com.liu.service.MyFriendsService;
import com.liu.utils.R.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/friends")
public class MyFriendsController {

    @Autowired
    private MyFriendsService myFriendsService;

    @PostMapping("/search")
    public R searchUser(@RequestBody SearchBO searchBO){
        if(ObjectUtils.isEmpty(searchBO.getMyUserId()) || ObjectUtils.isEmpty(searchBO.getFriendUsername())){
            return R.fail(null).message("查找用户不能为空");
        }
        Map friendResult = myFriendsService.searchUserId(searchBO);
        String msg = (String) friendResult.get("msg");
        if(msg.equals(SearchFriendsStatus.SUCCESS.msg)){
            Users friend = (Users) friendResult.get("friend");
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(friend,userVO);
            return R.success(userVO).message("查询成功");
        }else {
            return R.fail(null).message(msg);
        }
    }
}
