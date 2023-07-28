package com.flameking.lottery.common.util;

/**
 * 回调方法接口
 *
 * @author Wangsw
 * @date 2022/11/22
 */
@FunctionalInterface
public interface EntityUtilsCallBack<S, T> {

    /**
     * 回调方法
     *
     * @param t t
     * @param s 年代
     */
    void callBack(S t, T s);
}
