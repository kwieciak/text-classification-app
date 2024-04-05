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
        int counter = 0;
        for(int i=0; i<N1; i++){
            for(int j=0;j<N1-i; j++){
                String substring = word1prepared.substring(j, j + i + 1);
                if(word2prepared.contains(substring)){
                    counter ++;
                }
            }
        }
        return 2/(Math.pow(N,2) + N) * counter;
    }
}
