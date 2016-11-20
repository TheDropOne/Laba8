import java.io.*;
import java.util.*;

/**
 * Created by Semen on 20-Nov-16.
 */
public class Runner {

    private static final String INPUT_FILE_NAME = "juice.in";
    private static final String OUTPUT1_FILE_NAME = "juice1.out";
    private static final String OUTPUT2_FILE_NAME = "juice2.out";
    private static final String OUTPUT3_FILE_NAME = "juice3.out";

    public static void main(String[] args) {
        List<String> listOfStrings = readListOfStrings(INPUT_FILE_NAME);
        List<Juice> juices = new ArrayList<>();
        for (String s : listOfStrings) {
            s = s.trim();
            juices.add(new Juice(Arrays.asList(s.split(" "))));
        }
        // TASK 1
        List<String> components = new ArrayList<>();
        for (Juice juice : juices) {
            components.addAll(juice.getIngredients());
        }
        Set<String> componentsSet = new LinkedHashSet<>();
        componentsSet.addAll(components);
        writeToFile(OUTPUT1_FILE_NAME, componentsSet.iterator());
        //TASK 2
        Collections.sort(components, (o1, o2) -> {
            if (o1.length() < o2.length()) return -1;
            if (o1.length() > o2.length()) return 1;
            return o1.compareTo(o2);
        });
        writeToFile(OUTPUT2_FILE_NAME,components.iterator());

    }

    private static List<String> readListOfStrings(String path) {
        BufferedReader br = null;
        List<String> listOfStrings = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(new File(path)));
            String tempString = br.readLine();
            while (tempString != null) {
                listOfStrings.add(tempString);
                tempString = br.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Reading from file successfully failed. IOException");
        } finally {
            try {
                br.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Stream didn't closed or not exists. Reading from file failed. ");
            }
        }
        return listOfStrings;
    }

    private static void writeToFile(String filename, Iterator<?> iterator) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(filename));
            while (iterator.hasNext()) {
                fw.write((String) iterator.next());
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Stream didn't closed or not exists. Reading from file failed. ");
            }
        }
    }

}
