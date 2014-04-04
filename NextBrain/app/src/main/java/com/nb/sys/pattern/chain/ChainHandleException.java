package com.nb.sys.pattern.chain;

/**
 * Created by wangyue.wy on 14-4-4.
 */
public class ChainHandleException extends IllegalArgumentException {


    /**
     *
     * @param error 异常内容
     */
    ChainHandleException(String error) {
        super(error);
    }
}
