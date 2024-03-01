package cn.monkey.orm.reactive.mongo;

import cn.monkey.orm.reactive.ReactivePageableExecutionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public class SimpleReactiveMongoCrudRepository<T, ID extends Serializable> extends SimpleReactiveMongoRepository<T, ID> implements ReactiveMongoCrudRepository<T, ID>,
        ApplicationContextAware, InitializingBean {

    protected ApplicationContext applicationContext;

    protected final MongoEntityInformation<T, ID> entityInformation;

    protected final ReactiveMongoOperations reactiveMongoOperations;

    public SimpleReactiveMongoCrudRepository(MongoEntityInformation<T, ID> entityInformation, ReactiveMongoOperations mongoOperations) {
        super(entityInformation, mongoOperations);
        this.entityInformation = entityInformation;
        this.reactiveMongoOperations = mongoOperations;
    }

    @Override
    public Mono<Page<T>> findPage(Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        MongoEntityInformation<T, ID> entityInformation = this.getEntityInformation();
        Class<T> type = entityInformation.getJavaType();
        String collectionName = entityInformation.getCollectionName();
        ReactiveMongoOperations mongoOperations = this.getMongoOperations();
        Flux<T> flux = mongoOperations.find(query, type, collectionName);
        return flux.collectList().flatMap(list -> ReactivePageableExecutionUtils.getPage(list, pageable, mongoOperations
                .count(Query.of(query).limit(-1).skip(-1), type, collectionName)));
    }

    @Override
    public MongoEntityInformation<T, ID> getEntityInformation() {
        return this.entityInformation;
    }

    @Override
    public ReactiveMongoOperations getMongoOperations() {
        return this.reactiveMongoOperations;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}