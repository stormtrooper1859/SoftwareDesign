package aspect;

import java.util.Arrays;

public class CallTreeLogger {
    private static final CallTreeLogger INSTANCE = new CallTreeLogger();
    private StringBuilder sb = new StringBuilder();
    private int depth = 0;

    public static CallTreeLogger getInstance() {
        return INSTANCE;
    }

    public void enterMethod(String methodName, Object[] args) {
        for (int i = 0; i < depth - 1; i++) {
            sb.append("| ");
        }
        if (depth != 0) {
            sb.append("└ ");
        }
        sb.append(methodName).append(" (").append(Arrays.toString(args)).append(")");

        sb.append("\n");
        depth++;
    }

    private void printData() {
        System.out.println(sb);
    }

    private void cleanData() {
        sb = new StringBuilder();
        depth = 0;
    }

    private void pullData() {
        printData();
        cleanData();
    }

    public void leaveMethod() {
        depth--;

        if (depth == 0) {
            pullData();
        }
    }
}
