import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count=0;
        int p;
        Scanner s = new Scanner(System.in);
        System.out.println("how many times");
        p = s.nextInt();
        System.out.println("how many threads");
        int t = s.nextInt();

        Thread commit =new Thread(){
           public void run(){
               while(count<p) {
                   try {
                       Runtime.getRuntime().exec("git commit --allow-empty -m \"Threaded Commit\"");
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }
           }
        };
    }
}
