package cn.monkey.commons.bean;

public interface ContextEntityMapper<D, T, V> extends EntityMapper<D, T, V> {
    EntityMapperContext getContext(String key);

    void remove(String key);
}
