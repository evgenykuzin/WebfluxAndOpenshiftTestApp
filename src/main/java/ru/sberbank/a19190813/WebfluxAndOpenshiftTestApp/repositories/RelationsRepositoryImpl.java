package ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.repositories;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;

import static ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.repositories.orm_utils.EntityParser.*;

//@Component
public abstract class RelationsRepositoryImpl<E, I> implements RelationalRepository<E, I> {
    private final Class<E> entityClass;
    private final R2dbcEntityTemplate template;

    //@Autowired
    public RelationsRepositoryImpl(Class<E> entityClass, R2dbcEntityTemplate template) {
        this.entityClass = entityClass;
        this.template = template;
    }

    @Override
    public Mono<Integer> saveWithRelations(E e) {
        return template.getDatabaseClient()
                .sql("INSERT INTO $1 ($2) values ($3);")
                .bind("$1", parseTableName(e))
                .bind("$2", parseFields(e))
                .bind("$3", parseValues(e))
        .fetch()
        .rowsUpdated();
    }

    @Override
    public Mono<E> findByIdWithRelations(I id) {
        return template
                .select(entityClass)
                .first();
    }

}
