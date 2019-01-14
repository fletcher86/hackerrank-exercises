import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CompareTriplets {

    // Complete the compareTriplets function below.
    static List<Integer> compareTriplets(List<Integer> a, List<Integer> b) {
    	List<Integer> rtnList = new ArrayList<>();
    	//initialize
    	rtnList.add(0, 0);
    	rtnList.add(1, 0);
    	
    	for(int i=0; i<3; i++) {
    		if(a.get(i) > b.get(i)) {
    			Integer val = rtnList.get(0);
   				rtnList.set(0, val + 1);
    		}
    		else if(a.get(i) < b.get(i)) {
    			Integer val = rtnList.get(1);
   				rtnList.set(1, val + 1);
     		}
    		else if ( a.get(i) == b.get(i)) {
    			// do nothing
    			System.out.println("");
    		}
    	}
    	
    	return rtnList;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> b = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> result = compareTriplets(a, b);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining(" "))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
