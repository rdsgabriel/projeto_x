package com.ophis.projectx.service;

import com.ophis.projectx.dto.PostDTO;
import com.ophis.projectx.dto.UserDTO;
import com.ophis.projectx.entities.Post;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.mapper.PostMapper;
import com.ophis.projectx.repository.PostRepository;
import com.ophis.projectx.service.exceptions.DatabaseException;
import com.ophis.projectx.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper mapper;

    @Transactional
    public PostDTO createPost(User user, PostDTO dto) {
        Post save = postRepository.save(mapper.toEntity(dto));
        save.setCreatedAt(LocalDateTime.now());
        save.setUser(user);
        return mapper.toDTO(save);
    }

    @Transactional
    public void deletePostById(Long id) {
        try {
            postRepository.deleteById(id);
        } catch (
                EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        } catch (
                DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }


}
