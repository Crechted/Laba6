package Server;

import Code6.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    public final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static DatagramSocket socket;
    private static DatagramPacket packet;
    private static byte[] buffer = new byte[10000];
    private static int port, portStart = 5680;
    private static InetAddress inetAddress;

    static {
        System.out.println("Enter port:");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            portStart = Integer.parseInt(reader.readLine());
            System.out.println("port: " + portStart);
        } catch (Exception e) {
            System.out.println("Default port: 5680");
        }
    }


    private static String address = "result.json";
    public static String getArgs(){
        return address;
    }
    public static void setArgs(String s){
        address = s;
    }

    /**
     *Creates a new object of the CollectionOrganization class.
     *Created collection with objects from the file whose address is passed in the args variable[0]
     * @param args address of thew file
     * @see CollectionOrganization
     */
    public static void main(String[] args){
        try {
            setArgs(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not found arguments");
            System.out.println("File will be saved in \"result.json\"");
        }
        Collection collection = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getArgs())));
            String str = "", s = null;
            while ((s = reader.readLine()) != null) {
                str += s+"\n";
            }
            collection = GSON.fromJson(str, Collection.class);
            CollectionOrganization.getCollectionOrganization().setCollection(collection.getOrganizations());


            reader.close();
        } catch (FileNotFoundException ex) {

        } catch (EOFException e) {

        } catch (NullPointerException e) {

        } catch (IOException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Unable to read file");
        }



        printStartText();

        //start work with client

        while (true) {
            try {
                socket = new DatagramSocket(portStart);

                buffer = new byte[65*1024];

                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                Thread.sleep(200);

                buffer = packet.getData();

                inetAddress = packet.getAddress();
                port = packet.getPort();


                Workable work = (Workable) Converter.convertFromBytes(buffer);


                String answer = "";

                //EXIT

                if (work instanceof Command.Exit) {
                    answer = "End";
                    buffer = answer.getBytes();
                    packet = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                    socket.send(packet);
                    Command.Save save = Command.Save.getSave();
                    save.setPath(address);
                    System.out.println(save.work());
                    socket.close();
                    break;
                }

                //DEFAULT

                answer = work.work();
                System.out.println("was completed command " + work.getClass().getSimpleName());
                buffer = answer.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                socket.send(packet);

                socket.close();


                //EXCEPTION
            } catch (SocketException e1) {
                System.out.println("*happens* SocketException");
                buffer = "Error".getBytes();
                packet = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                try {
                    socket.send(packet);
                    socket.close();
                } catch (IOException e ){
                } catch (NullPointerException e){
                    System.out.println("Возоможно выбран не подходящий порт");
                    break;
                }
            } catch (IOException e2) {
                System.out.println("*happens* IOException");
                buffer = "Error".getBytes();
                packet = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                try {
                    socket.send(packet);
                } catch (IOException e){
                }
                socket.close();
            } catch (ClassNotFoundException e3) {
                System.out.println("*happens* ClassNotFoundException");
                buffer = "Error".getBytes();
                packet = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                try {
                    socket.send(packet);
                } catch (IOException e){
                }
                socket.close();
            } catch (InterruptedException e) {
            } catch (NumberFormatException e) {
                System.out.println("port = 5680");
            } catch (NullPointerException e) {
                System.out.println("port = 5680");
            }
        }

    }

    public static void printStartText(){
        System.out.println("Была создана коллекция с элементами типа Organization." +
                "\n" + "Элементы были созданы на основе файла " + Server.getArgs());
        int num = CollectionOrganization.getCollectionOrganization().getCollection().size();
        String s = "эл.";
        switch (num % 10){
            case 1:{ s = "элемент";
            break;}
            case 2:
            case 3:
                case 4:{ s = "элемента";
                    break;}
            case 0:
            case 5:
            case 6:
            case 7:
            case 8:
                case 9:{ s = "элементов";
            break;}
        }
        System.out.println("В данный момент в коллекции " + num + " " + s +
                "\n" + "Ожидаю клиентов...");
    }
}
