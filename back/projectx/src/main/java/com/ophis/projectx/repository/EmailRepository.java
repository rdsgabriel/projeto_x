package com.ophis.projectx.repository;

import com.ophis.projectx.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
