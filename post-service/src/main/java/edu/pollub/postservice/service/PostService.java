package edu.pollub.postservice.service;

import edu.pollub.postservice.dto.AccountResponse;
import edu.pollub.postservice.dto.PostRequest;
import edu.pollub.postservice.model.Post;
import edu.pollub.postservice.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private WebClient.Builder webClientBuilder;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(PostRequest postRequest) {
        AccountResponse accountResponse = webClientBuilder.build().get()
                .uri("http://account-service/api/users/"+postRequest.getCreatorId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new WebClientResponseException(HttpStatus.NOT_FOUND, "Creator not found", null, null, null, null))
                )
                .bodyToMono(AccountResponse.class)
                .block();
        Post post = Post.builder()
                .content(postRequest.getContent())
                .creatorId(postRequest.getCreatorId())
                .createdAt(new Date())
                .build();
        postRepository.save(post);
        return post;
    }
}
