package com.liu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liu.entity.Users;
import com.liu.mapper.UsersMapper;
import com.liu.service.UsersService;
import com.liu.utils.MD5Utils;
import com.liu.BO.LoginBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean selectUserIsExistByUsername(String username) {
        Users user = usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, username));
        return !ObjectUtils.isEmpty(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users selectUser(LoginBO loginBO) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername,loginBO.getUsername());
        queryWrapper.eq(Users::getPassword, MD5Utils.getMD5Str(loginBO.getPassword()));
        return usersMapper.selectOne(queryWrapper);
    }

    @Override
    public Users insertUser(LoginBO loginBO) {
        Users user = new Users();
        user.setUsername(loginBO.getUsername());
        user.setPassword(MD5Utils.getMD5Str(loginBO.getPassword()));
        user.setNickName(loginBO.getUsername());
        user.setQrCode("");
        user.setCid(loginBO.getCid());
        usersMapper.insert(user);
        return user;
    }


}
