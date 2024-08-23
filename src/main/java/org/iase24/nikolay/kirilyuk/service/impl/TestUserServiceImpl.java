package org.iase24.nikolay.kirilyuk.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.entity.test_index.UserTest;
import org.iase24.nikolay.kirilyuk.repository.TestUserRepository;
import org.iase24.nikolay.kirilyuk.service.TestUserService;
import org.iase24.nikolay.kirilyuk.utill.LoginBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestUserServiceImpl implements TestUserService {

    private final TestUserRepository testUserRepository;
    private final LoginBuilder loginBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    private static final int BATCH_SIZE = 1_000;

    @Override
    @Transactional
    public void CreatingTwelveThousandUsers(List<UserTest> users) {
        List<UserTest> newUsers = new ArrayList<>();
        Random random = new Random();
        List<String> randomNames = loginBuilder.createRandomData();


        for (int i = 0; i < 3_000_000; i++) {
            UserTest userTest = new UserTest();
            userTest.setLogin(randomNames.get(random.nextInt(randomNames.size())) + "_" + i);
            userTest.setEmail(users.get(0).getEmail() + "_" + i);
            userTest.setPassword(loginBuilder.generateRandomPassword(random, 7, 12));
            newUsers.add(userTest);
        }

        // Когда набирается 1000 записей, сохраняем их в базу данных
        if (newUsers.size() == BATCH_SIZE) {
            testUserRepository.saveAll(newUsers);
            newUsers.clear(); // Очищаем список после сохранения
        }

        // Сохраняем оставшиеся записи, если они есть
        if (!newUsers.isEmpty()) {
            testUserRepository.saveAll(newUsers);
        }
    }


    @Override
    public Slice<UserTest> getAllUsers(PageRequest pageable) {
        return testUserRepository.findAll(pageable);
    }

    @Override
    public Slice<UserTest> getUsersByLogin(String login, PageRequest pageRequest) {
        return testUserRepository.findUserTestByLoginLike(login, pageRequest);
    }

    @Override
    public List<String> checkIndexEfficiency() {
        String sql = "EXPLAIN ANALYZE SELECT * FROM user_test_index WHERE login = :login";
        String loginToCheck = "Zeke_243740%";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("login", loginToCheck);

        return query.getResultList();
    }
}
