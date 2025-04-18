package raisetech.student.management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
    public interface StudentMapper {
        @Select("SELECT 1")
        Integer testQuery();
}
