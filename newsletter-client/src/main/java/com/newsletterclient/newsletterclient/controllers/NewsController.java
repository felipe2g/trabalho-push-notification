package com.newsletterclient.newsletterclient.controllers;

import com.newsletterclient.newsletterclient.models.News;
import com.newsletterclient.newsletterclient.models.dtos.NewsDTO;
import com.newsletterclient.newsletterclient.services.NewsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/news")
public class NewsController {
    private NewsService newsService;

    @Value("${eureka.instance.instance-id}")
    private String instaceId;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/instance")
    public String getInstanceId() {
        return instaceId;
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return newsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable ObjectId id) {
        return newsService.findById(id);
    }

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody News news) {
        return newsService.save(news);
    }

    @PutMapping
    public ResponseEntity<NewsDTO> updateNews(@RequestBody NewsDTO news) {
        return newsService.update(news);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable ObjectId id) {
        newsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
