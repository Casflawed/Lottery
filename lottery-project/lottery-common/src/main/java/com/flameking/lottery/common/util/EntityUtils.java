package com.flameking.lottery.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 实体转换工具类
 *
 * @author Wangsw
 * @date 2022/11/22
 */
public class EntityUtils extends BeanUtils {

    public static <S, T> List<T> toList(List<S> sources, Supplier<T> target) {
        return toList(sources, target, null);
    }

    /**
     * @author Wangsw
     * 使用场景：Entity、Bo、Vo层数据的复制，因为BeanUtils.copyProperties只能给目标对象的属性赋值，却不能在List集合下循环赋值，因此添加该方法
     * 如：List<AdminEntity> 赋值到 List<AdminVo> ，List<AdminVo>中的 AdminVo 属性都会被赋予到值
     * S: 数据源类 ，T: 目标类::new(eg: AdminVo::new)
     */
    public static <S, T> List<T> toList(List<S> sources, Supplier<T> target, EntityUtilsCallBack<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            BeanUtils.copyProperties(source, t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
            list.add(t);
        }
        return list;
    }
}
