package Backend.IO;

import java.io.*;
import java.util.List;


/* NEXT: MULTITHREAD THIS CLASS METHODS */

public class IOController {

    // private NetworkController networkController;
    private String filename = null;
    private File file = null;
    private FileReader infile = null;
    private FileWriter outfile = null;

    /* =================================================================
                                   Constructor
    ================================================================= */
    public IOController(){

    }

    public IOController(String filename) throws IOException {
        loadFile(filename);
    }

    /* =================================================================
                                     Getter
    ================================================================= */
    public String getFilename(){
        return filename;
    }

    public File getFile(){
        return file;
    }

    public FileReader getFileReader(){
        return infile;
    }

    public FileWriter getFileWriter(){
        return outfile;
    }

    /* =================================================================
                                     Setter
    ================================================================= */
    public void setFilename(String filename){
        this.filename = filename;
    }

    /* =================================================================
                                 MenuBar Methods
    ================================================================= */
    // Create new file from user's filename input
    // If the file is not exists, create the new one then open it
    // If file exists, function won't open the file (User need to use the "open" Menu Item)
    public void createNewFile(String filename) throws IOException {
        // Store Loaded file's name
        String str_backup = getFilename();

        // Load new file
        loadFile(filename);

        // If file not exists, create the file
        if(!file.exists()){
            try{
                file.createNewFile();

                // SEND FILE'S CREATION INFO TO ANOTHER COMPUTER

                System.out.println(filename + " successfully created");
            }
            catch (IOException e){
                loadFile(str_backup);
                System.out.println("ERROR:" + e.getMessage());
                e.printStackTrace();
            }
        }
        // If file is not exists, load the latest file back
        else {
            loadFile(str_backup);
            System.out.println(filename + " already exists!");
        }

    }

    // Open created file
    public void openFile(String filename) throws IOException {
        loadFile(filename);
    }


    // Send file
    public void sendFileInformation(){
        String command = "";
        command += "filename: " + getFilename() + " , ";
        command += "content: ";

        /*
        for(int i = 0; i < networkController.getConnectedDevices().size(); i++){
            // sendCommand(networkController.getConnectedDevices().get(i), command);
        }
        */

        // kaeritai
    }

    /* =================================================================
                                  Private Methods
    ================================================================= */
    private void loadFile(String filename) throws IOException {
        try {
            file = new File(filename);
            infile = new FileReader(file);
            outfile = new FileWriter(file);
            setFilename(filename);
            System.out.println(filename + " Successfully loaded");
        }
        catch(IOException e){
            System.out.println("ERROR:" + e.getMessage());
            e.printStackTrace();
        }
    }

}
