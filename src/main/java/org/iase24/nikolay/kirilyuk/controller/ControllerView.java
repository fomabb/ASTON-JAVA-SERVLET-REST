package org.iase24.nikolay.kirilyuk.controller;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ControllerView {

    private final CourseService courseService;

    @GetMapping("/index")
    public String index(
            @RequestParam(name = "name", required = false, defaultValue = "Aston") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "index";
    }
}
