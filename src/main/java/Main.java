import java.io.File;

public class Main {

        public static void main(String[] args) {

                FileManager fileManager = FileManager.getInstance();

                //Checks if the android application src code exists in the current directory
                String dir = System.getProperty("user.dir");
                File file = new File(dir+"/..");
                boolean projectExists = fileManager.checkForFile(file);

                //Sets the file to the main src code folder
                if (projectExists){
                        file = new File(System.getProperty("user.dir"));
                        file = new File(file.getAbsolutePath() + "/COMPSCI702Project/app/src/main/");
                        System.out.println(file.getAbsolutePath());

                }
                else{
                        System.out.println("Project does not exist in directory");
                        return;
                }

                fileManager.initialiseFileStack(file);
                beginObfuscation();

        }

        /**
         *Loops through the entire folder until all files have been seen
         */
        private static void beginObfuscation(){
                FileManager fileManager = FileManager.getInstance();
                DataObfuscation dataObfuscation = new DataObfuscation();
                File nextFile = fileManager.getNextFile();

                while(nextFile != null){

                        ExtensionType type = fileManager.getType(nextFile);
                        switch (type){
                                case JAVA:
                                        break;
                                case JSON:
                                        //Encrypts the json files
                                        dataObfuscation.encryptJsonFile(nextFile);
                                        break;
                                case ELSE:
                                        break;
                                default:
                                        break;
                        }
                        nextFile = fileManager.getNextFile();
                }
        }
}
