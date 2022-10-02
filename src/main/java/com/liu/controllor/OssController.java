package com.liu.controllor;

import com.liu.utils.OSSUtils;
import com.liu.utils.R.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OssController {

    @Autowired
    private OSSUtils ossUtils;

    @GetMapping("/users/upload")
    public R getOssPolicy(){
        Map<String, String> policy = ossUtils.getPolicy();
        return R.success(policy);
    }

}