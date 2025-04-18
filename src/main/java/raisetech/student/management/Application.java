package raisetech.student.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@MapperScan("raisetech.student.management")
@RestController
public class Application {

	@Autowired
	private raisetech.student.management.StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/student")
	public List<raisetech.student.management.Student> getStudentList(@RequestParam String name) {
		return repository.searchByName(name);
	}
	@GetMapping("/test")
	public Integer testDatabaseConnection() {
		return repository.testQuery();
	}
}
