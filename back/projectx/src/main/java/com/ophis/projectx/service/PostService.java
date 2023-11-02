package com.ophis.projectx.service;

import com.ophis.projectx.dto.PostDTO;
import com.ophis.projectx.dto.PostUpdateDTO;
import com.ophis.projectx.entities.Post;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.mapper.PostMapper;
import com.ophis.projectx.repository.PostRepository;
import com.ophis.projectx.repository.UserRepository;
import com.ophis.projectx.service.exceptions.DatabaseException;
import com.ophis.projectx.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper mapper;

    @Transactional
    public PostDTO createPost(PostDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedInUser = userRepository.findByEmail(currentPrincipalName);
        Post save = postRepository.save(mapper.toEntity(dto));
        save.setCreatedAt(LocalDateTime.now());
        save.setUser(loggedInUser);
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

    @Transactional
    public PostDTO updatePost(Long id, String body) throws Exception
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedInUser = userRepository.findByEmail(currentPrincipalName);
        Post tweetToUpdate = postRepository.findById(id).orElse(null);
        Post save = postRepository.save(mapper.toEntityUpdate(tweetToUpdate));
        if(tweetToUpdate == null)
        {
            throw new Exception("Tweet not found");
        }
        if(!loggedInUser.equals(tweetToUpdate.getUser()))
        {
            throw new Exception("No permission to edit this tweet!");
        }
        tweetToUpdate.setBody(body);
        tweetToUpdate.setCreatedAt(LocalDateTime.now());
        return mapper.toDTO(save);
    }
}