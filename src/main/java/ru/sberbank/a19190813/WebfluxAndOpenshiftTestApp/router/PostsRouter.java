package ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.*;
import ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.entities.Post;
import ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.services.PostService;

import java.util.List;

import static org.springframework.core.ParameterizedTypeReference.forType;
import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.POST;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class PostsRouter {
    @Autowired
    private PostService postService;

    @Bean
    public RouterFunction<ServerResponse> getPosts() throws Exception {
        return route(GET("/posts"),
                        serverRequest ->
                                ServerResponse
                                        .ok()
                                        .body(postService
                                                .getPosts()
                                                .collectList()))
                .andRoute(POST("/posts"),
                        serverRequest ->
                                ServerResponse
                                        .ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .allow(HttpMethod.POST)
                                        .body(postService
                                                .addPostAndGetAll(serverRequest.body(Post.class))
                                                .collectList(), forType(List.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> main() throws Exception {
        return route(GET("/"),
                        serverRequest ->
                                ServerResponse.ok().body("Main page"));
    }


}
