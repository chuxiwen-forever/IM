package com.liu.service;

import com.liu.BO.NicknameBO;
import com.liu.entity.Users;
import com.liu.BO.LoginBO;

public interface UsersService {
    /**
     * 通过用户名判断用户是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean selectUserIsExistByUsername(String username);

    /**
     * 痛过用户名和密码判断用户是否存在
     * @param loginVO 前端用户信息
     * @return 返回完整对象
     */
    Users selectUser(LoginBO loginVO);

    /**
     * 将用户信息补偿后存入数据库中
     * @param loginVO 前端已经有的用户信息
     * @return 将补充字段的数据补充完整后返回到前端
     */
    Users insertUser(LoginBO loginVO);

    /**
     * 根据前端修改用户昵称
     * @param nicknameBO 前端传输的数据
     * @return 更改过的数据
     */
    Users updateNickname(NicknameBO nicknameBO);
}
