package com.mjliu.commonlib.retrofit;

public interface ErrorCode {

    int CODE_SERVER_404 = 404;
    String MSG_SERVER_404 = "服务器内部错误(404)，请稍后重试";

    int CODE_SERVER_500 = 500;
    String MSG_SERVER_500 = "服务器内部错误(500)，请稍后重试";

//    int CODE_NOT_SUCCESSFUL = 1000;
//    String MSG_NOT_SUCCESSFUL = "服务器异常，请稍后重试";

    int CODE_PARSE_ERROR = 2000;
    String MSG_PARSE_ERROR = "数据解析异常";

    int CODE_STATUS_N = 3000;

    int CODE_EXCEPTION_TIMEOUT = 4000;
    String MSG_EXCEPTION_TIMEOUT = "连接服务器超时";

    //出现这个异常，一般是由于网络不稳定导致的。
    //device will display as if it is connected to wifi, but is not really connected.
    //    int CODE_UNKNOW_HOST = 5000;
    //    String MSG_UNKNOW_HOST = "服务器域名错误";
    int CODE_UNKNOW_HOST = 5000;
    String MSG_UNKNOW_HOST = "网络异常，请稍后重试";

    int CODE_SERVER_ERROR = 7000;
    String MSG_SERVER_ERROR = "服务器状态异常";

    int CODE_SSLHANDSHAKE_EXCEPTION = 9000;
    String MSG_SSLHANDSHAKE_EXCEPTION = "证书验证失败";

    int CODE_JSON_SYNTAX_EXCEPTION = 10000;
    String MSG_JSON_SYNTAX_EXCEPTION = "JSON解析失败";

    int CODE_UNKNOW = 10000000;
    String MSG_UNKNOW = "未知错误";
}
