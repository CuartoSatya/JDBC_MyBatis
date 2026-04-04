package raisetech.student.management.data;

import lombok.Getter;

@Getter
public enum CourseStatus {
    TEMPORARY(0, "仮申込"),
    OFFICIAL(1, "本申込"),
    TAKING(2, "受講中"),
    FINISHED(3, "受講終了");

    private final int value;
    private final String label;

    CourseStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String fromValue(int value) {
        for (CourseStatus status : values()) {
            if (status.value == value) {
                return status.label;
            }
        }
        return "不明";
    }
}
