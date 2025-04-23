package raisetech.student.management;

public class Student {
    private int id;
    private String name;
    private String kanaName;
    private String nickname;
    private String mailAddress;
    private String address;
    private int age;
    private int sex;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKanaName() {
        return kanaName;
    }

    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) { this.mailAddress = mailAddress;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = address;}

    public int getAge() {
        return age;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() { return sex; }

    public void setAge(int age) {
        this.age = age;
    }
}
