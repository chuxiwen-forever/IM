package com.liu.BO;

import lombok.Data;

/**
 * 选择是否同意添加用户
 */
@Data
public class ChooseBO {

    private String acceptUserId;

    private String sendUserId;

    // 0 - 忽略添加，1 - 同意添加
    private Integer status;
}
