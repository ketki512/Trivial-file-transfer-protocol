


/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public enum TftpIOEnum {
    NETASCII("netascii"),
    OCTET("octet"),
    MAIL("mail");

    private final String mode;

    TftpIOEnum(String type) {
        mode = type;
    }

    public String getValue() {
        return mode;
    }
}
