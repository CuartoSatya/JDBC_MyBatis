package raisetech.student.management.constant;

import lombok.Getter;

@Getter
public enum CourseProgressStatus {

    PRE_SIGNUP(0, "preSignUp"),
    SIGNED_UP(1, "signUp"),
    LAUNCHED(2, "active"),
    FINISHED(3, "finish");

    private final int code;
    private final String label;

    CourseProgressStatus(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static CourseProgressStatus fromCode(int code) {
        for (CourseProgressStatus s : values()) {
            if (s.code == code) return s;
        }
        throw new IllegalArgumentException("Unknown status: " + code);
    }
}
