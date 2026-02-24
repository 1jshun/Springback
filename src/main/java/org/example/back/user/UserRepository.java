package org.example.back.user;

import org.example.back.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 메소드 이름의 시작을 find로 하면 SELECT
    // find 뒤에 By를 추가하면 WHERE
    // By뒤에 엔티티의 특정 변수이름을 추가하면 WHERE의 조건으로 추가
    // SELECT * FROM user WHERE email=?
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
