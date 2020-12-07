package program2;

import aspect.ExecutionTreeProfile;
import aspect.PerformanceProfile;

public class Inside {
    int aa = 0;

    @ExecutionTreeProfile
    @PerformanceProfile
    public void a() {
        aa++;
    }

    @ExecutionTreeProfile
    @PerformanceProfile
    public void aLong() {
        for (int i = 1; i < 200000; i++) {
            for (int j = 1; j * j < i; j++) {
                if (i % j == 0) aa++;
            }
        }
    }

}
