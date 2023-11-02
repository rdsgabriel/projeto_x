package com.ophis.projectx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ophis.projectx.entities.Post;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NotNull
    @Size(min = 1, max = 500)
    private String body;

    private UserDTO user;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @URL
    private String imgPost;

    public PostDTO(Post entity){
        id = entity.getId();
        body = entity.getBody();
        createdAt = entity.getCreatedAt();
        imgPost = entity.getImgPost();
    }
}
