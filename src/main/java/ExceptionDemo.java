public class ExceptionDemo {
    public static void hello(){
        throw new RuntimeException("错误1");
    }

    public static void main(String[] args) {
        try {
            hello();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
