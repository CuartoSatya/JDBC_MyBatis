package raisetech.student.management;

import java.util.Map;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@MapperScan("raisetech.student.management")
public class Application {
	@Autowired
	private StudentRepository repository;

	private Map<String, Integer> student;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@GetMapping("/student")
	public String getStudent(@RequestParam String name) {
		Student student = repository.findByName(name);
		return student.getName() + " " + student.getAge() + " years old";
	}
	@PostMapping("/student")
	public void registerStudent(@RequestParam String name, int age) {
		repository.registerStudent(name, age);
	}
	@PatchMapping("/student")
	public void updateStudent(@RequestParam String name, int age){
		repository.updateStudent(name, age);
	}
	@DeleteMapping("/student")
	public void deleteStudent(@RequestParam String name){
		repository.deleteStudent(name);
	}
 }
