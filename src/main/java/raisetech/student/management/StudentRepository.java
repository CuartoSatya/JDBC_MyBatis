package raisetech.student.management;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM student WHERE name LIKE CONCAT('%', #{name}, '%'))")
    List<raisetech.student.management.Student> searchByName(@Param("name") String name);

    @Select("SELECT 1")
    Integer testQuery();

}
