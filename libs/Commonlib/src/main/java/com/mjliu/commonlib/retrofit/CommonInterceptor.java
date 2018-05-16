package com.mjliu.commonlib.retrofit;
import android.text.TextUtils;

import com.mjliu.commonlib.utils.UtilsLog;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class CommonInterceptor implements Interceptor {
    private static final String TAG_DATA = "fydata";
    private static String TAG = CommonInterceptor.class.getSimpleName();

    private static String logBody(RequestBody body) {
        try {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            return buffer.readUtf8();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void printUrl(Request request) {
        if (request.body() != null && request.body().contentType() != null && "multipart".equals(request.body().contentType().type())) {
            UtilsLog.e(TAG, "request url : " + request.url());
        } else {
            String logBody = logBody(request.body());
            String body = getDecoder(logBody);

            String params = TextUtils.isEmpty(body) ? "&apptest=1" : "?" + body + "&apptest=1";
            UtilsLog.e(TAG, "request url : " + request.url() + params);
        }
    }

    private static String getDecoder(String str){
        String body = "";
        try {
            body = URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    public static void printResult(final Response response, final String result) {
        if (UtilsLog.isTest) {
            ExecutorService pool = Executors.newCachedThreadPool();
            pool.execute(() -> {
                String s = unicodeToUTF_8(result);
                printAll(response, s);
            });
            pool.shutdown();
        }
    }

    private static synchronized void printAll(Response response, String result) {
        //        printHeaders("request headers : ", response.request().headers());
        printUrl(response.request());
        UtilsLog.e(TAG, "response code : " + response.code() + " || response msg : " + response.message());
        UtilsLog.e(TAG, "response result : " + result);
        eSub(TAG, "response result : " + result);
    }

    private static void printHeaders(String tag, Headers headers) {
        StringBuilder sb = new StringBuilder();
        int headersLength = headers.size();
        for (int i = 0; i < headersLength; i++) {
            String headerName = headers.name(i);
            String headerValue = headers.get(headerName);
            sb.append("||name:" + headerName + "||value:" + headerValue);
        }
        UtilsLog.e("foryou_test", tag + sb.toString());
    }

    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void eSub(String tag, String msg) {
        int LOG_MAXLENGTH = 2000;
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            if (strLength > end) {
                UtilsLog.e(tag + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                UtilsLog.e(tag, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static String unicodeToUTF_8(String src) {
        if (TextUtils.isEmpty(src)) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder b = chain.request().newBuilder();
        Request request = RetrofitHeader.getRequestBuilder(b).build();
        Response response = chain.proceed(request);
        if (!response.isSuccessful()) {
            printResult(response, "");
            return response;
        }

        ResponseBody body = response.body();
        if (body == null) {
            printResult(response, "");
            return response;
        }

        MediaType mediaType = body.contentType();
        String content = body.string();
        int code = response.code();
        String msg = response.message();

        String fydata = null;
        try {
            JSONObject o = new JSONObject(content);
            fydata = o.optString(TAG_DATA);
        } catch (Exception e) {
            UtilsLog.e(TAG, "response err : " + e.getMessage());
        }
        if (TextUtils.isEmpty(fydata)) {//Status 没有加密
            printResult(response, content);
            return response.newBuilder().body(ResponseBody.create(mediaType, content)).code(code).headers(response.headers()).message(msg).build();
        }

        String result = null;
        try {
//            result = AESCrypt.getInstance().decrypt(fydata);
        } catch (Exception e) {
            UtilsLog.e(TAG, "AESCrypt err : " + e.getMessage());
        }

        printResult(response, result);

        return response.newBuilder().body(ResponseBody.create(mediaType, result)).code(code).headers(response.headers()).message(msg).build();
    }
}
