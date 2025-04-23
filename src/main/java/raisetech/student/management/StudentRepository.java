package raisetech.student.management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT id_student AS id, name, kana_name AS kanaName, nickname, mail_address AS mailAddress, address, age, sex FROM student WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
            @Result(property = "id", column = "id_student"),
            @Result(property = "kanaName", column = "kana_name"),
            @Result(property = "mailAddress", column = "mail_address"),
            @Result(property = "name", column = "name"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "address", column = "address"),
            @Result(property = "age", column = "age"),
            @Result(property = "sex", column = "sex")
    })
    List<Student> findByName(String name);
}
