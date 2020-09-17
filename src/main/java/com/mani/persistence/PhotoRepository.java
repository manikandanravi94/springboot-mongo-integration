package com.mani.persistence;

import com.mani.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gbs05347 on 17-09-2020.
 */
@Repository
public interface PhotoRepository extends MongoRepository<Photo, String>{
}
