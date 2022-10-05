package com.liu;

import com.liu.utils.OSSUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationShutDown implements CommandLineRunner , DisposableBean {

    @Autowired
    private OSSUtils ossUtils;

    @Override
    public void destroy() throws Exception {
        ossUtils.close();
        System.err.println("关闭相关应用....");
        System.err.println("关闭主应用...");
    }

    @Override
    public void run(String... args) throws Exception {
        System.err.println("应用启动....");
    }
}
