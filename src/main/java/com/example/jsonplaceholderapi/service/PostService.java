package com.example.jsonplaceholderapi.service;

import com.example.jsonplaceholderapi.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Service
public class PostService {

    private List<Post> posts = new ArrayList<>();

    public PostService(RestTemplate restTemplate) {
        // Pobranie danych z zewnętrznego API, tylko raz przy uruchomieniu aplikacji
        String url = "https://jsonplaceholder.typicode.com/posts";
        Post[] postsArray = restTemplate.getForObject(url, Post[].class);
        if (postsArray != null) {
            posts.addAll(Arrays.asList(postsArray));
        }
    }

    public List<Post> getAllPosts() {
        System.out.println("----------------Wczytano wszystkie posty-------------------");
        return posts;
    }

    public Post getPostById(int id) {
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        System.out.println("\n");
        throw new RuntimeException("--------------------------Nie znaleziono posta o id: " + id + "------------------------------------------------");
        
    }

    public Post updatePost(int id, Post updatedPost) {
        for (Post post : posts) {
            if (post.getId() == id) {
                post.setTitle(updatedPost.getTitle());
                post.setBody(updatedPost.getBody());
                System.out.println("----------------------Zaktualizowano post o id: " + post.getId() + "----------------------------");
                return post;
                
            }
        }
        throw new RuntimeException("-----------------------Nie znaleziono posta o id: " + id + "---------------------------------------");
    }

    public Post addPost(Post newPost) {
        for (Post post : posts) {
            if (post.getId() == newPost.getId()) {
                throw new RuntimeException("--------------------------Post o id " + newPost.getId() + " już istnieje--------------------");
            }
        }
        posts.add(newPost);
        System.out.println("-------------------------------------Dodano post o id:" + newPost.getId() + "------------------------------------");
        return newPost;
    }
    

    public void deletePost(int id) {
        if (posts.removeIf(post -> post.getId() == id)) {
            System.out.println("-------------------------------------Usunięto post o id:" + id + "------------------------------------");
        }
        else throw new RuntimeException("--------------------------Nie udało sie usunac posta o id:" + id + "--------------------------");
    }
}
