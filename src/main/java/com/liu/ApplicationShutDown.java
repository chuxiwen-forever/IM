package com.liu;

import com.liu.utils.OSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationShutDown implements CommandLineRunner , DisposableBean {

    @Autowired
    private OSSUtils ossUtils;

    @Override
    public void destroy() throws Exception {
        ossUtils.close();
        log.info("OSS正常关闭");
        log.info("SpringBoot应用停止");
    }

    @Override
    public void run(String... args) throws Exception {
        System.err.println("SpringBoot 应用启动");
    }
}
