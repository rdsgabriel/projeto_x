package com.ophis.projectx.resource;

import com.ophis.projectx.config.security.TokenService;
import com.ophis.projectx.dto.AuthenticationDTO;
import com.ophis.projectx.dto.LoginResponseDTO;
import com.ophis.projectx.dto.UserDTO;
import com.ophis.projectx.dto.UserInsertDTO;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Registrar usuario", description = "Registre um usuario")
public class AuthResource {
    private final UserService service;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Operation(
            summary = "Login Usuario",
            description = "Login via requisição POST recebendo Login e password")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Sucesso", responseCode = "200"
            ),
            @ApiResponse(responseCode = "400" ,description = "Email ou login já registrado")
    })
    @Hidden
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(
            summary = "Registrar Usuario",
            description = "Register via requisição POST recebendo os paramentros name(Obrigatorio), login(Obrigatorio e unico)," +
                    "password(Obrigatorio), birthDay(opcional) e imgURL")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class))),
    })
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
