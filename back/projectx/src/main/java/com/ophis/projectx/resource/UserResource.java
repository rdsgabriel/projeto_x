package com.ophis.projectx.resource;

import com.ophis.projectx.dto.UserDTO;
import com.ophis.projectx.resource.exceptions.StandardError;
import com.ophis.projectx.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@EnableAutoConfiguration
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User(UserResource)",
        description = "Busca um USER pelo Id, Deleta e modifique as informações(Email, Senha and Login)")
public class UserResource {

    private final UserService service;

    @GetMapping(value = "/{id}")
    @Operation(
            description = "Busque um usuario com o ID"
    )
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            description = "Delete o user ultizando o ID, apenas ROLE_ADMIN tem permissão para deletar."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(
                    responseCode = "403", description = "Access Denied",
                    content = @Content(schema = @Schema(implementation = StandardError.class))
            )
    })
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
