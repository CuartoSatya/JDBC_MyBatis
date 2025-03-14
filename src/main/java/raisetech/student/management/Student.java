package raisetech.student.management;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {
    private String name;
    private int age;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
