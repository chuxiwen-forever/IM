package com.liu.controllor;

import com.liu.entity.ChatMsg;
import com.liu.service.ChatMsgService;
import com.liu.utils.R.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatMsgController {

    @Autowired
    private ChatMsgService chatMsgService;

    @GetMapping("/getUnReadMsg/{acceptId}")
    public R getUnReadMsg(@PathVariable("acceptId") String acceptId){
        List<ChatMsg> unReadMsgList = chatMsgService.getUnReadMsgList(acceptId);
        return R.success(unReadMsgList).message("获取成功");
    }
}
