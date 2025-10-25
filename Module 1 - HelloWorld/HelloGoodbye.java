public class HelloGoodbye {
    public static void main(String[] args) {
        String name1 = args[0];
        String name2 = args[1];
        String hello = String.format("Hello %s and %s.", name1, name2);
        String goodbye = String.format("Goodbye %s and %s.", name2, name1);
        System.out.println(hello);
        System.out.println(goodbye);
    }
}
