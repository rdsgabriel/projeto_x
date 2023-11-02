package com.ophis.projectx.service;

import com.ophis.projectx.dto.*;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.entities.enums.AuthProvider;
import com.ophis.projectx.entities.enums.Roles;
import com.ophis.projectx.mapper.UserMapper;
import com.ophis.projectx.producers.UserProducer;
import com.ophis.projectx.repository.UserRepository;
import com.ophis.projectx.service.exceptions.DatabaseException;
import com.ophis.projectx.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserProducer userProducer;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findById(Long id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        String encodedPassword = this.passwordEncoder.encode(dto.getPassword());
        User save = repository.save(mapper.toEntityInsert(dto));
        save.setPassword(encodedPassword);
        save.setRole(Roles.USER);
        save.setProvider(AuthProvider.local);
        userProducer.publishMessageEmail(save);
        return mapper.toDTO(save);
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

}
