package org.iase24.nikolay.kirilyuk.repository;

import org.iase24.nikolay.kirilyuk.entity.test_index.UserTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepository extends JpaRepository<UserTest, Long> {

    @Query("select ut from UserTest ut where ut.login like :login%")
    Slice<UserTest> findUserTestByLoginLike(String login, PageRequest pageRequest);
}
