package com.liu.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MybatisHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"faceImage",String.class,"aaaa");
        this.strictInsertFill(metaObject,"faceImageBig",String.class,"aa");
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
