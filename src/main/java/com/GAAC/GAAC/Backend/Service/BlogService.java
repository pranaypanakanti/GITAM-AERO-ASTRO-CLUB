package com.GAAC.GAAC.Backend.Service;


import com.GAAC.GAAC.Backend.DTO.request.BlogDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.DTO.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.ENUMS.TeamEnum;
import com.GAAC.GAAC.Backend.Mapper.BlogMapper;
import com.GAAC.GAAC.Backend.Mapper.UserMapper;
import com.GAAC.GAAC.Backend.Model.Blog;
import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Repository.BlogRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    public void saveBlog(BlogDetailsDTO blog, String email) {
        User user = userService.getUserByEmail(email);
        Blog newBlog = new Blog();
        newBlog.setAuthor(user);
        newBlog.setTitle(blog.getTitle());
        newBlog.setContent(blog.getContent());
        newBlog.setTeam(blog.getTeam());
        blogRepo.save(newBlog);
        user.getBlogsList().add(newBlog);
    }

    public List<BlogResponseDTO> getAllBlogs() {
        return blogRepo.findAll()
                .stream()
                .map(BlogMapper::toBlogResponse)
                .toList();
    }

    public List<BlogResponseDTO> getTeamBlogs(TeamEnum teamName) {
        return blogRepo.findByTeam(teamName)
                .stream()
                .map(BlogMapper::toBlogResponse)
                .toList();
    }

    public Optional<Blog> getBlogById(UUID id) {
        return blogRepo.findById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    public void deleteBlogById(UUID id, String email) {
        User user = userService.getUserByEmail(email);
        user.getBlogsList().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        blogRepo.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    public void updateBlogById(UUID blogId, @Valid BlogDetailsDTO newBlog) {
        Blog old = blogRepo.findById(blogId).orElse(null);
        if(old == null) throw new RuntimeException("User not found");
        old.setTitle(newBlog.getTitle() != null && !newBlog.getTitle().isEmpty() ? newBlog.getTitle() : old.getTitle());
        old.setContent(newBlog.getContent() != null && !newBlog.getContent().isEmpty() ? newBlog.getContent() : old.getContent());
        old.setTeam(newBlog.getTeam() != null ? newBlog.getTeam() : old.getTeam());
        blogRepo.save(old);
    }
}
