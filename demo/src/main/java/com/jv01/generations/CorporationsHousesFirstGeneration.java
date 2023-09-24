package com.jv01.generations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CorporationsHousesFirstGeneration {

    private long seed;
    private int numChunks;
    private int radius;
    private Random random;

    List<long[]> chunks;

    public CorporationsHousesFirstGeneration(String seed, int numChunks, int radius) {
        this.seed = seed.hashCode();
        this.numChunks = numChunks;
        this.radius = radius;

        while((((this.radius*2+1)*(this.radius*2+1))-1)<this.numChunks){
            this.radius++;
        }

        this.random = new Random(this.seed);

        chunks = generateChunks();
    }

    public List<long[]> generateChunks() {
        int minDistance = radius * (-1);
        int maxDistance = radius;

        List<long[]> chunkList = new ArrayList<>();

        for (int i = 0; i < numChunks; i++) {
            long[] chunk = generateChunk(minDistance, maxDistance);
            if(verif(chunkList,chunk)){
                chunkList.add(chunk);
            }else{
                numChunks++;
            }       
        }

        return chunkList;
    }

    private boolean verif(List<long[]> chunks, long[] chunk){
        boolean verifBool = true;
        if(chunk[0]==0 && chunk[1]==0)verifBool=false;
        for(long[] c : chunks){
            if(c[0]==chunk[0] && c[1]==chunk[1])verifBool=false;         
        }
        return verifBool;
    }

    private long[] generateChunk(int minDistance, int maxDistance) {
        long x = random.nextInt(maxDistance - minDistance + 1) + minDistance;
        long y = random.nextInt(maxDistance - minDistance + 1) + minDistance;

        return new long[]{x, y};
    }

    public static void main(String[] args) {
        String seed = "fzehfis0uhfef1jeng64kjenj54445";
        int numChunks = 10;
        int radius = 50;

        CorporationsHousesFirstGeneration generator = new CorporationsHousesFirstGeneration(seed,numChunks,radius);

        for (long[] chunk : generator.chunks) {
            System.out.println("Chunk: [" + chunk[0] + ", " + chunk[1] + "]");
        }
    }
}