package ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.repositories;

import reactor.core.publisher.Mono;

public interface RelationalRepository<E, I> {
    Mono<Integer> saveWithRelations(E e);
    Mono<E> findByIdWithRelations(I id);
}
