package com.example.lms.repository;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByPhoneNumberOrEmail(String phoneNumber, String email);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByUuid(String uuid);

    List<User> findUsersByRole(Role role);

    Optional<User> findUserByEmail(String email);

    @Query("SELECT a.id FROM User a WHERE a.uuid = :uuid")
    Long findIdByUuid(@Param("uuid") String uuid);

    @Query("SELECT u FROM User u WHERE u.id IN :userIds")
    List<User> findAllById(@Param("userIds") List<Long> userIds);

}
