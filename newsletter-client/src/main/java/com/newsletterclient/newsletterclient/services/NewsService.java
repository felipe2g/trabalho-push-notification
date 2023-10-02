package com.newsletterclient.newsletterclient.services;

import com.newsletterclient.newsletterclient.models.News;
import com.newsletterclient.newsletterclient.models.dtos.NewsDTO;
import com.newsletterclient.newsletterclient.repositories.NewsRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository repository) {
        this.newsRepository = repository;
    }

    public ResponseEntity<List<NewsDTO>> findAll() {
        var dbNews = newsRepository.findAll();
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();

        var newsDtos = dbNews.stream().map(news -> {
            return new NewsDTO(news);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(newsDtos);
    }

    public ResponseEntity<NewsDTO> findById(ObjectId id) {
        if(id == null)
            return ResponseEntity.badRequest().build();
        var dbNews = newsRepository.findById(id);
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new NewsDTO(dbNews.get()));
    }

    public ResponseEntity<NewsDTO> save(News news) {
        if(news.getTitle().isBlank() ||
                news.getEditorName().isBlank() ||
                news.getDate().isBlank())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(new NewsDTO(newsRepository.save(news)));
    }

    public ResponseEntity<NewsDTO> update(NewsDTO newsDTO) {
        // validar newsDTO
        if(newsDTO.getId() == null)
            return ResponseEntity.badRequest().build();

        var objectId = new ObjectId(newsDTO.getId());
        var dbNews = newsRepository.findById(objectId);
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();
        // atualizar
        var dbNewsObj = dbNews.get();
        dbNewsObj.setTitle(newsDTO.getTitle());
        dbNewsObj.setDate(newsDTO.getDate());
        dbNewsObj.setEditorName(newsDTO.getEditorName());

        return ResponseEntity.ok(new NewsDTO(newsRepository.save(dbNewsObj)));
    }

    public ResponseEntity<?> delete(ObjectId id) {
        if(id == null)
            return ResponseEntity.badRequest().build();

        newsRepository.deleteById(id);

        var dbNews = newsRepository.findById(id);
        if(dbNews.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();

        return ResponseEntity.ok().build();
    }
}
