package com.ophis.projectx.resource;

import com.ophis.projectx.dto.PostDTO;
import com.ophis.projectx.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
                    "pode ser descartada na hora de chamar a requisição POST, 'user:{}' é gerado de acordo com as informações do user que a chamou requisição. "
    )
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO dto) {
        PostDTO newDTO = service.createPost(dto);
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

    @PutMapping("/update/{id}")
    @Operation(
            summary = "Editar um tweet",
            description = "O usuario edita o seu post salvando diretamente no banco de dados, a parte de 'user:{}'" +
                    "pode ser descartada na hora de chamar a requisição PUT, 'user:{}' é gerado de acordo com as informações do user que a chamou requisição. "
    )
    public ResponseEntity<Object> updateTweet(@PathVariable Long id,
                                              @RequestBody @Valid PostDTO tweet) throws Exception
    {
        PostDTO updatedTweet = service.updatePost(id, tweet.getBody());
        return ResponseEntity.ok().body(updatedTweet);
    }

}
