package com.newsletterclient.newsletterclient.services;

import com.newsletterclient.newsletterclient.message.RabbitMqSendLog;
import com.newsletterclient.newsletterclient.models.News;
import com.newsletterclient.newsletterclient.models.dtos.LogDTO;
import com.newsletterclient.newsletterclient.models.dtos.NewsDTO;
import com.newsletterclient.newsletterclient.repositories.NewsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    private RabbitMqSendLog rabbitMqSendLog;

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

        var dbNews = new NewsDTO(newsRepository.save(news));

        rabbitMqSendLog.sendLog(
                new LogDTO(
                        "create",
                        Date.from(Instant.now()),
                        dbNews,
                        dbNews.getClass().toString()
                )
        );

        return ResponseEntity.ok(dbNews);
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

        var dbNewsForSave = new NewsDTO(newsRepository.save(dbNewsObj));

        rabbitMqSendLog.sendLog(
                new LogDTO(
                        "create",
                        Date.from(Instant.now()),
                        dbNewsForSave,
                        dbNewsForSave.getClass().toString()
                )
        );

        return ResponseEntity.ok(dbNewsForSave);
    }

    public ResponseEntity<?> delete(ObjectId id) {
        var dbNewsForDelete = newsRepository.findById(id);

        if(id == null)
            return ResponseEntity.badRequest().build();

        newsRepository.deleteById(id);

        var dbNews = newsRepository.findById(id);
        if(dbNews.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();

        rabbitMqSendLog.sendLog(
                new LogDTO(
                        "create",
                        Date.from(Instant.now()),
                        dbNewsForDelete,
                        dbNewsForDelete.getClass().toString()
                )
        );

        return ResponseEntity.ok().build();
    }
}
