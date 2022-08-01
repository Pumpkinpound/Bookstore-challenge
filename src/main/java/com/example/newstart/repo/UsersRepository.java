package com.example.newstart.repo;

import com.example.newstart.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    @Transactional
    List<Users> deleteByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
