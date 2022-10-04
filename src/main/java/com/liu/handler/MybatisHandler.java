package com.liu.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MybatisHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"faceImage",String.class,"https://chuxiwen.oss-cn-beijing.aliyuncs.com/default.png");
        this.strictInsertFill(metaObject,"faceImageBig",String.class,"https://chuxiwen.oss-cn-beijing.aliyuncs.com/default.png");
        this.strictInsertFill(metaObject,"createTime", Date.class,new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
