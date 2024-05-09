package networkcsharp;


import repository.RepositoryException;
import service.ParticipantService;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import org.apache.commons.lang3.SerializationUtils;

public class SocketController {
    private final ParticipantService service = new ParticipantService();
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

    private IResponse handleRequest(IRequest request) {
        IResponse response = null;
        if (request instanceof RequestDto) {
            System.out.println("Participant request");

            Participant participant = ((RequestDto) request).getDto();
            System.out.println("handleRequest Participant ClientRpcWorker -- " + participant);

            synchronized (service) {

                try {
                    int lastId = service.getLastId() + 1;
                    participant.setId(lastId);
                    domain.Participant domainParticipant = new domain.Participant(participant.getId(), participant.getTeamName(), participant.getName(), participant.getIdRace());
                    service.addParticipant(domainParticipant);
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Raspuns OkResponse pentru InscrieParticipantRequest ---- ClientRpcWorker");
                List<Participant> list = new ArrayList<>();
                list.add(participant);
                return new ResponseDto("Participant", "Success", list);
            }


        } else if (request instanceof UserDto) {
            synchronized (activeUsernames) {
                System.out.println("User request");
                UserDto userDto = (UserDto) request;
                String type = userDto.getMessage();
                HashSet<String> users = userDto.getUsername();
                if (type.startsWith("addUser")) {
                    HashSet<String> actives = new HashSet<>();
                    String usrn = userDto.getUsername().iterator().next().substring(5);
                    System.out.println("Login: " + usrn);

                    activeUsernames.add(usrn);

                    for (String VARIABLE : activeUsernames) {
                        System.out.println(VARIABLE);
                        actives.add(VARIABLE);
                    }

                    return new UserDto(actives, "Success");
                } else if (type.startsWith("Logout")) {
                    String usrn = userDto.getUsername().iterator().next();
                    System.out.println("Logout: " + usrn);
                    activeUsernames.remove(usrn);
                }
            }
        }

        return response;
    }


    private void processClient(Socket clientSocket) throws IOException {
        //read
        var inputStream = clientSocket.getInputStream();
        //write
       // ObjectInputStream in = new ObjectInputStream(inputStream);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        // begin c#
        int BUFFER_SIZE = 1024;
        try {
            DataInputStream reader
                    = new DataInputStream(inputStream);
            int availableBytes = reader.available();
            System.out.println(availableBytes);
            if (availableBytes > 0) {
                final byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = reader.read(buffer);
                Object obj = deserializeUser(buffer);
                IRequest irequest = (IRequest)obj;
                System.out.println(irequest);

                portToSocketOutputStream.put(clientSocket.getPort(), out);
               // var inp = in.readObject();
               // System.out.println(inp.toString());
              //  IRequest irequest = (IRequest) inp;
                IResponse response = handleRequest(irequest);
                if (response != null) {
                    out.writeObject(response);
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception);
            //in.close();
            out.close();
            clientSocket.close();
        }
    }

    private Socket acceptClient() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Object deserializeUser(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

        try (ObjectInputStream in = new ObjectInputStream(bis)) {
            UserDto user = new UserDto();
            user.readObject(in);
            return user;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    static byte[] serializeUser(final UserDto user) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(user);
            out.flush();
            return bos.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
