package com.ophis.projectx.resource;

import com.ophis.projectx.dto.UserDTO;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            description = "Delete o user ultizando o ID, apenas ROLE_ADMIN tem permissão para deletar."
    )
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
