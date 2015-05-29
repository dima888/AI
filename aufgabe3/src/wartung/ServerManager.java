package wartung;

public class ServerManager {
    // TODO: http://stackoverflow.com/questions/931536/how-do-i-launch-a-completely-independent-process-from-a-java-program
    // TODO: Build jars to launch them.
    static int selected = -1;

    public static void startServer() {
        // start werver with id i
        System.err.println("Please Start Server " + selected);
    }
    public static void setSelectedServer(int i){
        selected = i;
    }
    public static void killServer(int i){
        System.err.println("Please Kill Server " + i);
    }
}
