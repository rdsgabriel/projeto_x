package com.ophis.projectx.mapper;

import com.ophis.projectx.dto.UserDTO;
import com.ophis.projectx.dto.UserInsertDTO;
import com.ophis.projectx.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User entity);

    User toEntity(UserDTO dto);

    User toEntityInsert(UserInsertDTO insertDTO);
}
