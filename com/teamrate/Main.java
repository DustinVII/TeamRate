package com.teamrate;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


            //Start of app

            Scanner sc = new Scanner(System.in);
            System.out.println("Welcome! How many members are in your group?");
            int membersAmount = sc.nextInt();

            if (membersAmount < 1 || membersAmount > 20) {
                System.err.println("Please enter a number between 1 and 20");
            } else {

                String[] member = new String[membersAmount+1];

                Scanner sc2 = new Scanner(System.in);

                //Get all team member names
                for (int i = 1; i < membersAmount+1; i++) {

                    System.out.println("What is the name of member "+i+"?");
                    member[i] = sc2.nextLine();

                }


                //Get scores
                System.out.println("Ok now let's get everyone's score. Give  a score from a scale from 1 to 10. Decimals are allowed.");

                double[][] score = new double[membersAmount+1][membersAmount+1];
                Scanner sc3 = new Scanner(System.in);
                for (int i = 1; i < membersAmount+1; i++) {

                    System.out.println("Let's rate "+member[i]);

                    for (int j = 1; j < membersAmount+1; j++) {


                        if (i!=j) {
                        System.out.println("What score does "+member[j]+" give to "+member[i]+"?");
                            score[i][j] = sc3.nextDouble();
                        }

                    }

                }


                //calculate scores
                double[] averageScore = new double[membersAmount+1];
                DecimalFormat oneDigit = new DecimalFormat("#.0");
                DecimalFormat twoDigits = new DecimalFormat("#.00");
                for (int i = 1; i < membersAmount+1; i++) {
                    for (int j = 1; j < membersAmount+1; j++) {
                        if (i!=j) {
                            averageScore[i] = averageScore[i] + score[i][j];
                        }

                    }
                    averageScore[i] = averageScore[i] / (membersAmount-1);
                    averageScore[i] = Double.parseDouble(oneDigit.format(averageScore[i]));
                }

                System.out.println("Remaining financial budget? ");
                double budget = sc.nextDouble();

                //calculate sum of all scores
                double sumOfAllScores = 0;
                for (int i = 1; i < membersAmount+1; i++) {
                    sumOfAllScores = sumOfAllScores + averageScore[i];
                }

                //print out all scores
                double salary;
                for (int i = 1; i < membersAmount+1; i++) {
                    salary = (averageScore[i] / sumOfAllScores) * budget;
                    salary = Double.parseDouble(twoDigits.format(salary));
                    System.out.println(member[i] + ": " + averageScore[i] + " - $"+salary);
                }



            } // end of else statement






    }





}