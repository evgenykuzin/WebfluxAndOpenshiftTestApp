package ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.entities.Post;

@Repository
public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

}
