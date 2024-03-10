package com.dsi.dem.repository;

import com.dsi.dem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public User getUserByEmail(String email);
}
