package com.thinkitive.practice2.controller;

import com.thinkitive.practice2.entity.Student;
import com.thinkitive.practice2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/student") // every URL in this class starts with /api/student
public class StudentController {

    @Autowired
    private StudentService studentService;

    // POST http://localhost:8080/api/students
    // @RequestBody converts the JSON body into a Student object
    @PostMapping
    public Student createStudent(@RequestBody Student student)
    {
        return studentService.createStudent(student);
    }

    // GET http://localhost:8080/api/students
    @GetMapping
    public List<Student> getAllStudents()
    {
        return studentService.getAllStudents();
    }

    // GET http://localhost:8080/api/students/1
    // @PathVariable reads the {id} part of the URL
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id)
    {
        return studentService.getStudentById(id);
    }

    // GET http://localhost:8080/api/students/search?course=BSc
    // @RequestParam reads the ?course=... part of the URL
    @GetMapping("/search")
    public List<Student> getStudentsByCourse(@RequestParam String course)
    {
        return studentService.getStudentsByCourse(course);
    }

    // PUT http://localhost:8080/api/students/1
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student)
    {
        return studentService.updateStudent(id, student);
    }

    // DELETE http://localhost:8080/api/students/1   (soft delete)
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id)
    {
        studentService.deleteStudent(id);
        return "Student with id " + id + " deleted successfully";
    }

    // GET http://localhost:8080/api/students/deleted
    @GetMapping("/deleted")
    public List<Student> getDeletedStudents()
    {
        return studentService.getDeletedStudents();
    }

    // PUT http://localhost:8080/api/students/1/restore
    @PutMapping("/{id}/restore")
    public Student restoreStudent(@PathVariable Long id)
    {
        return studentService.restoreStudent(id);
    }
}
