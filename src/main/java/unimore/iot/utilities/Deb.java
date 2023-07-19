package unimore.iot.utilities;

public class Deb {
    public static String debHelp() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length >= 3) {
            int traceIndex = 2;

            //String className = stackTrace[traceIndex].getClassName();
            String methodName = stackTrace[traceIndex].getMethodName();
            String fileName = stackTrace[traceIndex].getFileName();
            int lineNumber = stackTrace[traceIndex].getLineNumber();

            assert fileName != null;
            return "[" + fileName.replace(".java", "") + "." + methodName + "() - row " + lineNumber + "]\t\t\t";
        }

        return "[Utilis.debHelp()] --> ERROR\n";
    }

    public static void ShowTitle(String title) {
        System.out.println("\n#############################");
        System.out.println(centerText(title, 27));
        System.out.println("#############################");
    }

    public static String retShowTitle(String title) {
        StringBuilder sb = new StringBuilder();

        int separatorLength = 25;
        char separatorChar = '#';

        sb.append("\n");
        sb.append(String.valueOf(separatorChar).repeat(separatorLength));
        sb.append("\n");

        sb.append(centerText(title, separatorLength));

        sb.append("\n");
        sb.append(String.valueOf(separatorChar).repeat(separatorLength));
        sb.append("\n");

        return sb.toString();
    }

    public static String ShowTitleBig(String title) {
        StringBuilder sb = new StringBuilder();

        int separatorLength = 50;
        char separatorChar = '-';

        sb.append("\n");
        sb.append(String.valueOf(separatorChar).repeat(separatorLength));
        sb.append("\n");

        sb.append(centerText(title, separatorLength));

        sb.append("\n");
        sb.append(String.valueOf(separatorChar).repeat(separatorLength));

        return sb.toString();
    }

    public static String centerText(String text, int width) {
        int padding = width - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return String.format("%" + leftPadding + "s%s%" + rightPadding + "s", "", text, "");
    }
}
