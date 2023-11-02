package com.ophis.projectx.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUpdateDTO extends PostDTO {

    @Size(min = 1, max = 256)
    private String body;
}
