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

    /**
     * 
     * @param a First String.
     * @param b Second String.
     * @return An array with the index occurence [0] and the number of iterations
     *         needed [1]. -1 if no occurences, otherwise the index of the
     *         occurence.
     */
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

    private static int KMPSearchIterations = 0;

    // KMP pattern searching algorithm
    public static int[] KMPSearch(String pat, String txt) {
        KMPSearchIterations = 0;

        int M = pat.length();
        int N = txt.length();

        // Cria lps[] que vai guardar o maior
        // valor prefixo sufixo para o padrão
        int lps[] = new int[M];
        int j = 0; // index for pat[]

        // Calcula lps[]
        computeLPSArray(pat, M, lps);

        int i = 0; // index for txt[]
        while (i < N) {
            KMPSearchIterations++;
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                return new int[] { i - j, KMPSearchIterations };
            } else if (i < N && pat.charAt(j) != txt.charAt(i)) { // mismatch após j matches
                // Não faz match dos caracteres lps[0..lps[j-1]],
                // não é necesssário, eles combinarão
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }

        return null;
    }

    private static void computeLPSArray(String pat, int M, int lps[]) {
        // Tamanho do maior prefixo sufixo anterior
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // Loop calcula lps[i] for i = 1 to M-1
        while (i < M) {
            KMPSearchIterations++;
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {
                if (len != 0) {
                    len = lps[len - 1];
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

}