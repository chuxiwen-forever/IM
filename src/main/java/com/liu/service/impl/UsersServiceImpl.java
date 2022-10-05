package com.liu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liu.BO.ImageBO;
import com.liu.BO.NicknameBO;
import com.liu.VO.UserVO;
import com.liu.entity.Users;
import com.liu.mapper.UsersMapper;
import com.liu.service.UsersService;
import com.liu.utils.Base64Util;
import com.liu.utils.MD5Utils;
import com.liu.BO.LoginBO;
import com.liu.utils.OSSUtils;
import com.liu.utils.QRCodeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private OSSUtils ossUtils;

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
    @Transactional
    public Users insertUser(LoginBO loginBO) {
        Users user = new Users();
        user.setId(UUID.randomUUID().toString().replace("-",""));
        user.setUsername(loginBO.getUsername());
        user.setPassword(MD5Utils.getMD5Str(loginBO.getPassword()));
        user.setNickname(loginBO.getUsername());
        String qrCodePath = "D://photo//" + user.getId().substring(0,7)+"qrcode.png";
        qrCodeUtils.createQRCode(qrCodePath,"qrCode:" + user.getUsername());
        String uploadUrl = ossUtils.upload(qrCodePath);
        // 当照片存到OSS后，删除本地的文件
        File file = new File(qrCodePath);
        file.delete();
        user.setQrcode(uploadUrl);
        user.setCid(loginBO.getCid());
        usersMapper.insert(user);
        return user;
    }

    @Override
    @Transactional
    public Users updateNickname(NicknameBO nicknameBO) {
        Users users = usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getId, nicknameBO.getId()));
        users.setNickname(nicknameBO.getNickname());
        usersMapper.updateById(users);
        return users;
    }

    @Override
    @Transactional
    public UserVO updateImg(ImageBO imgBO) {
        String path = "D://photo//" + imgBO.getUserId().substring(0,6) + "img.jpg";
        // 将前端传来的Base64编码转换成图片存到本地
        Base64Util.convertBase64ToImage(imgBO.getImgData(),path);
        // 使用Oss将文件传到阿里云并改变返回存储路径
        String imgPath = ossUtils.upload(path);
        // 照片上传到阿里云以后，就将本地的文件进行删除
        File file = new File(path);
        file.delete();
        // 查询用户并并更新
        Users targetUser = usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getId, imgBO.getUserId()));
        targetUser.setFaceImage(imgPath);
        targetUser.setFaceImageBig(imgPath);
        usersMapper.updateById(targetUser);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(targetUser,userVO);
        return userVO;
    }


}
