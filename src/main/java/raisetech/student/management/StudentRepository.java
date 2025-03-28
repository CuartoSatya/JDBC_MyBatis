package raisetech.student.management;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentRepository {
    @Select("SELECT * FROM students WHERE name = #{name}")
    List<Student> findByName(@Param("name") String name);

}
