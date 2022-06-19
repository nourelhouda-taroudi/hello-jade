package devoir.ensa;

public class Main {

    public static void main(String[] args) {
        jade.Boot.main(new String[]{
                "-host","127.0.0.1",
                "-container",
                "agent1:ma.communication.Agent1;"+"agent2:ma.communication.Agent2"});
    }
}


