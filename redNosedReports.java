import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class redNosedReports {
    public static void main(String[] args) {
        HashMap<Integer, int[]> numbersList = getNumbers("numbers1.txt"); // open the file containing the puzzle input
        int nb_safes = 0;
        int nb_safes_before_removes = 0;
        boolean safe_after_remove = false;
        for (Map.Entry<Integer, int[]> entry : numbersList.entrySet()) {
            int key = entry.getKey();
            int[] value = entry.getValue();
            if (isSafe(value)) {
                nb_safes++;
                nb_safes_before_removes++;
                System.out.println("level " + key + ": Safe");
            } 
            else {
                safe_after_remove = false;
                for (int i = 0; i < value.length; i++) {
                    int[] newList = new int[value.length - 1];
                    for (int j = 0, k = 0; j < value.length; j++) {
                        if (j == i)
                            continue;
                        newList[k++] = value[j];
                    }
                    if (isSafe(newList)) {
                        nb_safes++;
                        safe_after_remove = true;
                        System.out.println("level " + key + ": Safe after remove");
                        break;
                    }
                }
                if (!safe_after_remove) {
                    System.out.println("level " + key + ": Unsafe");
                }
            }
        }
        System.out.println("Number of safe levels after removes :" + nb_safes);
        System.out.println("Number of safe levels before removes :" + nb_safes_before_removes);
    }

    public static HashMap<Integer, int[]> getNumbers(String file) {
        
        HashMap<Integer, int[]> numbersList = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) // open the text file,and wrap it in a BufferedReader so we can read it efficiently line by line
        {
            String line;
            int line_number = 1;

            while ((line = br.readLine()) != null) {
                String[] s_numbers = line.trim().split("\\s+"); // cleans the line (delete before and after text whitespaces with trim) and splits it into parts based on whitespace with split
                int[] numbers = Arrays.stream(s_numbers).mapToInt(Integer::parseInt).toArray(); // converts the parts to integers
                numbersList.put(line_number, numbers);
                line_number++;
            }

        } catch (FileNotFoundException e) {
            System.err.println("File Not Found : " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbersList;
    }

    public static boolean isSafe(int[] list) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < list.length - 1; i++) {
            if (Math.abs(list[i] - list[i + 1]) > 0 && Math.abs(list[i] - list[i + 1]) < 4) {
                result.add(list[i] - list[i + 1]);
            }
        }
        boolean allPositive = result.stream().allMatch(x -> x > 0);
        boolean allNegative = result.stream().allMatch(x -> x < 0);

        return (allPositive || allNegative) && result.size() == list.length - 1;

    }
}
