package com.ophis.projectx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ophis.projectx.entities.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String username;

    @Email
    @NotBlank
    private String email;

    // Gabriel acha um metodo de gerar automaticamente uma imagem de foto de perfil;
    private String imgUrl;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDay;

    public UserDTO(User entity) {
        name = entity.getName();
        email = entity.getEmail();
        username = entity.getUsername();
        imgUrl = entity.getImgUrl();
        birthDay = entity.getBirthDay();
    }
}
