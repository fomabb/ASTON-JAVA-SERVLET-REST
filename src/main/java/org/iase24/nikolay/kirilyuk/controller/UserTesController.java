package org.iase24.nikolay.kirilyuk.controller;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.entity.test_index.UserTest;
import org.iase24.nikolay.kirilyuk.service.TestUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class UserTesController {

    private final TestUserService testUserService;

    @PostMapping
    public void CreatingTwelveThousandUsers(@RequestBody List<UserTest> users) {
        testUserService.CreatingTwelveThousandUsers(users);
    }

    @GetMapping
    public Slice<UserTest> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return testUserService.getAllUsers(PageRequest.of(page, size));
    }

    @GetMapping("/check-index")
    public List<String> checkIndex() {
        return testUserService.checkIndexEfficiency();
    }

    @GetMapping("/search")
    public Slice<UserTest> searchUsersByLogin(
            @RequestParam("login") String login
            , @RequestParam("page") int page
            , @RequestParam("size") int size
    ) {
        return testUserService.getUsersByLogin(login, PageRequest.of(page, size));
    }
}
