import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadFile {


    private List<Log>

    void readFile(String filename){

        try(BufferedReader rd = new BufferedReader(new FileReader(filename))) {

            String inputLine = null;

            while((inputLine = rd.readLine()) != null){

                String [] record = inputLine.split("|");

                Log log = new Log();
                log.setIp(record[0]);
                log.setRequest(record[1]);
                log.setTime(record[2]);


                mySQLAccess.insert(record[0], record[1], record[2]);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
