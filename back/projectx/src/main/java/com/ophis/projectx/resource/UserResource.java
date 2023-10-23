package com.ophis.projectx.resource;

import com.ophis.projectx.dto.UserDTO;
import com.ophis.projectx.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@EnableAutoConfiguration
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User(UserResource)",
        description = "Find a user by id, delete your user, update your information's(Username, password and email)")
public class UserResource {

    private final UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
