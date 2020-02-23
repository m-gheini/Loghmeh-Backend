package IECA.logic;

public class ContinousWork implements java.lang.Runnable {

        @Override
        public void run() {
            System.out.println("Job trigged by scheduler");
        }
}
