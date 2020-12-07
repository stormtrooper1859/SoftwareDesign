package aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PerformanceLogger {
    private static final PerformanceLogger INSTANCE = new PerformanceLogger();
    private Map<String, ArrayList<Long>> loggerMap = new HashMap<>();
    private boolean locked = false;

    public static PerformanceLogger getInstance() {
        return INSTANCE;
    }

    private String getTextOutput() {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, ArrayList<Long>> entry : loggerMap.entrySet()) {
            String key = entry.getKey();
            ArrayList<Long> list = entry.getValue();
            int calledTimes = list.size();
            double totalExecutedTimeMCS = list.stream().reduce(0L, Long::sum) / 1000.0;

            result.append(key).append(":\n");
            result.append("\tcalled times: ").append(calledTimes).append("\n");
            result.append("\tavg executed time: ").append(String.format("%.3f", totalExecutedTimeMCS / calledTimes)).append(" mcs\n");
            result.append("\ttotal executed time: ").append(String.format("%.3f", totalExecutedTimeMCS)).append(" mcs\n");
        }

        return result.toString();
    }

    private void cleanStorage() {
        loggerMap = new HashMap<>();
        locked = false;
    }

    public boolean isFirstAndLock() {
        boolean result = loggerMap.isEmpty() && !locked;
        if (result) locked = true;
        return result;
    }

    public void writeRecord(String methodName, long executionTime) {
        if (!loggerMap.containsKey(methodName)) {
            loggerMap.put(methodName, new ArrayList<>());
        }
        loggerMap.get(methodName).add(executionTime);
    }

    public String pullLog() {
        String result = getTextOutput();

        cleanStorage();

        return result;
    }
}
