package com.project.gdscSoci.services;

//import com.project.gdscSoci.entities.Like;
import com.project.gdscSoci.entities.Post;
import com.project.gdscSoci.entities.User;
import com.project.gdscSoci.repos.PostRepository;
//import com.project.gdscSoci.responses.LikeResponse;
import com.project.gdscSoci.responses.PostResponse;
import org.junit.Assert;
//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

//import java.time.LocalDateTime;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;


class PostServiceTest {

    private PostService postService;

    private PostRepository postRepository;
    private UserService userService;
    private LikeService likeService;


    //bu test sınıfı çalıştığında, her test senaryosundan önce  bu beforeEach  metodu çalıştırılır.
    // sonra test senayosu çalışır, sonrasında AfterEach metotsunuz varsa o çalışır
    @BeforeEach
    void setUp() {
        postRepository = Mockito.mock(PostRepository.class);
        userService = Mockito.mock(UserService.class);
        likeService = Mockito.mock(LikeService.class);

        postService = new PostService(postRepository, userService);
        postService.setLikeService(likeService);
    }

    @Test
    void testGetAllPosts_WithUserId() {
        // Arrange
        Long userId = 1L;
        Post post1 = new Post(1L, new User(userId, "user1", "pass1"), "Title1", "Text1");
        Post post2 = new Post(2L, new User(userId, "user1", "pass1"), "Title2", "Text2");
        List<Post> posts = Arrays.asList(post1, post2); // PostRepository.findByUserId(userId) metodunun döndüreceği post listesi
        Mockito.when(postRepository.findByUserId(userId)).thenReturn(posts); // PostRepository.findByUserId(userId) metodunun davranışını belirliyoruz

        // Act
        List<PostResponse> result = postService.getAllPosts(Optional.of(userId)); // PostService.getAllPosts metodunu çağırıyoruz

        // Assert
        Assert.assertEquals(2, result.size()); // Sonucun 2 elemanlı olup olmadığını kontrol ediyoruz
        Assert.assertEquals("Title1", result.get(0).getTitle()); // Sonucun ilk elemanının başlığını kontrol ediyoruz
        Assert.assertEquals("Title2", result.get(1).getTitle()); // Sonucun ikinci elemanının başlığını kontrol ediyoruz
        Mockito.verify(likeService, Mockito.times(2)).getAllLikesWithParam(Mockito.any(), Mockito.any()); // LikeService.getAllLikesWithParam metodunun 2 kez çağrıldığını kontrol ediyoruz

    }
}