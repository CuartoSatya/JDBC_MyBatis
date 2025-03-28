package raisetech.student.management;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentRepository {
    @Select("SELECT id, name, age FROM student WHERE name = #{name}")
    Student findByName(@Param("name") String name);
    @Insert("INSERT INTO student (name, age) VALUES (#{name}, #{age})")
    void registerStudent(@Param("name") String name, @Param("age") int age);

}
