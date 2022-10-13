import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String[] args) throws IOException {
        int count=0;
        int p;
        Scanner s = new Scanner(System.in);
        System.out.println("how many times");
        p = s.nextInt();
        System.out.println("how many threads");
        int t = s.nextInt();
        Runtime rt = Runtime.getRuntime();

        //prr = rt.exec("git commit --allow-empty -m \"i am speed\"");

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(t);

        executor.submit(() -> {
            for(int i=0;i<=p/t;i++) {
                try {
                    Process prr = rt.exec("git add .");
                    printResults(prr);
                    //Process pr = rt.exec("git commit --allow-empty -m \"i am speed\"");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });




    }
    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
