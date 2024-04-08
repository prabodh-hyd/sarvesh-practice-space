package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.Service.BreakService;
import com.Project.TimetableApplication.models.OutputObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Break")
public class BreakController {
    @PostMapping("/SetBreak")
    public static OutputObject setBreak(int a){
        return BreakService.breakApply(a);
    }
}
