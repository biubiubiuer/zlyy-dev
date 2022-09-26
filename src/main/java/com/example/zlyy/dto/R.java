package com.example.zlyy.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = false)
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Integer getCode() {

        return (Integer) this.get("code");
    }

    public R setData(Object data) {
        put("data", data);
        return this;
    }

    public <T> T getData(TypeReference<T> tTypeReference) {
        return this.getData("data", tTypeReference);
    }

    public <T> T getData(String key, TypeReference<T> tTypeReference) {
        Object data = this.get(key);
        String toJSONString = JSON.toJSONString(data);
        T t = JSON.parseObject(toJSONString, tTypeReference);
        return t;
    }
}