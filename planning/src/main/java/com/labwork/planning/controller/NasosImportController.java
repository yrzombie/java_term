package com.labwork.planning.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labwork.planning.parser.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nasos")
public class NasosImportController {

    private final NasosParser parser;

    @PostMapping("/{variant}")
    public String importData(@PathVariable int variant) {
        parser.importFromNasosFile(variant, "995");
        return "Импорт завершен";
    }
}