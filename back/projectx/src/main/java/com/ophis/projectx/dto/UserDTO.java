package com.ophis.projectx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ophis.projectx.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @NotBlank
    @Schema(example = "Name")
    private String name;

    @Schema(example = "newUser")
    private String login;

    @Email
    @NotBlank
    @Schema(example = "email@email.com")
    private String email;

    @URL
    @Schema(example = "https://img.png")
    private String imgUrl;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(example = "2004-07-03")
    private Date birthDay;

    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        login = entity.getLogin();
        imgUrl = entity.getImgUrl();
        birthDay = entity.getBirthDay();
    }
}
