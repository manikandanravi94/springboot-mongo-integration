package com.mani.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * Created by gbs05347 on 16-09-2020.
 */
//@Configuration
public class GridFSMongoConfig {

    @Autowired
    private MappingMongoConverter mongoConverter;


    protected String getDatabaseName() {
        return "localtestdb";
    }



}
