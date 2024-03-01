package cn.monkey.commons.bean;

public interface EntityMapperContext {
    <T> T get(String key, Class<T> type);

    void put(String key, Object v);
}
