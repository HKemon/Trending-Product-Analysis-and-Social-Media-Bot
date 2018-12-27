import java.util.ArrayList;

public class TESTCLASS {
    public static void main(String[] args) {
        final int x =10;
        class test{
            public  void m2(){
                System.out.println(x);
            }
        }
        test test = new test();
        test.m2();
    }
}
