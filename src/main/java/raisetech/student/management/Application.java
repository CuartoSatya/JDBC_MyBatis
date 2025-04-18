package raisetech.student.management;

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
	private StudentMapper studentMapper;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/test")
	public Integer testQuery() {
		return studentMapper.testQuery();
	}}
