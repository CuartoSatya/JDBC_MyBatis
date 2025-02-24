package raisetech.student.management;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	private String name = "Oda Shintaro";
	private String age = "44";

	private Map<String,String> student;

	public static void main(String[] args) {
		//localhost:8080
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo() {
		return name + " " + age + " years old";
				}
	@PostMapping("/studentInfo")
	public void setStudentInfo(String name, String age) {
		this.name = name;
		this.age = age;
	}
	@PostMapping("/studentName")
	public void UpdateStudentName(String name) {
		this.name = name;
	}
 }
