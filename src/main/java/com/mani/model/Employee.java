package com.mani.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbs05347 on 16-09-2020.
 */
@Document
@Setter
@Getter
@ToString
public class Employee {


    @Id
    private String empId;

    private String name;

    private String designation;

    private List<String> imageIds= new ArrayList<>();

    public Employee() {
    }

    public Employee(String empId, String name, String designation) {
        this.empId=empId;
        this.name = name;
        this.designation = designation;
    }
}
