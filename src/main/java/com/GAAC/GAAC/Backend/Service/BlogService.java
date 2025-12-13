package com.GAAC.GAAC.Backend.Service;


import com.GAAC.GAAC.Backend.Model.Blog;
import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BlogService {

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UserService userService;


    public void saveBlog(Blog blog, String email) {
        User user = userService.getUserByEmail(email);
        Blog save = blogRepo.save(blog);
        user.getBlogsList().add(save);
        userService.saveUser(user);

    }

    public void saveBlog(Blog blog) {
        blogRepo.save(blog);
    }

    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    public Optional<Blog> getBlogById(UUID id) {
        return blogRepo.findById(id);
    }

    public void deleteBlogById(UUID id, String email) {
        User user = userService.getUserByEmail(email);
        user.getBlogsList().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        blogRepo.deleteById(id);
    }
}
