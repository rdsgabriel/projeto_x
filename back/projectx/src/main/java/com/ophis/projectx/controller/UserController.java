package com.ophis.projectx.controller;

import com.ophis.projectx.controller.exceptions.DatabaseException;
import com.ophis.projectx.dto.UserDTO;
import com.ophis.projectx.dto.UserInsertDTO;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.mapper.UserMapper;
import com.ophis.projectx.repository.UserRepository;
import com.ophis.projectx.controller.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj  = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        String encodedPassword = this.passwordEncoder.encode(dto.getPassword());
        User save = repository.save(mapper.toEntityInsert(dto));
        save.setPassword(encodedPassword);
        return mapper.toDTO(save);
    }

    @Transactional
    public void delete(Long id) {
       try {
           repository.deleteById(id);
       }catch (EmptyResultDataAccessException e){
           throw new ResourceNotFoundException("Id not found");
       }catch (DataIntegrityViolationException e) {
           throw new DatabaseException("Integrity violation");
       }
    }
}
