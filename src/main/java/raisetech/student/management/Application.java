package raisetech.student.management;

import java.util.Map;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@MapperScan("raisetech.student.management")
public class Application {
	@Autowired
	private StudentRepository repository;

	private String name = "Enami Kouji";
	private int age = 44;

	private Map<String, Integer> student;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@GetMapping("/studentInfo")
	public String getStudentInfo() {
		Student student = repository.findByName("Tanaka Taro");
		if (student == null) {
			return "Student not found";
		}
		return student.getName() + " " + student.getAge() + " years old";
	}

	@PostMapping("/studentInfo")
	public void setName(@RequestParam String name, @RequestParam int age) {
		this.name = name;
		this.age = age;
	}

	@PostMapping("/studentName")
	public void updateStudentName(@RequestParam String name){
		this.name = name;
	}
 }
