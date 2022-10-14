import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.System.currentTimeMillis;

public class Main {
    static boolean pushing = false;

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner s = new Scanner(System.in);
        System.out.println("how many times (infinite rn)");
        int p = s.nextInt();
        System.out.println("how many threads");
        int t = s.nextInt();

        File f = new File("/Users/wduan/Downloads/epic boredom");
        Path path = f.toPath();

        gitPush(path);
        //prr = rt.exec("git commit --allow-empty -m \"i am speed\"");

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(t);
        for (int j = 1; j <= t; j++) {
            executor.submit(() -> {
                long count = 0;
                long t1 = System.currentTimeMillis();
                for (; ; ) {
                        gitCommit(path, "i am speed v2.0.1 : "+count);
                        count++;
                        if (count % 1000 == 0) {
                            long t2 = System.currentTimeMillis();
                            System.out.println("Git Pushing, 1000ms cooldown:" + (t2-t1));
                            t1 = System.currentTimeMillis();
                            Thread.sleep(1000);
                            gitPush(path);
                            System.out.println("Git Pushing, 3000ms cooldown");
                            Runtime.getRuntime().exec("clear");
                            Thread.sleep(3000);
                        }
                }
            });


            System.out.println("THREAD #" + j + " STARTED");
            Thread.sleep(200);
        }
    }


        public static void gitCommit (Path directory, String message) throws IOException, InterruptedException {
            runCommand(directory, "git", "commit", "--allow-empty", "-m", message);
        }

        public static void gitPush (Path directory) throws IOException, InterruptedException {
            runCommand(directory, "git","push","-u","origin","master");
        }

        public static void runCommand (Path directory, String...command) throws IOException, InterruptedException {
            Objects.requireNonNull(directory, "directory");
            if (!Files.exists(directory)) {
                throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
            }
            ProcessBuilder pb = new ProcessBuilder()
                    .command(command)
                    .directory(directory.toFile());
            Process p = pb.start();
            int exit = p.waitFor();

            /*
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
            outputGobbler.start();
            errorGobbler.start();
            errorGobbler.join();
            outputGobbler.join();
            if (exit != 0) {
                throw new Error(String.format("runCommand returned %d", exit));
            }

             */


        }
        private static class StreamGobbler extends Thread {

            private final InputStream is;
            private final String type;

            private StreamGobbler(InputStream is, String type) {
                this.is = is;
                this.type = type;
            }

            @Override
            public void run() {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(type + "> " + line);
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

    }
