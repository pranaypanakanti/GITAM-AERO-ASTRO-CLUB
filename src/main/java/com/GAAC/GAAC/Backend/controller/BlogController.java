package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.dto.request.BlogDetailsDTO;
import com.GAAC.GAAC.Backend.model.dto.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.ProfileResponseDTO;
import com.GAAC.GAAC.Backend.model.Blog;
import com.GAAC.GAAC.Backend.service.BlogService;
import com.GAAC.GAAC.Backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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


    @Operation(
            summary = "Get user blogs",
            description = "Returns the blogs written by the user"
    )
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

    @Operation(
            summary = "Post blog",
            description = "Create a new blog"
    )
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


    @Operation(
            summary = "Delete blog",
            description = "Deletes blog by id"
    )
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

    @Operation(
            summary = "Update blog",
            description = "Update blog by id"
    )
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
