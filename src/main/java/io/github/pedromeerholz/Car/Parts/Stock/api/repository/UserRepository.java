package io.github.pedromeerholz.Car.Parts.Stock.api.repository;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "select userId from user order by userId desc limit 1;")
    public Long getLastUserId();
}
