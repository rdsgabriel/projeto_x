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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@EnableAutoConfiguration
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Criar tweet", description = "Cria post, deleta post e modifica")

public class PostResource {

    private final PostService service;

    @PostMapping("/tweet")
    @Operation(
            summary = "Postar um tweet",
            description = "O usuario cria uma tweet salvando diretamente no banco de dados, a parte de 'user:{}'" +
                    "pode ser descartada na hora de chamar a requisição POST, 'user:{}' é gerado de acordo com as informações quem chamou requisição. "
    )
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO dto, Authentication authentication) {
        PostDTO newDTO = service.createPost((User) authentication.getPrincipal(), dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").
                buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "DELETE TWEET",
            description = "Delete o  tweet ultizando o ID, apenas ROLE_ADMIN tem permissão para deletar."
    )
    public ResponseEntity<PostDTO> deleteTweet(@PathVariable Long id){
        service.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
