package raisetech.student.management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT id_student AS id, name, kana_name AS kanaName, nickname, mail_address AS mailAddress, address, age, sex FROM student WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Student> findByName(String name);
}
