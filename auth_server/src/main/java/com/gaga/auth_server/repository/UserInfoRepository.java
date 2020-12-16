package com.gaga.auth_server.repository;

import com.gaga.auth_server.dto.response.GetAllUsersResponseDTO;
import com.gaga.auth_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<User, Long> {

     User findByEmail(String email);

     List<User> findAll();
}
