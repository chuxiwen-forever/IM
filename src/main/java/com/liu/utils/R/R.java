package com.liu.utils.R;

import lombok.Data;

@Data
public class R<T> {

    private Integer code;

    private String message;

    private T data;

    public R() {
    }

    /**
     * 构造返回数据的方法
     */
    private static <T> R<T> build(T body, Integer code, String message) {
        R<T> result = new R<>();
        if (body != null) {
            result.setData(body);
        }
        result.setCode(code);
        result.setMessage(message);
        return result;
    }


    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> R<T> success(T data) {
        return build(data, ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage());
    }


    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static <T> R<T> fail(T data) {
        return build(data, ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMessage());
    }

    /**
     * 更改返回消息的方法
     * @param msg
     * @return
     */
    public R<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    /**
     * 更改返回状态码的方法
     * @param code
     * @return
     */
    public R<T> code(Integer code){
        this.setCode(code);
        return this;
    }

}
