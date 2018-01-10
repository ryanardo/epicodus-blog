package models;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Guest on 1/8/18.
 */
public class PostTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        Post.clearAllPosts();
    }

    @Test
    public void NewPostObjectGetsCorrectlyCreated_true() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals(true, post instanceof Post);
    }

    @Test
    public void PostInstantiatesWithContent_true() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals("Day 1: Intro", post.getContent());
    }

    @Test
    public void AllPostsAreCorrectlyReturned_true() {
        Post.clearAllPosts();
        Post post = new Post("Day 1: Intro");
        Post otherPost = new Post("How to pair successfully");
        assertEquals(2, Post.getAll().size());
    }

    @Test
    public void AllPostsContainsAllPosts_true() {
        Post post = new Post("Day 1: Intro");
        Post otherPost = new Post("How to pair successfully");
        assertTrue(Post.getAll().contains(post));
        assertTrue(Post.getAll().contains(otherPost));
    }

    @Test
    public void getPublished_isFalseAfterInstantiation_false() throws Exception {
        Post myPost = new Post("Day 1: Intro");
        assertEquals(false, myPost.getPublished()); //should never start as published
    }

    @Test
    public void getCreatedAt_instantiatesWithCurrentTime_today() throws Exception {
        Post myPost = setupNewPost();
        assertEquals(LocalDateTime.now().getDayOfWeek(), myPost.getCreatedAt().getDayOfWeek());
    }

    public Post setupNewPost() {
        return new Post("Day 1: Intro");
    }

    @Test
    public void getId_postsInstantiateWithAnID_1() throws Exception {
        Post myPost = new Post("Day 1: Intro");
        assertEquals(1, myPost.getId());
    }

    @Test
    public void findReturnsCorrectPost() throws Exception {
        Post post = setupNewPost();
        assertEquals(1, Post.findById(post.getId()).getId());
    }

    @Test
    public void findReturnsCorrectPostWhenMoreThanOnePostExists() throws Exception {
        Post post = setupNewPost();
        Post otherPost = new Post("How to pair successfully");
        assertEquals(2, Post.findById(otherPost.getId()).getId());
    }

    @Test
    public void updateChangesPostContent() throws Exception {
        Post post = setupNewPost();
        String formerContent = post.getContent();
        LocalDateTime formerDate = post.getCreatedAt();
        int formerId = post.getId();

        post.update("Android: Day 40");

        assertEquals(formerId, post.getId());
        assertEquals(formerDate, post.getCreatedAt());
        assertNotEquals(formerContent, post.getContent());
    }
    @Test
    public void deleteDeletesASpecificPost() throws Exception {
        Post post = setupNewPost();
        Post otherPost = new Post("How to pair successfully");
        post.deletePost();
        assertEquals(1, Post.getAll().size()); //one is left
        assertEquals(Post.getAll().get(0).getId(), 2); //the one that was deleted has the id of 2. Why do we care?
    }
    @Test
    public void deleteAllPostsDeletesAllPosts() throws Exception {
        Post post = setupNewPost();
        Post otherPost = setupNewPost();

        Post.clearAllPosts();
        assertEquals(0, Post.getAll().size());
    }

}