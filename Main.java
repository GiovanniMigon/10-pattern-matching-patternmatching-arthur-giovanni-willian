import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        System.out.println("*** PATTERN MATCHING ***\n");

        String book = stringFromFile("book.txt");
        String[] entries = new String[] { "plumage", "gunwales", "captain" };

        System.out.println("* Força Bruta *\n");

        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            int[] bruteForceResults = PatternMatching.bruteForce(entry, book);
            System.out.println("S1: " + entry + " | S2: (conteúdo do livro em 'book.txt')");
            System.out.println("Índice de ocorrência: " + bruteForceResults[0]);
            System.out.println("Iterações: " + bruteForceResults[1] + "\n");
        }

        System.out.println("\n* Rabin Karp *\n");

        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            int[] rabinKarpResults = PatternMatching.rabinKarp(entry, book);
            System.out.println("S1: " + entry + " | S2: (conteúdo do livro em 'book.txt')");
            System.out.println("Índice de ocorrência: " + rabinKarpResults[0]);
            System.out.println("Iterações: " + rabinKarpResults[1] + "\n");
        }

        System.out.println("\n* Knuth-Morris-Pratt *\n");

        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            int[] kmpResults = PatternMatching.KMPSearch(entry, book);
            System.out.println("S1: " + entry + " | S2: (conteúdo do livro em 'book.txt')");
            System.out.println("Índice de ocorrência: " + kmpResults[0]);
            System.out.println("Iterações: " + kmpResults[1]);
            System.out.println("Instruções: " + kmpResults[2] + "\n");
        }

    }

    public static String stringFromFile(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            br.close();
            return sb.toString();
        } catch (FileNotFoundException error) {
            System.out.println("Erro, arquivo não encontrado.");
            return "";
        } catch (IOException error) {
            System.out.println("Erro durante a leitura do arquivo");
            return "";
        }
    }

}
