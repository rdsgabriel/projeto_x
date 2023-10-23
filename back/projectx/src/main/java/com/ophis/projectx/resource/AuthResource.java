package com.ophis.projectx.resource;

import com.ophis.projectx.config.security.TokenService;
import com.ophis.projectx.dto.AuthenticationDTO;
import com.ophis.projectx.dto.LoginResponseDTO;
import com.ophis.projectx.dto.UserInsertDTO;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.service.UserService;
import com.ophis.projectx.dto.UserDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final UserService service;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Operation(responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO newDTO = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").
                buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @GetMapping("/hello-world")
    @Hidden
    public String hello() {
        return "Hello World!";
    }
}
