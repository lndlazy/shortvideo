package com.aaron.fpvideodemo.common.widget.utils;


/**
 * Http状态异常
 */
public class HttpStatusException extends Exception {
    public HttpStatusException(String detailMessage) {
        super(detailMessage);
    }
}
