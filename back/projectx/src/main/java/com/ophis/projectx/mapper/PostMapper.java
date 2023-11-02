package com.ophis.projectx.mapper;

import com.ophis.projectx.dto.PostDTO;
import com.ophis.projectx.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toDTO(Post entity);

    Post toEntity(PostDTO dto);

    Post toEntityUpdate(Post updateDTO);

}
