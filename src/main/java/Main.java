import java.io.IOException;

/**
 * Created by amid on 27.11.14.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        double R2 = RSquaredStatistics.bestR2();
        GraphDrawing.params = Combinations.getCombinations().get(RSquaredStatistics.bestIndex);

        System.out.println( "By using the x parameters: ");
        for (int j = 0; j < GraphDrawing.params.length; j++) {
            System.out.println("X" + GraphDrawing.params[j] + " ");
        }
        System.out.println("We got the best adjusted R-squared statistic: " + R2);

        GraphDrawing.draw();
    }
}
