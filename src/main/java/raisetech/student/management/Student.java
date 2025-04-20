package raisetech.student.management;

public class Student {
    private int idStudent;
    private String name;
    private String kanaName;
    private String nickname;
    private String mailAddress;
    private String address;
    private int age;
    private int sex;

    public int getId() { return idStudent; }

    public void setId(int id) { this.idStudent = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKanaName() {
        return kanaName;
    }

    public void setKanaName(String KanaName) {
        this.kanaName = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) { this.mailAddress = name;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = name;}

    public int getAge() {
        return age;
    }

    public void setSex(int sex) {
        this.age = age;
    }

    public int getSex() { return sex; }

    public void setAge(int sex) {
        this.sex = age;
    }
}
