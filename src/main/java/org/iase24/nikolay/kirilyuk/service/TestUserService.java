package org.iase24.nikolay.kirilyuk.service;

import org.iase24.nikolay.kirilyuk.entity.test_index.UserTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TestUserService {

    void CreatingTwelveThousandUsers(List<UserTest> users);

    Slice<UserTest> getAllUsers(PageRequest pageable);

    List<String> checkIndexEfficiency();

    Slice<UserTest> getUsersByLogin(String login, PageRequest of);
}
