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
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            ProfileResponseDTO user = userService.getUserDTOByEmail(email);
            if(user == null) throw new RuntimeException("User not found");
            List<BlogResponseDTO> blogs = user.getBlogsList();
            if(blogs != null && !blogs.isEmpty()){
                return new ResponseEntity<>(blogs,HttpStatus.OK);
            }else throw new RuntimeException("No data found.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/new-blog")
    public ResponseEntity<BlogDetailsDTO> createBlog(@Valid @RequestBody BlogDetailsDTO myBlog){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            blogService.saveBlog(myBlog,email);
            return new ResponseEntity<>(myBlog,HttpStatus.CREATED);
        }catch (RuntimeException e){
            throw  new RuntimeException("Blog already exists");
        }catch (Exception e){
            return new ResponseEntity<>(myBlog,HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete-blog/{blogId}")
    public ResponseEntity<?> deleteBlogById(@PathVariable UUID blogId){
        try {
            Blog blog = blogService.getBlogById(blogId).orElse(null);
            if(blog == null) throw new RuntimeException("Blog not found");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            blogService.deleteBlogById(blogId,email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update-blog/{blogId}")
    public ResponseEntity<?> updateBlogById(@PathVariable UUID blogId,
                                                    @Valid @RequestBody BlogDetailsDTO newBlog){
            try {
                blogService.updateBlogById(blogId,newBlog);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
    }
}
