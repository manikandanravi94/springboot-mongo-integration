package com.mani.controller;

import com.mani.model.Employee;
import com.mani.model.in.EmployeeRequest;
import com.mani.persistence.EmployeeRepository;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by gbs05347 on 16-09-2020.
 */
@RestController
public class EmployeeController {

    public static int entry =1;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @GetMapping(value = "/")
    public Employee getEmployee(@RequestParam  String name){
        Employee employee =employeeRepository.findByName(name);
        System.out.println("Employee fetched successfully");
        return employee;
    }

    @PostMapping(value = "/")
    public void getEmployee(@RequestBody EmployeeRequest employee){
        Employee emp = new Employee(employee.getEmpId(),employee.getName(),employee.getDesignation());
        employeeRepository.save(emp);
        System.out.println("insertion successfull");
    }

    @PostMapping("/uploadimage")
    public String saveEmployeeFile(@RequestParam("name") String name, @RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
        try {
            Employee employee =employeeRepository.findByName(name);
            ObjectId id=gridFsTemplate.store(file.getInputStream(),file.getOriginalFilename(),file.getContentType(),employee);
            List<String> objIdList = employee.getImageIds();
            objIdList.add(id.toString());
            System.out.println("ObjectId:"+id.toString());
            employee.setImageIds(objIdList);
            employeeRepository.save(employee);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "successfully uploaded";
    }

    @GetMapping("/image")
    public String getEmployeeImage(HttpServletResponse response, String id, String name){
        GridFSFile file=gridFsTemplate.findOne(new Query(Criteria.where("metadata.name").is(name).and("_id").is(id)));
        try {
            FileCopyUtils.copy( gridFsTemplate.getResource(file).getInputStream(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "download success";
    }

    @GetMapping("/allimages")
    public String getEmployeeImages(HttpServletResponse response, String name){
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        try(ZipOutputStream zipOutputStream= new ZipOutputStream(response.getOutputStream())){

            List<GridFSFile> fileList = new ArrayList<GridFSFile>();
            gridFsTemplate.find(new Query(Criteria.where("metadata.name").is(name))).into(fileList);
            //GridFSFindIterable iterable=gridFsTemplate.find(new Query(Criteria.where("metadata.name").is(name)));
            for (GridFSFile gridFSFile : fileList) {
                //    InputStream inputStream = gridFsTemplate.getResource(gridFSFile).getInputStream();
                zipOutputStream.putNextEntry(new ZipEntry(gridFSFile.getFilename()));
                StreamUtils.copy(gridFsTemplate.getResource(gridFSFile).getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "successfully downloaded";
    }

    @DeleteMapping("/deleteimage")
    public String deleteEmployeeImage(String name, String id){
        Employee emp = employeeRepository.findByName(name);
        gridFsTemplate.delete(new Query(Criteria.where("metadata.name").is(name).and("_id").is(id)));
        emp.getImageIds().remove(id);
        employeeRepository.save(emp);
        return "Success";
    }

}
