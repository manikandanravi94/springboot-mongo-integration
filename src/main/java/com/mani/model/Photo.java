package com.mani.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by gbs05347 on 17-09-2020.
 */
@Document
@Getter
@Setter
@ToString
public class Photo {

    @Id
    private String id;

    private String title;

    private Binary image;
}
