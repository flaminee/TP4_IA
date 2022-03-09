import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Main {

    public static <Int> void main(String[] args) throws InterruptedException, IOException {
        String file = "all_absolute-.txt";

        HashMap typeOfCard = TypeOfCard.getTypeOfCard(file);

        String inputfile = makeInputFile(file, typeOfCard);

        String outputfile = "outputFile.txt";

        ProcessBuilder processBuilder = new ProcessBuilder(
                "powershell.exe",  "java", "-jar", "spmf.jar run LCM " + inputfile + " " + outputfile + " 7%"
        );
        Process precess = processBuilder.start();
        precess.waitFor();

        readOutputFile(outputfile, typeOfCard);

    }

    public static void readOutputFile(String file, HashMap typeOfCard){
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            PrintWriter writer = new PrintWriter("readOutputFile.txt", "UTF-8");

            while ((line = br.readLine()) != null) {

                String[] splited = line.split(" ");


                if(splited.length > 5){

                    writer.println("Nombre d'occurence de cette compo : " + splited[splited.length - 1 ]);
                    for(int i = 0; i < splited.length - 2; i ++){

                        writer.print(typeOfCard.get( Integer.parseInt(splited[i])  ) + " ");

                    }
                    writer.println("\n");

                }
            }

            writer.close();

        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }



    public static String makeInputFile(String file, HashMap typeOfCard){

        String intputFile = "intputFile.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            HashMap<Integer, ArrayList<Integer>> items = new HashMap<>();


            while ((line = br.readLine()) != null) {

                String[] splited = line.split(" ");

                if (splited[1].startsWith("O")) {

                    items.get(Integer.parseInt(splited[0]) * 2).add(TypeOfCard.getKey(typeOfCard, splited[1].substring(1)));
                } else if (splited[1].startsWith("M")) {
                    items.get(Integer.parseInt(splited[0]) * 2 + 1).add(TypeOfCard.getKey(typeOfCard, splited[1].substring(1)));

                } else {
                    items.put(Integer.parseInt(splited[0]) * 2 + 1, new ArrayList<>());
                    items.put(Integer.parseInt(splited[0]) * 2, new ArrayList<>());

                }

            }

            PrintWriter writer = new PrintWriter(intputFile, "UTF-8");

            for (int i = 0; i < items.keySet().size(); i++) {

                ArrayList<Integer> l = items.get(i);
                Collections.sort(l);

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
}
