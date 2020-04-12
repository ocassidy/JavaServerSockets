package com.concurrent.shared;

import java.io.*;
import java.util.*;

public class LongestSequence {
    public List<Integer> getInput(BufferedReader input, PrintWriter output) throws IOException {
        List<Integer> list = new ArrayList<>();
        String inputLine;

        while ((inputLine = input.readLine()) != null && !(inputLine.equalsIgnoreCase("finish"))) {
            int number = 0;

            try {
                //parse int out of string value
                number = Integer.parseInt(inputLine);
                //print back to user
                output.println(number);
                output.flush();
            } catch (NumberFormatException e) {
                //catch any input other than a valid integer
                output.println("not a number or valid integer, please try again");
                output.flush();
            }
            //add number to list
            list.add(number);
            //remove element if equal to 0
            list.removeIf(num -> num == 0);
        }
        return list;
    }

    public Set<Integer> longestConsecutive(List<Integer> numList, PrintWriter output) {
        int startPos = 0;
        int endPos = 0;
        int temp = 0;
        for (int i = 0; i < numList.size(); i++) {
            if (i == 0 || numList.get(i) < numList.get(i - 1)) {
                temp = i;
            } else if (i - temp > endPos - startPos) {
                startPos = temp;
                endPos = i;
            }
        }
        return new LinkedHashSet<>(numList.subList(startPos, endPos + 1));
    }
}