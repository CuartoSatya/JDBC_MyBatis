package raisetech.student.management;

import java.util.Map;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@MapperScan("raisetech.student.management")
@RestController
public class Application {
	@Autowired
	private StudentRepository repository;

	private Map<String, Integer> student;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@GetMapping("/student")
	public List<Student> getStudentList(@RequestParam String name) {
		return repository.searchByName(name);
		}
	}
 }
