package org.iase24.nikolay.kirilyuk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerView {

    @RequestMapping("/view")
    public String view() {
        return "index";
    }
}
