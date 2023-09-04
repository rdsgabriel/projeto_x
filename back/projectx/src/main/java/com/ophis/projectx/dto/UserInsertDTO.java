package com.ophis.projectx.dto;

import com.ophis.projectx.controller.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@UserInsertValid
@Getter
@Setter
@NoArgsConstructor
public class UserInsertDTO extends UserDTO {

    @NotNull
    @Size(min = 8, max = 24)
    private String password;

    @NotBlank
    @Size(min = 6, max = 14)
    private String username;

}
