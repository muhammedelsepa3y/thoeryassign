import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputFilePathPDA = "input_pda.txt";
        String inputFilePathCFG = "input_cfg.txt";
        String outputFilePathPDA = "output_pda.txt";
        String outputFilePathCFG = "output_cfg.txt";

        Map<Integer, List<String>> inputsPDA = processInputFile(inputFilePathPDA);
       // Map<Integer, List<String>> inputsCFG = processInputFile(inputFilePathCFG);
        Map<Integer, List<String>> resultsPDA = validateInputsPDA(inputsPDA);
       // Map<Integer, List<String>> resultsCFG = validateInputsCFG(inputsCFG);
        writeOutputFile(outputFilePathPDA, resultsPDA);
      //  writeOutputFile(outputFilePathCFG, resultsCFG);
    }

    private static Map<Integer, List<String>> processInputFile(String inputFile) {
        Map<Integer, List<String>> inputs = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            int currentProblem = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.matches("\\d+")) {
                    currentProblem = Integer.parseInt(line);
                    inputs.putIfAbsent(currentProblem, new ArrayList<>());
                } else if (!line.equals("end")) {
                    inputs.get(currentProblem).add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return inputs;
    }

    private static Map<Integer, List<String>> validateInputsPDA(Map<Integer, List<String>> inputs) {
        Map<Integer, List<String>> results = new HashMap<>();
        for (Map.Entry<Integer, List<String>> entry : inputs.entrySet()) {
            int problemNumber = entry.getKey();
            List<String> inputStrings = entry.getValue();
            List<String> outputStrings = new ArrayList<>();

            for (String input : inputStrings) {
                boolean accepted = false;
                switch (problemNumber) {
                    case 1:
                        accepted = validatePDA1(input);
                        break;
                    case 2:
                        accepted = validatePDA2(input);
                        break;
                    case 3:
                        accepted = validatePDA3(input);
                        break;
                    case 4:
                        accepted = validatePDA4(input);
                        break;
                }
                outputStrings.add(accepted ? "accepted" : "not accepted");
            }
            outputStrings.add("end");
            results.put(problemNumber, outputStrings);
        }
        return results;
    }

    private static Map<Integer, List<String>> validateInputsCFG(Map<Integer, List<String>> inputs) {
        Map<Integer, List<String>> results = new HashMap<>();
        for (Map.Entry<Integer, List<String>> entry : inputs.entrySet()) {
            int problemNumber = entry.getKey();
            List<String> inputStrings = entry.getValue();
            List<String> outputStrings = new ArrayList<>();

            for (String input : inputStrings) {
                boolean accepted = false;
                switch (problemNumber) {
                    case 1:
                        accepted = validatePDA1(input);
                        break;
                    case 2:
                        accepted = validatePDA2(input);
                        break;
                    case 3:
                        accepted = validatePDA3(input);
                        break;
                    case 4:
                        accepted = validatePDA4(input);
                        break;
                }
                outputStrings.add(accepted ? "accepted" : "not accepted");
            }
            outputStrings.add("end");
            results.put(problemNumber, outputStrings);
        }
        return results;
    }

    private static boolean validatePDA1(String input) {
        int aCount = 0, bCount = 0;
        boolean bStarted = false;

        for (char c : input.toCharArray()) {
            if (c == 'a'&&!bStarted) aCount++;
            else if (c == 'b') {
                bStarted = true;
                bCount++;
            } else return false;

        }
        return aCount == bCount;
    }

    private static boolean validatePDA2(String input) {
        int aCount = 0, bCount = 0;
        boolean bStarted = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == 'a' && !bStarted) {
                aCount++;
            } else if (c == 'b') {
                bStarted = true;
                bCount++;
            } else {
                return false;
            }
        }
        return (aCount == 2 * bCount / 3) && (bCount % 3 == 0) && (aCount % 2 == 0);
    }


    private static boolean validatePDA3(String input) {
        Stack<Character> stack = new Stack<>();
        for (char c : input.toCharArray()) {
            if (c == '{') stack.push(c);
            else if (c == '}') {
                if (stack.isEmpty() || stack.pop() != '{') return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean validatePDA4(String input) {
        int aCount = 0, bCount = 0, cCount = 0;
        int phase = 0;
        for (char c : input.toCharArray()) {
            if (c == 'a' && phase == 0) aCount++;
            else if (c == 'b' && (phase == 0 || phase == 1)) {
                phase = 1;
                bCount++;
            } else if (c == 'c' && (phase == 1 || phase == 2)) {
                phase = 2;
                cCount++;
            } else return false;
        }
        return aCount == bCount + cCount && bCount >= 1 && cCount >= 1;
    }

    private static void writeOutputFile(String outputFile, Map<Integer, List<String>> results) {
        try (PrintWriter writer = new PrintWriter(new File(outputFile))) {
            for (Map.Entry<Integer, List<String>> entry : results.entrySet()) {
                writer.println(entry.getKey());
                for (String result : entry.getValue()) {
                    writer.println(result);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
}
