package logpool.example.logpool.controllers;

import logpool.example.logpool.models.dto.LogDTO;
import logpool.example.logpool.models.dto.NewsDTO;
import logpool.example.logpool.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
public class LogController {

    @Autowired
    private LogService service;

    @GetMapping
    public ResponseEntity<List<LogDTO<NewsDTO>>> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<LogDTO<NewsDTO>> save(@RequestBody LogDTO logDTO) { return service.save(logDTO); }
}