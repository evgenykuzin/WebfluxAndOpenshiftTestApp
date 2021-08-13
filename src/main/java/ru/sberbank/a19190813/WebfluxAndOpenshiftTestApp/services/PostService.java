package ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.entities.Post;
import ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.repositories.PostRepository;

import java.util.Comparator;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Flux<Post> getPosts() {
        return postRepository.findAll()
                .sort(Comparator
                        .comparing(Post::getCreationDate));
    }

    public Flux<Post> addPostAndGetAll(Post post) {
        postRepository.save(post);
        return getPosts();
    }

}
