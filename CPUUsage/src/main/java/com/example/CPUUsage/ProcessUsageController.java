package com.example.CPUUsage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProcessUsageController {
    @Autowired
    private ProcessUsageService processUsageService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("processUsage", processUsageService.getProcessUsage("my_app.exe"));
        return "index";
    }
}