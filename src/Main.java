import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Main {

    public static <Int> void main(String[] args) throws InterruptedException, IOException {
        String file = "all_absolute-.txt";

        //HashMap associated an index to each type of card present
        HashMap typeOfCard = TypeOfCard.getTypeOfCard(file);

        //make input file for LCM algorithm
        String inputfile = makeInputFile(file, typeOfCard);

        //make output file file for LCM algorithm
        String outputfile = "outputFile.txt";

        //execute LCM algorithm
        ProcessBuilder processBuilder = new ProcessBuilder(
                "powershell.exe",  "java", "-jar", "spmf.jar run LCM " + inputfile + " " + outputfile + " 7%"
        );
        Process precess = processBuilder.start();
        precess.waitFor();

        //make the output file of the LCM algorithm readable
        readableOutputFile(outputfile, typeOfCard);

    }

    /**
     * make input file for LCM algorithm
     * @param file name who contain the dataset we want to transform
     * @param typeOfCard
     * @return  file name with format compatible with LCM algorithm
     */
    public static String makeInputFile(String file, HashMap typeOfCard){

        String intputFile = "intputFile.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            //map with a game index and a list of cards played
            HashMap<Integer, ArrayList<Integer>> items = new HashMap<>();

            while ((line = br.readLine()) != null) {

                String[] splited = line.split(" ");

                if (splited[1].startsWith("O")) {

                    //we add the card that the player has just played to the list of the current game
                    items.get(Integer.parseInt(splited[0]) * 2).add(TypeOfCard.getKey(typeOfCard, splited[1].substring(1)));

                } else if (splited[1].startsWith("M")) {

                    //idem
                    items.get(Integer.parseInt(splited[0]) * 2 + 1).add(TypeOfCard.getKey(typeOfCard, splited[1].substring(1)));

                } else {
                    //start a new game
                    //create a new list of cards to play for each player
                    items.put(Integer.parseInt(splited[0]) * 2 + 1, new ArrayList<>());
                    items.put(Integer.parseInt(splited[0]) * 2, new ArrayList<>());

                }
            }

            //create the input file
            PrintWriter writer = new PrintWriter(intputFile, "UTF-8");

            //for each game
            for (int i = 0; i < items.keySet().size(); i++) {

                //sort the list of playing cards
                ArrayList<Integer> l = items.get(i);
                Collections.sort(l);

                //write the list of cards played on the same line
                for (Integer item : l) {
                    writer.print(item + " ");
                }
                writer.println();
            }
            writer.close();

            return intputFile;
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }

    }


    /**
     * make the Output file readable by replacing the numbers with their name
     * Writing is done in the file readableOutputFile.txt
     * @param file
     * @param typeOfCard
     */
    public static void readableOutputFile(String file, HashMap typeOfCard){
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            PrintWriter writer = new PrintWriter("readableOutputFile.txt", "UTF-8");

            while ((line = br.readLine()) != null) {

                String[] splited = line.split(" ");

                for(int i = 0; i < splited.length - 2; i ++){

                    //for each key we write the name of the associated card
                    writer.print(typeOfCard.get( Integer.parseInt(splited[i])  ) + " ");

                }
                writer.print(" #SUP: : " + splited[splited.length - 1 ]);
                writer.println("\n");

            }

            writer.close();

        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }
}
