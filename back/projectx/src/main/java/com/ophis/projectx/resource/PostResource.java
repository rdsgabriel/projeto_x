package com.ophis.projectx.resource;

import com.ophis.projectx.dto.PostDTO;
import com.ophis.projectx.entities.Post;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@EnableAutoConfiguration
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Post", description = "Create a tweet, delete, update")
public class PostResource {

    private final PostService service;

    @PostMapping("/tweet")
    @Operation(
            summary = "Post a tweet",
            description = "Create a post in timeline, you can delete the fields USER and CREATE_AT"

    )
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO dto, Authentication authentication) {
        PostDTO newDTO = service.createPost((User) authentication.getPrincipal(), dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").
                buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable Long id){
        service.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
