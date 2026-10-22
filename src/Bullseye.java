/*
Name: Connor Cash
Date: 12-6-24
Class: CSC 1120
PLEDGE: I have neither given nor received unauthorized aid on this program.
Description: Bullseye is a program that takes in a text file that represents
a game of darts played among two players. The text file gives the coordinates in
a 2-dimensional plane of 6 throws from player one and 6 throws from player 2.
Input: None
Output: The score of the games from the text file that represents the dart games played
by player one and player two
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * A try catch block is created to handle a file not found exception. Inside
 * the try block a scanner object is created to read the text file containing
 * the games of darts. Then a loop is used to create string representing only
 * the coordinates of player1's throws and another loop is used to create a
 * string of only the coordinates of player2's throws. Then helper methods
 * are used to initialize strings of each players coordinates for their x coordinates
 * and then their yCoordinates. Then getCoordinatesList is used to get lists of each player's
 * coordinates. Then each player's score is calculates using calculateTotalScore method. Then
 * the printWinner method is called with each player's scores
 */
public class Bullseye {
    public static void main(String[] args){
        try{
            Scanner reader = new Scanner(new File("TestGame.txt"));
            while(reader.hasNext()){
                String data = reader.nextLine();
                String player1Coordinates = "";
                String player2Coordinates = "";
                int spaceCount = 0;
                int i = 0;
                while(spaceCount < 6){
                    player1Coordinates += data.charAt(i);
                    if(data.charAt(i) == ' ')
                        spaceCount++;
                    i++;
                }
                for(int j = i; j < data.length(); j++){
                    player2Coordinates += data.charAt(j);
                }

                String player1XSCoordinatesString = getCoordinatesString(player1Coordinates)[0];
                String player1YCoordinatesString = getCoordinatesString(player1Coordinates)[1];

                String player2XCoordinatesString = getCoordinatesString(player2Coordinates)[0];
                String player2YCoordinatesString = getCoordinatesString(player2Coordinates)[1];

                ArrayList<Double> player1XScores = getCoordinatesList(player1XSCoordinatesString);
                ArrayList<Double> player1YScores = getCoordinatesList(player1YCoordinatesString);

                ArrayList<Double> player2XScores = getCoordinatesList(player2XCoordinatesString);
                ArrayList<Double> player2YScores = getCoordinatesList(player2YCoordinatesString);

                int player1Score = calculateTotalScore(player1XScores, player1YScores);
                int player2Score = calculateTotalScore(player2XScores, player2YScores);

                printWinner(player1Score, player2Score);


            }

        } catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }

    /**
     * Function takes in player1's and player2's score
     * If player1 has a higher score then a statement is printed that player1 wins
     * else If player2 has a higher score then a statement is printed that player2 wins
     * else a tie happened and player2 and player1 have an equal score, so a statement
     * is printed that a tie happened
     * @param score1
     * @param score2
     */
    public static void printWinner(int score1, int score2){
        if(score1 > score2)
            System.out.println("SCORE: " + score1 + " TO " + score2 + ", PLAYER 1 WINS.");
        else if(score2 > score1)
            System.out.println("SCORE: " + score1 + " TO " + score2 + ", PLAYER 2 WINS.");
        else
            System.out.println("SCORE: " + score1 + " TO " + score2 + ", TIE.");

    }

    /**
     * Function initializes a running sum and loops through the number of coordinates
     * In loop, the running sum (total score) is added to by calling the score method
     * which calculates the score for a given xCoordinate and yCoordinate for a throw
     * @param xCoords, list that contains the xCoordinates of a player's dart throws for a particular game
     * @param yCoords, list that contains the yCoordinates of a player's dart throws for a particular game
     * @return totalScore for a game for a player, running sum
     */
    public static int calculateTotalScore(ArrayList<Double> xCoords, ArrayList<Double> yCoords){
        int totalScore = 0;
        for(int i = 0; i < xCoords.size(); i++){
            totalScore += score(xCoords.get(i), yCoords.get(i));
        }
        return totalScore;
    }

    /**
     * Function uses string concatenation on two empty strings and loops through
     * the string representation of the coordinates of a players throws and if
     * it is an x coordinate concatenates to the xScore string, else concatenates
     * to the yScore string. Then an array of length 2 is initialized outside the loop
     * and the first element is assigned the xScore and the second element is assigned
     * they yScore.
     * @param f, A string that represents all of a player's dart throws in a game
     * @return a string array where the first element is a string of all the xCoordinates of a
     * players game, and the second element is a string of all the yCoordinates of a
     * players game
     * EX: coordinates[0] = "9 0 -2" for xCoordinates
     * EX: coordinates[1] = "0 3.5 -2" for yCoordinates
     */
    public static String[] getCoordinatesString(String f){
        String xScore = "";
        String yScore = "";
        int k = 0;
        int xyValue = 0;
        f += " ";
        while(k < f.length()){
            while(f.charAt(k) != ' '){
                if(xyValue % 2 == 0) {
                    xScore += f.charAt(k);
                }
                else
                    yScore += f.charAt(k);
                k++;
            }
            if(xyValue % 2 == 0)
                xScore += " ";
            else
                yScore += " ";

            k++;
            xyValue++;
        }
        String[] coordinates = new String[2];
        coordinates[0] = xScore;
        coordinates[1] = yScore;

        return coordinates;
    }

    /**
     * Function creates a coordinate variable to hold an individual coordinate, and a list
     * to hold all the coordinates. The string is iterated over in a for loop. Inside the for loop body
     * a while loop is used to find the index of a space value. An individual coordinate is then given
     * by the substring of the iterator variable of the for loop to the index variable of the space
     * value. The coordinate is then added to the coordinates list using parseDouble method of the
     * class double.
     * @param f, that represents all the xCoordinates or yCoordinates of a player's game
     * @return a list of Doubles that contain the coordinates of a player's game
     */
    public static ArrayList<Double> getCoordinatesList(String f){
        int t = 0;
        String coordinate = "";
        ArrayList<Double> coordinates = new ArrayList<>();
        for(int j = 0; j < f.length(); j++){
            while(f.charAt(t) != ' '){
                t++;
            }
            coordinate = f.substring(j,t);
            if(!coordinate.isEmpty())
                coordinates.add(Double.parseDouble(coordinate));
            j=t;
            t++;
        }
        return coordinates;
    }

    /**
     * Function: calculates the distance from the origin for a throw
     * using r^2 = x^2 + y^2, r = sqrt(x^2 + y^2) and returns the proper
     * score of the throw using if statements
     * @param x, x Coordinate for a throw
     * @param y, y Coordinate for a throw
     * @return score for a throw
     */
    public static int score(double x, double y){
        double distance = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

        if(distance <= 3)
            return 100;
        if(distance <= 6)
            return 80;
        if(distance <= 9)
            return 60;
        if(distance <= 12)
            return 40;
        if(distance <= 15)
            return 20;

        return 0;
    }

}
