package com.ophis.projectx.resource;

import com.ophis.projectx.dto.PostDTO;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@EnableAutoConfiguration
public class PostResource {

    private final PostService service;

    @PostMapping("/tweet")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO dto, Authentication authentication) {
        PostDTO newDTO = service.createPost((User) authentication.getPrincipal(), dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").
                buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

}
