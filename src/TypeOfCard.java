import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TypeOfCard {


    /**
     * @return HashMap associated an index to each type of card present
     * */
    public static HashMap getTypeOfCard(String file) throws IOException {

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            //List of all cards
            ArrayList<String> cards = new ArrayList<>();

            while ((line = br.readLine()) != null) {

                String[] splited = line.split(" ");

                for (String s : splited) {

                    if (s.startsWith("O") || s.startsWith("M")) {

                        //add card to list
                        cards.add(s.substring(1));
                    }
                }
            }

            //remove duplicates
            List<String> typeOfCard = new ArrayList<>(new HashSet<>(cards));

            //map associated an index to each type of card present
            HashMap map = new HashMap<>();

            for (int i = 0; i < typeOfCard.size(); i++) {

                //add a card with his index
                map.put(i + 1, typeOfCard.get(i));
            }

            //return map
            return map;
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param map
     * @param card
     * @return the key corresponding to the card
     */
    public static Integer getKey(HashMap<Integer, String> map, String card)
    {
        for (HashMap.Entry<Integer, String> entry: map.entrySet())
        {
            if (card.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
