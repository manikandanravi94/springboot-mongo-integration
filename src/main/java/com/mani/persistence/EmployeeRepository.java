package com.mani.persistence;

import com.mani.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gbs05347 on 16-09-2020.
 */
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Employee findByName(String name);
}
