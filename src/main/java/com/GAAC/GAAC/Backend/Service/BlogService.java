package com.GAAC.GAAC.Backend.Service;


import com.GAAC.GAAC.Backend.DTO.request.BlogDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.Mapper.BlogMapper;
import com.GAAC.GAAC.Backend.Model.Blog;
import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BlogService {

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UserService userService;


    public void saveBlog(BlogDetailsDTO blog, String email) {
        User user = userService.getUserByEmail(email);
        Blog newBlog = new Blog();
        newBlog.setAuthor(user);
        newBlog.setTitle(blog.getTitle());
        newBlog.setContent(blog.getContent());
        blogRepo.save(newBlog);
        user.getBlogsList().add(newBlog);
    }

    public void saveBlog(Blog blog) {
        blogRepo.save(blog);
    }

    public List<BlogResponseDTO> getAllBlogs() {
        return blogRepo.findAll()
                .stream()
                .map(BlogMapper::toBlogResponse)
                .toList();
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
