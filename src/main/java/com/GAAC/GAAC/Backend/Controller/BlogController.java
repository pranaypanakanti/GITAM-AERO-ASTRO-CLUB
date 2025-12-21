package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.DTO.request.BlogDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.DTO.response.ProfileResponseDTO;
import com.GAAC.GAAC.Backend.Model.Blog;
import com.GAAC.GAAC.Backend.Service.BlogService;
import com.GAAC.GAAC.Backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;


    @GetMapping("/get-by-user")
    public ResponseEntity<?> getAllBlogsOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ProfileResponseDTO user = userService.getUserDTOByEmail(email);
        if(user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<BlogResponseDTO> blogs = user.getBlogsList();
        if(blogs != null && !blogs.isEmpty()){
            return new ResponseEntity<>(blogs,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new-blog")
    public ResponseEntity<BlogDetailsDTO> createBlog(@Valid @RequestBody BlogDetailsDTO myBlog){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            blogService.saveBlog(myBlog,email);
            return new ResponseEntity<>(myBlog,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(myBlog,HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete-by-id/{blogId}")
    public ResponseEntity<?> deleteBlogById(@PathVariable UUID blogId){
        Blog blog = blogService.getBlogById(blogId).orElse(null);
        if(blog == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        blogService.deleteBlogById(blogId,email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-blog/{blogId}")
    public ResponseEntity<?> updateBlogById(@PathVariable UUID blogId,
                                                    @Valid @RequestBody BlogDetailsDTO newBlog){
        Blog old = blogService.getBlogById(blogId).orElse(null);
        if(old != null){
            old.setTitle(newBlog.getTitle() != null && !newBlog.getTitle().isEmpty() ? newBlog.getTitle() : old.getTitle());
            old.setContent(newBlog.getContent() != null && !newBlog.getContent().isEmpty() ? newBlog.getContent() : old.getContent());
            blogService.saveBlog(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
