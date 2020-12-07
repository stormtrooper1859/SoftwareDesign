package program2;

import aspect.ExecutionTreeProfile;
import aspect.PerformanceProfile;

import java.util.Random;

public class Outside {

    @ExecutionTreeProfile
    @PerformanceProfile
    public void doSomething(Outside it, Inside inside) {
        Random rnd = new Random();
        int a = 0;
        int c = 0;
        while (c < 10) {
            c++;
            if (rnd.nextInt(17) == 0) it.doSomething(it, inside);
            if (rnd.nextInt(3) == 0) {
                inside.aLong();
            } else {
                inside.a();
            }
        }
    }

}
