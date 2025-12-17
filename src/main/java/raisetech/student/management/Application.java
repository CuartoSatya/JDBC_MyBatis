package raisetech.student.management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(title="StudentManegementSystem"))
@MapperScan("raisetech.student.management.repository")
@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/RinkAcademy")
	public String hello() {
		return "I'm learning in Aviva Okayama School.";

	}
 }
