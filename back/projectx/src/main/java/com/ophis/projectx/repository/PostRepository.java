package com.ophis.projectx.repository;

import com.ophis.projectx.entities.Post;
import com.ophis.projectx.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
