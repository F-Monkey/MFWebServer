package cn.monkey.commons.bean;

import cn.monkey.commons.bean.EntityMapper;

public interface NoOpEntityMapper<T> extends EntityMapper<T, T, T> {
    @Override
    default T copyFromDto(T t) {
        return t;
    }

    @Override
    default T copyToVo(T t) {
        return t;
    }
}
