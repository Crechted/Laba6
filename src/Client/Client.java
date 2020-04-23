package Client;



import Code6.CollectionOrganization;
import Code6.Command;
import Code6.Converter;
import Code6.Workable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class Client {

    private static ByteBuffer buffer;
    private static SocketAddress address;
    private static DatagramChannel channel;
    private static int port = 5680;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static {
        System.out.println("Enter port:");
        try {
            port = Integer.parseInt(reader.readLine());
            System.out.println("port: " + port);
        } catch (Exception e) {
            System.out.println("Default port: " + port);
        }
    }

    private static byte[] bufferOut;
    private static byte[] bufferIn;
    private static Workable work;

    /**
     * @see CollectionOrganization
     * @see Work
     */
    public static void main(String[] args)  {
        address = new InetSocketAddress("localhost", port);
        work = null;

        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
        } catch (IOException e) {
            System.out.println("Channel is not open");
        }

        System.out.println("Здравствуйте вам доступны следующие команды:");
        System.out.println(Command.Help.getHelp().work());

        while(true) {
            System.out.println("Enter your command, please: ");
            work = Work.goInteractiveWork();

            try {
                bufferOut = Converter.convertToBytes(work);
                buffer = ByteBuffer.wrap(bufferOut);

                //EXIT

                if (work instanceof Command.Exit) {
                    channel.send(buffer, address); //send a message to the server

                    String answer = serverReceive(); //receive answer by the server
                    if (answer.equals("Server is not responding")) {
                        System.out.println(answer);
                        break;
                    }

                    System.out.println(answer);
                    channel.close();
                    reader.close();
                    break;
                }

                //EXECUTE_SCRIPT

                boolean exit = false;
                if (work instanceof Command.ExecuteScript) {
                    ArrayList<Workable> list = Command.ExecuteScript.getExecuteScript().getListWork();

                    for (int i = 0; i < list.size(); ++i) {

                        bufferOut = Converter.convertToBytes(list.get(i));
                        buffer = ByteBuffer.wrap(bufferOut);
                        channel.send(buffer, address);

                        String answer = serverReceive(); //receive answer by the server
                        if (answer.equals("End")) {
                            System.out.println(answer);
                            exit = true;
                            break;
                        }

                        if (answer.equals("Server is not responding")) {
                            System.out.println(answer);
                            continue;
                        }

                        System.out.println(answer);
                    }
                    Work.removeScriptNameList();
                    Command.ExecuteScript.getExecuteScript().getListWork().removeAll(list);
                }
                if (exit) {
                    reader.close();
                    break;
                }

                //DEFAULT

                 channel.send(buffer, address);

                String answer = serverReceive(); //receive answer by the server
                if (answer.equals("Server is not responding")) {
                    System.out.println(answer);
                    continue;
                }
                System.out.println(answer);

                //EXCEPTION


            } catch (SocketException  e1) {
                System.out.println("*happens* SocketException");
            } catch (UnknownHostException e2) {
                System.out.println("*happens* UnknownHostException");
            } catch (IOException e3) {
                System.out.println("*happens* IOException");
            } catch (StackOverflowError e) {
                System.out.println(e.getStackTrace());
            } catch (InterruptedException e) {
            }

        }


    }// C:\Programming\Plugin\gson-2.8.6.jar Lab5 C:/Programming/resultNJ.json
    public static String serverReceive() throws IOException, InterruptedException {
        String answer = "";
        ArrayList<byte[]> packetList = new ArrayList<>(1);
        boolean isReceived = false;
        int count = 0;
        for (int i = 0; true; ++i){
            byte[] packet = new byte[65*1024];
            ByteBuffer buffer = ByteBuffer.wrap(packet);

            if (i == 10) {
                System.out.println("Passed 5 seconds. New answer from the server wasn't received");
                break;
            }

            SocketAddress add = channel.receive(buffer);
            if (add != null) {
                System.out.println("Packet " + ++count + " was received from the server");
                isReceived = true;
                packetList.add(packet);
                continue;
            }
            Thread.sleep(500);
            if(i % 2 == 0) {
                System.out.println(i/2+1);
            }
        }

        if (!isReceived)
            answer = "Server is not responding";

        for(int i = 0; i < packetList.size(); ++i)
            answer += getAnswer(packetList.get(i));

        return answer;
    }

    public static String getAnswer(byte[] buffer) {
        String answer = "";
        if (buffer == null) return "";
        for (int i = 0; i < buffer.length; ++i)
            answer += buffer[i] != 0 ? (char)buffer[i] : "";
        return answer;
    }
}
