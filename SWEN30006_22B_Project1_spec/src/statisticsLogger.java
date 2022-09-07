package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class statisticsLogger {
    public static final String STATISTICFILEPATH = "SWEN30006_22B_Project1_spec\\Statistics.txt’";
    public static final int MAXROUNDSSTORED = 100;

    private int [] roundScore = new int[MAXROUNDSSTORED];
    private HashMap<String, Integer>[] pieceCountArray = new HashMap[MAXROUNDSSTORED];
    private int currentRound = 0;

    private String previousRoundStats;

    //This method creates the text for the statistics file
    private List<String> statisticsTextToString(String difficultyLevel){
        List<String> topText = Arrays.asList("Difficulty: " + difficultyLevel, "Average score per round: " +findAverageScore());
        for(int i=1; i<currentRound; i++){
            topText.add("------------------------------------------");
            topText.add("\nRound #" + i);
            topText.add("\nScore" + roundScore[i]);
            topText.add("\nI: " + getHashValue(i,"I"));
            topText.add("\nJ: " + getHashValue(i,"J"));
            topText.add("\nK: " + getHashValue(i,"K"));
            topText.add("\nL: " + getHashValue(i,"L"));
            topText.add("\nO: " + getHashValue(i,"O"));
            topText.add("\nS: " + getHashValue(i,"S"));
            topText.add("\nT: " + getHashValue(i,"T"));
            topText.add("\nZ: " + getHashValue(i,"Z"));
            topText.add("\n+: " + getHashValue(i,"+"));
            topText.add("\nP: " + getHashValue(i,"P"));
            topText.add("\nQ: " + getHashValue(i,"Q"));
        }
        return topText;
    }


    private int getHashValue (int round, String key){
        if(pieceCountArray[round].get(key)!=null){
            return pieceCountArray[round].get(key);
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

    //This method reads the statistics text file
    public void readFile(String path){
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
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
        }
        this.roundScore[currentRound]=score;
    }

    //This method gets called every time a new piece is added/created
    public void addPieceToStat( String piece){
        int newValue = getHashValue(currentRound,piece)+1;
        pieceCountArray[currentRound].put(piece,newValue);
    }

}

