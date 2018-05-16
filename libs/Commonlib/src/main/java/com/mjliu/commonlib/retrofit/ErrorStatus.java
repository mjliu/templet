package com.mjliu.commonlib.retrofit;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by Kellan on 2017/8/31.
 */

public class ErrorStatus {

    public int code;
    public String msg;

    public ErrorStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorStatus getStatus(Throwable e) {
        if (e instanceof HttpException)//服务器异常
            return getStatusByCode(((HttpException) e).code());

        if (e instanceof SocketTimeoutException)//连接超时
            return new ErrorStatus(ErrorCode.CODE_EXCEPTION_TIMEOUT, ErrorCode.MSG_EXCEPTION_TIMEOUT);

        if (e instanceof UnknownHostException)//未知主机
            return new ErrorStatus(ErrorCode.CODE_UNKNOW_HOST, ErrorCode.MSG_UNKNOW_HOST);

        if (e instanceof ConnectException || e instanceof SocketException)//连接异常
            return new ErrorStatus(ErrorCode.CODE_SERVER_ERROR, ErrorCode.MSG_SERVER_ERROR);

        if (e instanceof javax.net.ssl.SSLHandshakeException)//证书异常
            return new ErrorStatus(ErrorCode.CODE_SSLHANDSHAKE_EXCEPTION, ErrorCode.MSG_SSLHANDSHAKE_EXCEPTION);

        if (e instanceof JsonSyntaxException)
            return new ErrorStatus(ErrorCode.CODE_JSON_SYNTAX_EXCEPTION, ErrorCode.MSG_JSON_SYNTAX_EXCEPTION);

        return new ErrorStatus(ErrorCode.CODE_UNKNOW, ErrorCode.MSG_UNKNOW);//未知异常
    }

    public static ErrorStatus getStatusByCode(int code) {
        return new ErrorStatus(code, "服务器内部错误(" + code + ")，请稍后重试");
    }
}
