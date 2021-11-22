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
        return new int[] { -1, iterations }; // nenhuma ocorrência.
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
    private static int KMPSearchInstructions = 0;

    /**
     * 
     * @param pat First String.
     * @param txt Second String.
     * @return An array with the index occurence [0], the number of iterations
     *         needed [1] and the number of instructions needed [2]. -1 if no
     *         occurences, otherwise the index of the occurence.
     */
    public static int[] KMPSearch(String pat, String txt) {
        KMPSearchIterations = 0;

        int M = pat.length();
        int N = txt.length();
        KMPSearchInstructions += 4; // Atribuicao + acesso a atributos

        // Cria lps[] que vai guardar o maior
        // valor prefixo sufixo para o padrão
        int lps[] = new int[M];
        int j = 0; // index for pat[]
        KMPSearchInstructions += 2; // Atribuicao

        // Calcula lps[]
        computeLPSArray(pat, M, lps);

        int i = 0; // index for txt[]
        KMPSearchInstructions++; // Atribuicao
        while (i < N) {
            KMPSearchIterations++;
            KMPSearchInstructions += 5; // Condicao while e primeiro if + acesso a atributos + comparacao
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
                KMPSearchIterations += 4; // Atribuicao e operacao aritmetica
            }
            KMPSearchInstructions++; // Condicao if
            if (j == M) {
                KMPSearchInstructions += 2; // Return e operacao aritmetica
                return new int[] { i - j, KMPSearchIterations, KMPSearchInstructions };
            } else if (i < N && pat.charAt(j) != txt.charAt(i)) { // mismatch após j matches
                KMPSearchInstructions += 5; // Tudo dentro do else if soma 5
                // Não faz match dos caracteres lps[0..lps[j-1]],
                // não é necesssário, eles combinarão
                if (j != 0) {
                    j = lps[j - 1];
                    KMPSearchInstructions += 4; // Condicao if, atribuicao, acesso ao array e operacao aritmetica
                } else {
                    i = i + 1;
                    KMPSearchInstructions += 2; // Atribuicao e operacao aritmetica
                }
            }
        }

        KMPSearchInstructions += 2; // Return
        return new int[] { -1, KMPSearchIterations, KMPSearchInstructions };
    }

    private static void computeLPSArray(String pat, int M, int lps[]) {
        // Tamanho do maior prefixo sufixo anterior
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0
        KMPSearchInstructions += 4; // Atribuicoes e acesso a posicao do array

        // Loop calcula lps[i] for i = 1 to M-1
        while (i < M) {
            KMPSearchIterations++;
            KMPSearchInstructions += 4; // Condicao primeiro if, acesso a atributos e comparacao
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
                KMPSearchInstructions += 6; // Atribuicao, operacoes aritmeticas, acesso ao array
            } else // (pat[i] != pat[len])
            {
                if (len != 0) {
                    len = lps[len - 1];
                    KMPSearchInstructions += 4; // Condicao if, atribuicao, acesso ao array e operacao aritmetica
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                    KMPSearchInstructions += 4; // Acesso ao array, atribuicao e operacao aritmetica
                }
            }
        }
    }

}