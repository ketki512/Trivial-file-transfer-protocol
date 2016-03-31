


/**
 * 
 * The Client Return codes which are mentioned in the RFC- 1350
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class TftpClientReturnCode {
	
	/**
	 * The code to say that result is OK
	 */
    public static final int RESULT_OK = 0;
    
    /**
     * The Code to say that File do not exist in the server
     */
    public static final int FILE_DOESNT_EXIST = 1;
    
    /**
     * The code to say that Thread Interruption has taken place in the server
     */
    public static final int THREAD_INTERRUPTION = 2;
    
    /**
     * The code which says Socket Timeout Exception - #3
     * 
     */
    public static final int SOCKET_TIMEOUT = 3;
    
    /**
     * The Code which states the Server ERROR 
     * 
     */
    public static final int SERVER_ERROR = 4;
    
}
