package com.gaga.auth_server.mapper;

import com.gaga.auth_server.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    //로그인


    //회원가입
    public int insertUser(@Param("user") User user);

    //getAlluser 모든 유저 정보 조회
    public List<User> getAllUser();

    // 유저 정보 삭제
    public int deleteUser(@Param("id") int id);
}
