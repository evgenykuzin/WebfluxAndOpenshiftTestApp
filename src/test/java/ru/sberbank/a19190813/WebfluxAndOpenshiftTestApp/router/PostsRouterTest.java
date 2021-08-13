package ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.router;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.WebfluxAndOpenshiftTestAppApplication;
import ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.entities.Post;
import ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.services.PostService;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebfluxAndOpenshiftTestAppApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class PostsRouterTest {
    @SpyBean
    PostService postService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getPosts() throws Exception {
        Mockito.when(postService.getPosts()).thenReturn(generatePostsFlux(10));

        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].title", notNullValue()))
                .andDo(print());
    }

    @Test
    public void addPostAndUpdate() throws Exception {
        Mockito.when(postService.getPosts()).thenReturn(generatePostsFlux(10));

        mvc.perform(post("/posts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(generatePost(20))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[19].id", notNullValue()))
                .andDo(print());
    }

    @Test
    public void main() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private Flux<Post> generatePostsFlux(int count) {
        return Flux.range(1, count)
                .map(this::generatePost);
    }

    private Post generatePost(Integer id) {
        Post post = new Post();
        post.setId(id.longValue());
        post.setCreationDate(LocalDateTime.now());
        post.setTitle("Bruh" + id);
        post.setText("text ala blablablablablaba");
        post.setAuthor("Juji");
        post.setImg("http://url.com/1.img");
        post.setLikes(100);
        return post;
    }

}