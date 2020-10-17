package com.anotes.service;

import com.anotes.repository.BaseRepository;
import io.vavr.control.Option;
import lombok.Getter;

@Getter
public abstract class BaseServiceImpl<T, R extends BaseRepository<T>> implements BaseService<T> {

    private final R repository;

    protected BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Option<T> findById(Long id) {
        return Option.ofOptional(repository.findById(id));
    }
}
