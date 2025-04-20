package raisetech.student.management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM student WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Select("SELECT * FROM student")
    @Results({
            @Result(property = "idStudent", column = "id_student"),
            @Result(property = "kanaName", column = "kana_name"),
            @Result(property = "mailAddress", column = "mail_address")
            // 他のカラムも同様に追加
    })
    List<Student> findAll();
}
