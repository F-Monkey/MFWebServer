package cn.monkey.gateway.dsl;

import java.util.function.Function;
import java.util.function.Predicate;

public interface PredicateFactory<D, T> extends Function<D, Predicate<T>> {
}
