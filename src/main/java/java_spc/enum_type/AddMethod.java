package java_spc.enum_type;

/**
 * 在enum中添加自定义方法
 * 添加自定义方法时必须先定义enum实例，
 * 同时在enum的最后一个实例后添加分号
 *
 * @author SPC
 */
public enum AddMethod {
    WEST("go to west"),
    EAST("go to east"),
    SOUTH("go to south"),
    NORTH("go to north");

    private String description;

    private AddMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static void main(String[] args) {
        for (AddMethod a : AddMethod.values()) {
            System.out.println(a + " : " + a.getDescription());
        }
    }

}
