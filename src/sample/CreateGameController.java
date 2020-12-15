package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateGameController implements Initializable {
    @FXML
    private Label nameField;

    @FXML
    private TextField portName;


    @FXML
    public void createGameAction(ActionEvent actionEvent) throws IOException {
        if (!getPortName().equals("")) {
            int port = Integer.parseInt(getPortName());
            try (ServerSocket server = new ServerSocket(port)) {
                Socket client = server.accept();
                System.out.print("Connection accepted.");

// инициируем каналы для  общения в сокете, для сервера

// канал записи в сокет
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                System.out.println("DataOutputStream  created");

                // канал чтения из сокета
                DataInputStream in = new DataInputStream(client.getInputStream());
                System.out.println("DataInputStream created");

// начинаем диалог с подключенным клиентом в цикле, пока сокет не закрыт
                while(!client.isClosed()){

                    System.out.println("Server reading from channel");

// сервер ждёт в канале чтения (inputstream) получения данных клиента
                    String entry = in.readUTF();

// после получения данных считывает их
                    System.out.println("READ from client message - "+entry);

// и выводит в консоль
                    System.out.println("Server try writing to channel");

// инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову       - quit
                    if(entry.equalsIgnoreCase("quit")){
                        System.out.println("Client initialize connections suicide ...");
                        out.writeUTF("Server reply - "+entry + " - OK");
                        out.flush();
                        Thread.sleep(3000);
                        break;
                    }

// если условие окончания работы не верно - продолжаем работу - отправляем эхо-ответ  обратно клиенту
                    out.writeUTF("Server reply - "+entry + " - OK");
                    System.out.println("Server Wrote message to client.");

// освобождаем буфер сетевых сообщений (по умолчанию сообщение не сразу отправляется в сеть, а сначала накапливается в специальном буфере сообщений, размер которого определяется конкретными настройками в системе, а метод  - flush() отправляет сообщение не дожидаясь наполнения буфера согласно настройкам системы
                    out.flush();

                }

// если условие выхода - верно выключаем соединения
                System.out.println("Client disconnected");
                System.out.println("Closing connections & channels.");

                // закрываем сначала каналы сокета !
                in.close();
                out.close();

                // потом закрываем сам сокет общения на стороне сервера!
                client.close();

                // потом закрываем сокет сервера который создаёт сокеты общения
                // хотя при многопоточном применении его закрывать не нужно
                // для возможности поставить этот серверный сокет обратно в ожидание нового подключения

                System.out.println("Closing connections & channels - DONE.");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            }
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameField.setText("yolo");
    }

    public void setNameField(String name) {
        nameField.setText(name);
    }

    public String getPortName() {
        return portName.getText();
    }


}
