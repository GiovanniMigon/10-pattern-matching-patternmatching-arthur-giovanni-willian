public class Main {

    public static void main(String[] args) {

        System.out.println("*** PATTERN MATCHING ***\n");
        String s1 = "ABCDCBDCBDACBDABDCBADF";
        String s2 = "ADF";
        System.out.println("String 1: " + s1 + " | String 2: " + s2 + "\n");

        System.out.println("* Força Bruta *");
        int[] bruteForceResults = PatternMatching.bruteForce(s1, s2);
        System.out.println("Índice de ocorrência: " + bruteForceResults[0]);
        System.out.println("Iterações: " + bruteForceResults[1]);

    }

}
