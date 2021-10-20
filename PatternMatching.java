class PatternMatching {

    /**
     * 
     * @param a First String.
     * @param b Second String.
     * @return An array with the index occurence [0] and the number of iterations
     *         needed [1]. -1 if no occurences, otherwise the index of the
     *         occurence.
     */
    public static int[] bruteForce(String a, String b) {

        String bigger = a.length() >= b.length() ? a : b;
        String smaller = a.length() < b.length() ? a : b;

        int indexOfOccurence = -1;

        int biggerLength = bigger.length();
        int smallerLength = smaller.length();

        int biggerIterator = 0, smallerIterator = 0, matchingStreak = 0;

        while (biggerIterator < biggerLength) {
            if (smaller.charAt(smallerIterator) == bigger.charAt(biggerIterator)) {
                if (matchingStreak == 0) {
                    indexOfOccurence = biggerIterator;
                }
                smallerIterator++;
                matchingStreak++;
            } else {
                matchingStreak = smallerIterator = 0;
            }
            if (matchingStreak == smallerLength) {
                return new int[] { indexOfOccurence, biggerIterator };
            }
            biggerIterator++;
        }

        return new int[] { -1, biggerIterator };

    }

    public static int[] rabinKarp(String a, String b) {
        int iterations = 0;
        int m = a.length();
        int n = b.length();
        long patHash = hornerHash(a, m);
        iterations += hornerHashIterations;
        hornerHashIterations = 0;
        for (int i = 0; i <= n - m; i++) {
            iterations++;
            long txtHash = hornerHash(b.substring(i, i + m), m);
            iterations += hornerHashIterations;
            hornerHashIterations = 0;
            if (patHash == txtHash) {
                return new int[] { i, iterations }; // ocorrência? colisão?
            }
        }
        return new int[] { n, iterations }; // nenhuma ocorrência.
    }

    // Notação: o padrão tem M caracteres, o texto tem N caracteres, o alfabeto tem
    // R caracteres (0 … R−1)
    // Q é o módulo para o cálculo do Hash.
    // Qual o valor de Q? Escolha Q igual a um primo grande para minimizar a chance
    // de colisões.
    // Por exemplo: o maior primo que possa ser representado com um int
    private static int hornerHashIterations = 0;

    private static long hornerHash(String s, int m) {
        int q = Integer.MAX_VALUE;
        int r = 256;
        long h = 0;
        for (int j = 0; j < m; j++) {
            hornerHashIterations++;
            h = (h * r + s.charAt(j)) % q;
        }
        return h;
    }

}