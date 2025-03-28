package raisetech.student.management;

import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentRepository {
    @Select("SELECT id, name, age FROM student WHERE name = #{name}")
    Student findByName(@Param("name") String name);
    @Insert("INSERT INTO student (name, age) VALUES (#{name}, #{age})")
    void registerStudent(@Param("name") String name, @Param("age") int age);
    @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
    void updateStudent(String name, int age);
}
