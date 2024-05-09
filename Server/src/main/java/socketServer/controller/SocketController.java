package socketServer.controller;

import common.UserDto;
import domain.Participant;
import myproject.hibernate.service.Service;
//import service.ParticipantService;
import common.RequestDto;
import common.ResponseDto;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SocketController {
    //    private final ParticipantService service = new ParticipantService();
    private final Service service = new Service();
    private final ServerSocket serverSocket;
    private final Map<Integer, ObjectOutputStream> portToSocketOutputStream = new HashMap<>();
    private Set<String> activeUsernames = Collections.synchronizedSet(new HashSet<>());

    public SocketController(Properties properties) {
        serverSocket = createServerSocket(properties);
    }

    private ServerSocket createServerSocket(Properties properties) {
        try {
            String portString = properties.getProperty("server.port");
            //var server = new ServerSocket(Integer.parseInt(portString));
            InetAddress bindAddr = InetAddress.getByName("localhost");
            var server = new ServerSocket(Integer.parseInt(portString), 50, bindAddr);
            System.out.println(server);
            return server;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenForClients() {
        while (true) {
            // server on
            Socket clientSocket = acceptClient();
            new Thread(() -> {
                try {
                    processClient(clientSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private void processClient(Socket clientSocket) throws IOException {
        //read
        var inputStream = clientSocket.getInputStream();
//                                                                                                    ''''''''''''''''
        // begin c#
        /*
        int BUFFER_SIZE = 1024;
        try {
            DataInputStream reader
                    = new DataInputStream(inputStream);
            int availableBytes = reader.available();
            System.out.println(availableBytes);
            if (availableBytes > 0) {
                final byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = reader.read(buffer);
                UserDto deserializedUser = SerializationUtils.deserialize(buffer);
                System.out.println(deserializedUser);

            }
            ;
        } catch (Exception exp) {
            System.out.println(exp);
        }
  */
        //''''''''''''''''
        // end c#

        //write
        ObjectInputStream in = new ObjectInputStream(inputStream);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

        portToSocketOutputStream.put(clientSocket.getPort(), out);

        try {
            RequestDto requestDto = readCommandFromClient(in);
            String requestType = requestDto.getRequestType();
            if ("addParticipant".equals(requestType)) {
                myproject.hibernate.domain.Participant savedParticipant = null;
                try {
//                    int lastId = service.getLastId() + 1;
                    Participant participant = requestDto.getDto();
//                    participant.setId(lastId);
//                    service.addParticipant(participant);
                    myproject.hibernate.domain.Participant p = new myproject.hibernate.domain.Participant();
                    p.setName(participant.getName());
                    p.setTeamName(participant.getTeamName());
                    savedParticipant = service.addParticipant(p, participant.getIdRace());
                    System.out.println("Saving to db " + requestDto.getDto());
                } catch (Exception exception) {
                    System.out.println("db ex " + exception.getMessage());
                    System.out.println("db ex " + exception);
                }
                ResponseDto response = null;
                if (savedParticipant != null) {
                    Participant participant = new Participant(savedParticipant.getId(), savedParticipant.getTeamName(), savedParticipant.getName(), savedParticipant.getRace().getId());
                    List<Participant> participantList = List.of(participant);
                     response = new ResponseDto("Participant", "Success", participantList);
                }else{
                    response = new ResponseDto("Participant", "Error", List.of(requestDto.getDto()));
                }
                out.writeObject(response);
                System.out.println("Sending to client " + response);

            } else if ("findAll".equals(requestType)) {
//                List<Participant> dtos = service.getAll();
                List<myproject.hibernate.domain.Participant> dtos = service.getAllParticipant();
                //mapare la domeniu
                var list = dtos.stream()
                        .map(d -> new domain.Participant(d.getId(), d.getTeamName(), d.getName(), d.getRace().getId()))
                        .toList();

                out.writeObject(new ResponseDto("findAll", "Success", list));
            } else if (requestType.startsWith("Login")) {
                String usrn = requestType.substring(5);
                System.out.println(" login:" + usrn);
                activeUsernames.add(usrn);
                out.writeObject(new UserDto(activeUsernames, "Success"));
            } else if (requestType.startsWith("Logout")) {
                String usrn = requestType.substring(7);
                System.out.println(" logout:" + usrn);
                activeUsernames.remove(usrn);
                System.out.println(activeUsernames);

            } else {
                System.out.println();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception);
            in.close();
            out.close();
            clientSocket.close();
        }
    }

    private RequestDto readCommandFromClient(ObjectInputStream in) {
        //  LOG.info("reading");
        try {
            var inp = in.readObject();
            System.out.println(inp.toString());
            return (RequestDto) inp;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    private Socket acceptClient() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
