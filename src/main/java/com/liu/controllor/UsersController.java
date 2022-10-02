package com.liu.controllor;

import com.liu.BO.NicknameBO;
import com.liu.VO.UserVO;
import com.liu.entity.Users;
import com.liu.service.UsersService;
import com.liu.utils.R.R;
import com.liu.BO.LoginBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public R login(@RequestBody LoginBO loginBO){
        if(StringUtils.isBlank(loginBO.getUsername()) || StringUtils.isBlank(loginBO.getPassword())){
            return R.fail(null).message("用户名或者密码为空");
        }
        boolean userIsExist = usersService.selectUserIsExistByUsername(loginBO.getUsername());
        UserVO userVO = new UserVO();
        if(userIsExist){
            // 登录
            Users curUser = usersService.selectUser(loginBO);
            if(ObjectUtils.isEmpty(curUser)){
                return R.fail(null).message("用户名或者密码为空");
            }
            BeanUtils.copyProperties(curUser,userVO);
            return R.success(userVO).message("登录成功");
        }else {
            //注册
            Users insertUser = usersService.insertUser(loginBO);
            BeanUtils.copyProperties(insertUser,userVO);
            return R.success(userVO).message("注册成功");
        }
    }

    @PostMapping("/updateNickname")
    public R updateNickname(@RequestBody NicknameBO nicknameBO){
        Users users = usersService.updateNickname(nicknameBO);
        return R.success(users).message("修改成功");
    }

}
