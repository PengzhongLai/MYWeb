package com.mes.common; // 建议放在一个专门的 common 或 util 包下

public class Result<T> {
    private int code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // --- 静态工厂方法，用于快速创建 Result 对象 ---


    public static <T> Result<T> success() {
        return new Result<>(1, "操作成功", null);
    }


    public static <T> Result<T> success(T data) {
        return new Result<>(1, "操作成功", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(1, msg, data);
    }


    public static <T> Result<T> error() {
        return new Result<>(0, "操作失败", null);
    }


    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, null);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    // --- Getters and Setters ---

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}