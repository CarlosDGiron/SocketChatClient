package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import model.Message;
import model.User;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author cana0
 */
public class ChatClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here7
        File hostFile=new File("host.txt");
        BufferedReader reader = new BufferedReader(new FileReader (hostFile));
        String host=reader.readLine();
        reader.close();
        Login cr=new Login();
        cr.setHost(host);
        cr.show();
    }    
}
