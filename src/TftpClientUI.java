
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class TftpClientUI extends JFrame implements TftpObserver, ActionListener {

    static final Logger logger = LogManager.getLogManager().getLogger("");

    /** TOP MENU **/
    JPanel top;
    JLabel serverLabel;
    JTextField serverInput;
    JLabel portLabel;
    JTextField portInput;

    /** CENTER CONTAINER **/
    JPanel center;
    static final String DEFAULT_REMOTE_INPUT = "File Name on the server";

    /** SEND CONTAINER **/
    JPanel send;
    JTextField remoteUploadFileNameInput;
    JFileChooser uploadChooser;
    JProgressBar progressBar;

    /** RECEIVE CONTAINER **/
    JPanel receive;
    JTextField remoteDownloadFileNameInput;
    JFileChooser downloadChooser;

    /** RIGHT PANEL **/
    JTextArea logArea;

    /* TFTP CLIENTS */
    Client client;

    public TftpClientUI(){
        super("TFTP Client");

        configureWindow();
        drawUI();
        setListeners();

        setLogging();
    }

    private void setLogging() {
        logger.addHandler(new TextFieldHandler(logArea));
    }

    private void setListeners() {
        uploadChooser.addActionListener(this);
        downloadChooser.addActionListener(this);

        remoteDownloadFileNameInput.addActionListener(this);
    }

    private void drawUI() {

        /** Serveur informations **/
        top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
        top.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        serverLabel = new JLabel("Server IP : ");

        serverInput = new JTextField("127.0.0.1");
        serverInput.setPreferredSize(new Dimension(100, 20));

        portLabel = new JLabel("Server port : ");

        portInput = new JTextField("69");
        portInput.setPreferredSize(new Dimension(30, 20));

        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        progressBar.setVisible(false);
        progressBar.setStringPainted(true);
        progressBar.getSize().width += 60;
        progressBar.getSize().height += 20;

        add(top, BorderLayout.NORTH);

        top.add(serverLabel);
        top.add(serverInput);
        top.add(portLabel);
        top.add(portInput);
        top.add(progressBar);
        top.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        /** Main content definition **/

        center = new JPanel();
        center.setLayout(new GridLayout(1, 2));

        /** Setting sending components **/

        send = new JPanel();
        send.setLayout(new BorderLayout());
        send.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Send File"));
        remoteUploadFileNameInput = new JTextField(DEFAULT_REMOTE_INPUT);

        //Simulate prompt
        remoteUploadFileNameInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(remoteUploadFileNameInput.getText().equalsIgnoreCase(DEFAULT_REMOTE_INPUT)){
                    remoteUploadFileNameInput.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(remoteUploadFileNameInput.getText().equalsIgnoreCase("")){
                    remoteUploadFileNameInput.setText(DEFAULT_REMOTE_INPUT);
                }
            }
        });

        uploadChooser = new JFileChooser();
        uploadChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        uploadChooser.setApproveButtonText("Send");

        JPanel sendContent = new JPanel();
        sendContent.setLayout(new BorderLayout());
        sendContent.add(uploadChooser, BorderLayout.CENTER);
        sendContent.add(remoteUploadFileNameInput, BorderLayout.PAGE_START);
        send.add(sendContent, BorderLayout.CENTER);

        /** Setting reception components */

        receive = new JPanel();
        receive.setLayout(new BorderLayout());
        receive.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),"Receive File "));
        remoteDownloadFileNameInput = new JTextField(DEFAULT_REMOTE_INPUT);

        //Simulate prompt
        remoteDownloadFileNameInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(remoteDownloadFileNameInput.getText().equalsIgnoreCase(DEFAULT_REMOTE_INPUT)){
                    remoteDownloadFileNameInput.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(remoteDownloadFileNameInput.getText().equalsIgnoreCase("")){
                    remoteDownloadFileNameInput.setText(DEFAULT_REMOTE_INPUT);
                }
            }
        });

        downloadChooser = new JFileChooser();
        downloadChooser.setDialogType(JFileChooser.DIRECTORIES_ONLY);
        downloadChooser.setApproveButtonText("Receive");

        JPanel receiveContent = new JPanel();
        receiveContent.setLayout(new BorderLayout());
        receiveContent.add(downloadChooser, BorderLayout.CENTER);
        receiveContent.add(remoteDownloadFileNameInput, BorderLayout.PAGE_START);
        receive.add(receiveContent, BorderLayout.CENTER);

        /** Adding to the center container **/
        center.add(send);
        center.add(receive);

         /** Adding logging area **/

        logArea = new JTextArea("Flow of events: " + System.getProperty("line.separator"));
        logArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        logArea.setPreferredSize(new Dimension(getWidth(), 150));


        /** Adding all **/

        add(center, BorderLayout.CENTER);
        add(new JScrollPane(logArea), BorderLayout.PAGE_END);
    }

    private void configureWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        setSize((int)(screen.width * 0.8), (int)(screen.height * 0.8));
        setLocation((screen.width - getWidth())/ 2, (screen.height - getHeight())/ 2);

        setLayout(new BorderLayout());
    }

    @Override
    public void onFileSendingStarted(final File sourceFile) {
        logger.info("Started sending files : " + sourceFile);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setLocation((getWidth() - progressBar.getWidth()) /2, (getHeight() - progressBar.getHeight())/2);
                progressBar.setString("Envoi de " + sourceFile.getName() + " en cours");
                progressBar.setVisible(true);
            }
        });
    }

    @Override
    public void onFileSendingProgress(final float percent) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setValue((int) (percent * 100f));
            }
        });
    }

    @Override
    public void onFileSendingEnded(Client client, File sourceFile) {
        logger.info("Finished File Upload");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisible(false);
                progressBar.setValue(0);
            }
        });
        client.close();
    }

    @Override
    public void onFileReceptionStarted(String remoteFileName) {
        logger.info("Reception file: " + remoteFileName);
    }

    @Override
    public void onFileReceptionEnded(Client client, File holder) {
        logger.info("Reception of the complete file : " + holder);
        client.close();
    }

    @Override
    public void onExceptionOccured(Client client, Exception t) {
        if(t instanceof SocketException){
            displayError("The waiting time for the request has been exceeded ...");
        }else if(t instanceof TftpException || t instanceof IOException){
            displayError("An error occured when transmitting or receiving a packet : " + t.getMessage());
        }
    }

    @Override
    public void onProtocolError(Client client, int errno, String errorMsg) {
        displayError("The server returned an error : (" + errno + ") : " + errorMsg );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == downloadChooser){
            if(remoteDownloadFileNameInput.getText().equalsIgnoreCase("") || remoteDownloadFileNameInput.getText().equalsIgnoreCase(DEFAULT_REMOTE_INPUT)){
                displayError("Please enter a valid filename to retrieve from the server");
            }

            try {

                client = new Client(serverInput.getText(), Integer.parseInt(portInput.getText()));
                client.addObserver(this);
                int result = client.receiveFile(remoteDownloadFileNameInput.getText(), downloadChooser.getSelectedFile(), TftpIOEnum.OCTET);

                if(result != 0){
                    logger.info("An error occurred during the reception : error" + result);
                }

            } catch (UnknownHostException e1) {
                String error = "Unable to determine a path to the host:" + serverInput.getText() + ":" + portInput.getText();

                logger.log(Level.WARNING, error);
                displayError(error);
            } catch (SocketException e1) {
                String error = "An error occured when connecting";

                logger.log(Level.WARNING, error);
                displayError(error);
            }

        }else if(e.getSource() == uploadChooser){
            logger.info("Sending a file :" + uploadChooser.getSelectedFile());
            if(remoteUploadFileNameInput.getText().equalsIgnoreCase("") || remoteUploadFileNameInput.getText().equalsIgnoreCase(DEFAULT_REMOTE_INPUT)){
                displayError("Please enter a valid filename to store on the server");
            }

            //Dupplication de code bien moche ...
            try {

                client = new Client(serverInput.getText(), Integer.parseInt(portInput.getText()));
                client.addObserver(this);
                int result = client.sendFile(uploadChooser.getSelectedFile(), remoteUploadFileNameInput.getText(), TftpIOEnum.OCTET);

                if(result != 0){
                    logger.info("An error occured when sending : error " + result);
                }

            } catch (UnknownHostException e1) {
                String error = "Unable to determine a path to the host:" + serverInput.getText() + ":" + portInput.getText();

                logger.log(Level.WARNING, error);
                JOptionPane.showMessageDialog(this, error, "Error :(", JOptionPane.ERROR_MESSAGE);
            } catch (SocketException e1) {
                String error = "An error occured when connecting";

                logger.log(Level.WARNING, error);
                JOptionPane.showMessageDialog(this, error, "Error :(", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayError(String s) {
        JOptionPane.showMessageDialog(this, s, "Error :(", JOptionPane.WARNING_MESSAGE);
    }
}
