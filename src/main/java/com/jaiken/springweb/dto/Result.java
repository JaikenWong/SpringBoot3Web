package com.jaiken.springweb.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回
 *
 * @author JaikenWong
 * @since 2025-08-30 10:48
 **/
@Data
public class Result implements Serializable {
    private String code;
    private String msg;
    private Object data;

    public static Result succ(Object data) {
        Result m = new Result();
        m.setCode("0");
        m.setData(data);
        m.setMsg("操作成功");
        return m;
    }

    public static Result succ(String mess, Object data) {
        Result m = new Result();
        m.setCode("0");
        m.setData(data);
        m.setMsg(mess);
        return m;
    }

    public static Result fail(String mess) {
        Result m = new Result();
        m.setCode("-1");
        m.setData(null);
        m.setMsg(mess);
        return m;
    }

    public static Result fail(String mess, Object data) {
        Result m = new Result();
        m.setCode("-1");
        m.setData(data);
        m.setMsg(mess);
        return m;
    }
}
