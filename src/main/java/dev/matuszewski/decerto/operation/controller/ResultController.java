package dev.matuszewski.decerto.operation.controller;

import dev.matuszewski.decerto.operation.dto.ResultDto;
import dev.matuszewski.decerto.operation.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @GetMapping("result")
    public ResultDto getCurrentValue() {
        return resultService.receiveCurrentResult();
    }
}
