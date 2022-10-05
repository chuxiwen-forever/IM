package com.liu.controllor;

import com.liu.BO.ChooseBO;
import com.liu.BO.RequestFriendBO;
import com.liu.VO.MyFriendVO;
import com.liu.VO.RequestVO;
import com.liu.service.FriendsRequestService;
import com.liu.service.MyFriendsService;
import com.liu.utils.R.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class FriendsRequestController {

    @Autowired
    private FriendsRequestService friendsRequestService;

    @Autowired
    private MyFriendsService myFriendsService;

    @PostMapping("/friends")
    public R becomeFriend(@RequestBody RequestFriendBO requestFriendBO){
        friendsRequestService.sendFriendRequest(requestFriendBO);
        return R.success(null).message("添加成功");
    }

    @GetMapping("/selectSendUser/{acceptId}")
    public R selectSendUser(@PathVariable("acceptId") String acceptId){
        List<RequestVO> requestVOS = friendsRequestService.selectRequestByAcceptId(acceptId);
        return R.success(requestVOS);
    }

    @PostMapping("/becomeFriend")
    public R becomeFriendOrNot(@RequestBody ChooseBO chooseBO){
        Integer status = friendsRequestService.ifAgreeBecomeFriend(chooseBO);
        List<MyFriendVO> friendList = myFriendsService.getAllFriendsByUserId(chooseBO.getAcceptUserId());
        if (status.equals(1)){
            return R.success(friendList).message("添加成功");
        }else if (status.equals(2)){
            return R.success(friendList).message("已忽略");
        }else {
            return R.fail(null).message("接口拒绝被访问");
        }
    }
}
