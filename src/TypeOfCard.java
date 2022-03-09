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
     * associated an index to each type of card present in the file
     * */
    public static HashMap getTypeOfCard(String file) throws IOException {

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<String> cards = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] splited = line.split(" ");

                for (String s : splited) {
                    if (s.startsWith("O") || s.startsWith("M")) {

                        cards.add(s.substring(1));
                    }
                }
            }
            List<String> typeOfCard = new ArrayList<>(new HashSet<>(cards));

            HashMap map = new HashMap<>();

            for (int i = 0; i < typeOfCard.size(); i++) {
                map.put(i + 1, typeOfCard.get(i));
            }

            return map;
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getKey(HashMap<Integer, String> map, String value)
    {
        for (HashMap.Entry<Integer, String> entry: map.entrySet())
        {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
