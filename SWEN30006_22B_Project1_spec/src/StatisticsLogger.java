package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StatisticsLogger {
    public static final String STATISTICFILEPATH = "SWEN30006_22B_Project1_spec\\Statistics.txt";
    public static final int MAXROUNDSSTORED = 100;

    private int [] roundScore = new int[MAXROUNDSSTORED];
    private List<HashMap<String, Integer>> pieceCountArray = new ArrayList<>();
    private int currentRound = 0;

    //This method creates the text for the statistics file
    private List<String> statisticsTextToString(String difficultyLevel){
        ArrayList<String> topText = new ArrayList<>();
        topText.add("Difficulty: " + difficultyLevel);
        topText.add("Average score per round: " +findAverageScore());
        for(int i=1; i<=currentRound; i++){
            topText.add("------------------------------------------");
            topText.add("Round #" + i);
            topText.add("Score " + roundScore[i]);
            topText.add("I: " + getHashValue(i,"I"));
            topText.add("J: " + getHashValue(i,"J"));
            topText.add("K: " + getHashValue(i,"K"));
            topText.add("L: " + getHashValue(i,"L"));
            topText.add("O: " + getHashValue(i,"O"));
            topText.add("S: " + getHashValue(i,"S"));
            topText.add("T: " + getHashValue(i,"T"));
            topText.add("Z: " + getHashValue(i,"Z"));
            topText.add("+: " + getHashValue(i,"+"));
            topText.add("P: " + getHashValue(i,"P"));
            topText.add("Q: " + getHashValue(i,"Q"));
        }
        return topText;
    }

    private int getHashValue (int round, String key){
        if(!pieceCountArray.get(round-1).isEmpty() && pieceCountArray.get(round-1).get(key) != null){
            return pieceCountArray.get(round-1).get(key);
        }
        return 0;
    }

    //This method writes to the statistics text file
    public void writeToFile(String difficultyLevel){
        Path file = Paths.get(STATISTICFILEPATH);
        try {
            Files.write(file, statisticsTextToString(difficultyLevel), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method calculates the average score per round
    private int findAverageScore(){
        if(currentRound!=0){
            int max = 0;
            for (int i = 1; i<=currentRound; i++){
                max = max + roundScore[i];
            }
            return max/currentRound;
        }
        else return 0;
    }

    //This method gets called every time the score is updated
    public void updateRoundScore(int round, int score){
        if(score == 0) {
            this.currentRound = round;
            pieceCountArray.add(new HashMap<>());
        }
        this.roundScore[currentRound]=score;
    }

    //This method gets called every time a new piece is added/created
    public void addPieceToStat(String piece){
        int newValue = getHashValue(currentRound,piece)+1;
        pieceCountArray.get(currentRound-1).put(piece,newValue);
    }

}

