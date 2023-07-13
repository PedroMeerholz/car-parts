package io.github.pedromeerholz.stock.api.repository;

import io.github.pedromeerholz.stock.api.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE email = :email")
    public Optional<User> findByEmail(@Param("email") String email);
}
