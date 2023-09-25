package com.ophis.projectx.service;

import com.ophis.projectx.dto.PostDTO;
import com.ophis.projectx.entities.Post;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.mapper.PostMapper;
import com.ophis.projectx.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

}
