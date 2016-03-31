
/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class Tftp {
	
	
    public static void main(String[] args) {
        if(args.length == 0){
            launchUI();
        }else{
            checkArgs(args);
        }
    }

    private static void launchUI() {
        new TftpClientUI().setVisible(true);
    }

    private static void checkArgs(String[] args) {

    }    
    
}
