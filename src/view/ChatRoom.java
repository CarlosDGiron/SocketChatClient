/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.Message;
import model.Request;
import model.Response;
import model.User;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author cana0
 */
public class ChatRoom extends javax.swing.JFrame {

    public String HOST;
    private int lastMessageId;
    private User currentUser;
    private Message requestMessage;
    private final String requestType = "sendMessage";
    private Timestamp timestamp;
    public Timer timerEcho;
    public Timer timerMessage;

    /**
     * Creates new form ChatRoom
     */
    public ChatRoom() {
        initComponents();
        currentUser = null;
        requestMessage = null;
        timestamp = null;
        lastMessageId = 0;
    }

    public void setHost(String HOST) {
        this.HOST = HOST;
    }

    public void startEcho() {
        timerEcho = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Request echoRequest = new Request("userOnlineCheck", currentUser);
                        Socket socketClient;
                        try {
                            socketClient = new Socket("0.0.0.0", 5050);
                            InputStream inputStream = socketClient.getInputStream();
                            ObjectInputStream socketResponse = new ObjectInputStream(inputStream);
                            OutputStream outputStream = socketClient.getOutputStream();
                            ObjectOutputStream socketRequest = new ObjectOutputStream(outputStream);
                            socketRequest.writeObject(echoRequest);
                            try {
                                Response response = (Response) socketResponse.readObject();
                                ArrayList<User> data = (ArrayList<User>) response.getRequestData();
                                ArrayList<String> modelData = new ArrayList();
                                for (User u : data) {
                                    modelData.add(u.getName() + "(" + u.getId() + ")");
                                }
                                DefaultComboBoxModel model = new DefaultComboBoxModel<>(modelData.toArray(new String[0]));
                                onlineUsers.setModel(model);
                            } catch (IOException ex) {
                                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            }
        });
        timerEcho.start();
    }

    public void startHistory() {
        timerMessage = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Request messageRequest = new Request("updateMessageList", lastMessageId);
                        Socket socketClient;
                        try {
                            socketClient = new Socket("0.0.0.0", 5050);
                            InputStream inputStream = socketClient.getInputStream();
                            ObjectInputStream socketResponse = new ObjectInputStream(inputStream);
                            OutputStream outputStream = socketClient.getOutputStream();
                            ObjectOutputStream socketRequest = new ObjectOutputStream(outputStream);
                            socketRequest.writeObject(messageRequest);
                            try {
                                Response response = (Response) socketResponse.readObject();
                                if (response.getResponseValue()) {
                                    ArrayList<Message> data = (ArrayList<Message>) response.getRequestData();
                                    for (Message message : data) {
                                        for (String messageText : message.getText()) {
                                            text.append(message.getSender().getName() + "(" + message.getSender().getId() + "): " + messageText + "\n");
                                        }
                                        lastMessageId = message.getId();
                                    }
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            }
        });
        timerMessage.start();
    }

    public void setUser(User currentUser) throws IOException {
        this.currentUser = currentUser;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sendBtn = new javax.swing.JButton();
        message = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        onlineUsers = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SocketChat");

        sendBtn.setText("Enviar");
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        message.setToolTipText("Escriba el mensaje a enviar...");
        message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageActionPerformed(evt);
            }
        });

        text.setEditable(false);
        text.setColumns(20);
        text.setRows(5);
        text.setFocusable(false);
        jScrollPane2.setViewportView(text);

        jLabel1.setText("Online User:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(onlineUsers, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed
        // TODO add your handling code here:
        Request sendMessageRequest;
        Response sendMessageResponse;
        requestMessage = new Message(0, message.getText(), currentUser, null);
        sendMessageRequest = new Request(requestType, requestMessage);
        sendMessageResponse = sendMessage(sendMessageRequest);
        clearText();
    }//GEN-LAST:event_sendBtnActionPerformed
    private Response sendMessage(Request sendMessageRequest) {
        Response sendMessageResponse = null;
        try {
            Socket socketClient = new Socket("0.0.0.0", 5050);
            InputStream inputStream = socketClient.getInputStream();
            ObjectInputStream socketResponse = new ObjectInputStream(inputStream);
            OutputStream outputStream = socketClient.getOutputStream();
            ObjectOutputStream socketRequest = new ObjectOutputStream(outputStream);
            socketRequest.writeObject(sendMessageRequest);
            sendMessageResponse = (Response) socketResponse.readObject();
            socketClient.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " - " + e.getCause());
        }
        return sendMessageResponse;
    }
    private void messageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messageActionPerformed
    private void clearText() {
        message.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatRoom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField message;
    private javax.swing.JComboBox<String> onlineUsers;
    private javax.swing.JButton sendBtn;
    private javax.swing.JTextArea text;
    // End of variables declaration//GEN-END:variables
}
