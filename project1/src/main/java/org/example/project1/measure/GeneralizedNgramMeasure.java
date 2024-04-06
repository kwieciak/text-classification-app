package org.example.project1.measure;

public class GeneralizedNgramMeasure {
    public GeneralizedNgramMeasure() {
    }

    public double CalculateMeasure(String word1, String word2){
        String word1prepared = word1.toLowerCase().trim();
        String word2prepared = word2.toLowerCase().trim();
        int N1 = word1.length();
        int N2 = word2.length();
        int N = Math.max(N1,N2);
        int minSimilarity = Math.min(CalculateSimilarity(word1prepared, word2prepared), CalculateSimilarity(word2prepared, word1prepared));
        return 2/(Math.pow(N,2) + N) * minSimilarity;
    }

    private int CalculateSimilarity(String word1, String word2){
        int N1 = word1.length();
        int counter = 0;
        for(int i=0; i<N1; i++){
            for(int j=0;j<N1-i; j++){
                if(word2.contains(word1.substring(j, j + i + 1))){
                    counter ++;
                }
            }
        }
        return counter;
    }

    public double CalculateMetric(String word1, String word2){
        return 1-CalculateMeasure(word1, word2);
    }
}
