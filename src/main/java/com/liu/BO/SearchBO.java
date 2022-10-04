package com.liu.BO;

import lombok.Data;

/**
 * 搜索好友，并添加，前端传入自己的id和朋友的好友名
 */
@Data
public class SearchBO {

    private String myUserId;

    private String friendUsername;
}
