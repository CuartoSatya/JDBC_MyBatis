package raisetech.student.management;

import org.apache.ibatis.annotations.Mapper;

    @Mapper
    public interface StudentMapper {
        Student findByName(String name);
    }
