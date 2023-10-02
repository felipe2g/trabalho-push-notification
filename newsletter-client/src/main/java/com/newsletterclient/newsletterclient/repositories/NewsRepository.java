package com.newsletterclient.newsletterclient.repositories;

import com.newsletterclient.newsletterclient.models.News;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, ObjectId> {
}
