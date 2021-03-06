package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class JoinGameController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField hostName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void JoinGameAction(ActionEvent actionEvent) {
        int host = Integer.parseInt(getPort());
        try(Socket socket = new Socket("localhost", host);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()); )
        {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

// проверяем живой ли канал и работаем если живой
            while(!socket.isOutputShutdown()){

// ждём консоли клиента на предмет появления в ней данных
                if(br.ready()){

// данные появились - работаем
                    System.out.println("Client start writing in channel...");
                    Thread.sleep(1000);
                    String clientCommand = br.readLine();

// пишем данные с консоли в канал сокета для сервера
                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Clien sent message " + clientCommand + " to server.");
                    Thread.sleep(1000);
// ждём чтобы сервер успел прочесть сообщение из сокета и ответить

// проверяем условие выхода из соединения
                    if(clientCommand.equalsIgnoreCase("quit")){

// если условие выхода достигнуто разъединяемся
                        System.out.println("Client kill connections");
                        Thread.sleep(2000);

// смотрим что нам ответил сервер на последок перед закрытием ресурсов
                        if(ois.read() > -1)     {
                            System.out.println("reading...");
                            String in = ois.readUTF();
                            System.out.println(in);
                        }

// после предварительных приготовлений выходим из цикла записи чтения
                        break;
                    }

// если условие разъединения не достигнуто продолжаем работу
                    System.out.println("Client sent message & start waiting for data from server...");
                    Thread.sleep(2000);

// проверяем, что нам ответит сервер на сообщение(за предоставленное ему время в паузе он должен был успеть ответить)
                    if(ois.read() > -1)     {

// если успел забираем ответ из канала сервера в сокете и сохраняем её в ois переменную,  печатаем на свою клиентскую консоль
                        System.out.println("reading...");
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }
// на выходе из цикла общения закрываем свои ресурсы
            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (InterruptedException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong host");
            alert.setContentText("Enter correct host");
            alert.showAndWait();
        }
    }

    public String getPort() {
        return hostName.getText();
    }
}
