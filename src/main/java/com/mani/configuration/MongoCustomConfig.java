package com.mani.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * Created by gbs05347 on 16-09-2020.
 */
@Configuration
public class MongoCustomConfig {

    @Autowired
    private MappingMongoConverter mongoConverter;

    @Bean
    public MongoClient mongo(){
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/localtestdb");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        return new MongoTemplate(mongo(),"localtestdb");
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(new SimpleMongoClientDatabaseFactory(mongo(), "localtestdb"), mongoConverter);
    }

}
