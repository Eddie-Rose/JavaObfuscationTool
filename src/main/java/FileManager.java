import java.io.File;
import java.util.Stack;

public class FileManager {
    private static FileManager fileManager = null;
    private String projectToBeObfuscated = "Compsci";
    private Stack<File> fileStack;

    private FileManager(){ }

    /**
     * Singleton method to return the one instance that exists
     * @return Returns the instance
     */
    public static FileManager getInstance(){
        if (fileManager == null){
            fileManager = new FileManager();
        }
        return fileManager;
    }

    /**
     * Prints the children of the file inputted
     * Used mainly for debugging
     * @param file
     */
    public void printDirectory(File file){
        File[] dirListing = file.listFiles();
        if (dirListing != null){
            for(File childFile : dirListing){
                System.out.println(childFile.getName());
            }
        }
    }

    /**
     * Checks for the File that is to be obfuscated
     * File name is hardcoded, only to be used for our application
     * @param file
     * @return boolean shows if the file exists
     */
    public boolean checkForFile(File file){
        File[] dirListing = file.listFiles();
        if (dirListing != null){
            for(File childFile : dirListing){
                if (childFile.getName().equals(projectToBeObfuscated)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks the file type of the file
     * @param file
     * @return Enum that describes the file type.
     */
    public ExtensionType getType(File file){
        String extension = "";

        int i = file.getAbsolutePath().lastIndexOf('.');
        if (i > 0) {
            extension = file.getAbsolutePath().substring(i+1);
        }

        if (extension.equals("java")){
            return ExtensionType.JAVA;
        }
        else if (extension.equals("json")){
            return ExtensionType.JSON;
        }
        else{
            return ExtensionType.ELSE;
        }
    }

    /**
     * Initialises the stack and fills it up with the children of the file parameter
     * @param file Top level folder
     */
    public void initialiseFileStack(File file) {
        fileStack = new Stack<File>();
        File[] dirListing = file.listFiles();
        if (dirListing != null) {
            for (File childFile : dirListing) {
                fileStack.push(childFile);
            }
        }
    }

    /**
     * Iterates through the file stack
     * If the file is a parent folder push the children onto the stack
     * @return a file that has no children
     */
    public File getNextFile(){

        //Returns null if there are no more files to iterate through
        if(fileStack.empty()){
            return null;
        }

        //Loops and fills stack until it finds a file with no children
        while(fileStack.peek().listFiles() != null) {
            File file = fileStack.pop();
            File[] dirListing = file.listFiles();

            for (File childFile : dirListing) {
                fileStack.push(childFile);
            }
        }
        return fileStack.pop();
    }
}
