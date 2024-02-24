package com.blog.repository;

import com.blog.entity.User;
import net.bytebuddy.dynamic.DynamicType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByUsername(String username); // String username: this should match with the entity table  attribute
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email); // findByUsernameOrEmail is correct it is a standard
                                                                          //findByusernameOremail is incorrect

    Boolean existsByEmail(String email); // existsByEmail : this is also a  standard naming covention in JPA
    Boolean existsByUsername(String username);

}
