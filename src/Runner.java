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
        (new Thread(() -> {
            Collections.sort(components, (o1, o2) -> {
                if (o1.length() < o2.length()) return -1;
                if (o1.length() > o2.length()) return 1;
                return o1.compareTo(o2);
            });
        })).run();
        writeToFile(OUTPUT2_FILE_NAME, components.iterator());
        //TASK 3
        int minCount = 0;
        (new Thread(() -> {
            Collections.sort(juices, (juice1, juice2) -> {
                Collections.sort(juice1.getIngredients());
                Collections.sort(juice2.getIngredients());
                if (juice1.getIngredients().get(0).charAt(0) < juice2.getIngredients().get(0).charAt(0)) return -1;
                if (juice1.getIngredients().get(0).charAt(0) > juice2.getIngredients().get(0).charAt(0)) return 1;
                return juice2.getIngredients().get(0).compareTo(juice2.getIngredients().get(0));
            });
        })).run();
        for (int i = 0; i < juices.size() - 1; i++) {
            boolean containsIngredients = false;
            for (String component : juices.get(i).getIngredients()) {
                if (juices.get(i + 1).getIngredients().contains(component)) {
                    containsIngredients = true;
                }
            }
            if (!containsIngredients) minCount++;
        }
        minCount++;
        writeToFile(OUTPUT3_FILE_NAME, String.valueOf(minCount));
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

    private static void writeToFile(String filename, String... s) {
        FileWriter fw = null;
        int iterator = 0;
        try {
            fw = new FileWriter(new File(filename));
            while (s.length != iterator) {
                fw.write(s[iterator]);
                fw.write(System.lineSeparator());
                iterator++;
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
