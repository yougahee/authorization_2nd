package com.gaga.auth_server.repository;

import com.gaga.auth_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<User, Long> {
}
