class PatternMatching {

    /**
     * 
     * @param a First String.
     * @param b Second String.
     * @return An array with the index occurence [0] and the number of iterations needed [1]. 
     * -1 if no occurences, otherwise the index of the occurence.
     */
    public static int[] bruteForce(String a, String b) {

        String bigger = a.length() >= b.length() ? a : b;
        String smaller = a.length() < b.length() ? a : b;

        int indexOfOccurence = -1;

        int biggerLength = bigger.length();
        int smallerLength = smaller.length();

        int biggerIterator = 0, smallerIterator = 0, matchingStreak = 0;

        while(biggerIterator < biggerLength){
            if(smaller.charAt(smallerIterator) == bigger.charAt(biggerIterator)){
                if(matchingStreak == 0){
                    indexOfOccurence = biggerIterator;
                }
                smallerIterator++;
                matchingStreak++;
            } else {
                matchingStreak = smallerIterator = 0;
            }
            if(matchingStreak == smallerLength){
                System.out.println("Entrou aqui!");
                return new int[] {indexOfOccurence, biggerIterator};
            }
            biggerIterator++;
        }

        return new int[] {-1, biggerIterator};

    }

}