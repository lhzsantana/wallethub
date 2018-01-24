import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private final static String FILENAME = "access.log";
    private final static MySQLAccess mySQLAccess = new MySQLAccess();

    public static void main(String[] args) {

        loadToDatabase();
        parseCommand(args);
    }

    private static void parseCommand(String [] command){

        queryDatabase(getValue(command[0]), getValue(command[1]), getValue(command[2]));
    }

    private static String getValue(String arg){

        String [] parameter = arg.split("=");
        return parameter[1];
    }

    private static void loadToDatabase(){

        try(BufferedReader rd = new BufferedReader(new FileReader(FILENAME))) {

            String inputLine = null;

            while((inputLine = rd.readLine()) != null){

                String [] record = inputLine.split("|");
                mySQLAccess.insert(record[0], record[1], record[2]);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void queryDatabase(String startDate, String duration, String threshold){
        mySQLAccess.query(startDate, duration, threshold);
    }
}