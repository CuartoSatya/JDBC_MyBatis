package raisetech.student.management;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository {
    @Select("SELECT * FROM student WHERE name = #{name}")
    Student findByName(String name);
    @Insert("INSERT INTO student (name, age) VALUES (#{name}, #{age})")
    void registerStudent(@Param("name") String name, @Param("age") int age);

}
