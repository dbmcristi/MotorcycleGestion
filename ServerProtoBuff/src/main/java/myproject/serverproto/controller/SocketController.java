package myproject.serverproto.controller;

import com.google.protobuf.ProtocolStringList;
import moto.network.protobuffprotocol.MotoProtobufs;
import repository.RepositoryException;
import service.ParticipantService;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


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

    private MotoProtobufs.IResponse handleRequest(MotoProtobufs.IRequest request) {
        MotoProtobufs.IResponse response = null;
        System.out.println(request + " handleRequest");
        if (request.hasRequestDto()) {
            System.out.println("Participant request");
            MotoProtobufs.Participant participant = request.getRequestDto().getDto();
            System.out.println("handleRequest Participant ClientRpcWorker -- " + participant);
            synchronized (service) {
                try {
                    int lastId = service.getLastId() + 1;
                    domain.Participant domainParticipant = new domain.Participant(lastId, participant.getTeamName(), participant.getName(), participant.getIdRace());
                    service.addParticipant(domainParticipant);
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Raspuns OkResponse pentru InscrieParticipantRequest ---- ClientRpcWorker");
                var participantProto = MotoProtobufs.Participant.newBuilder()
                        .setId(participant.getId())
                        .setName(participant.getName())
                        .setTeamName(participant.getTeamName())
                        .setIdRace(participant.getIdRace())
                        .build();
                var responseDto = MotoProtobufs.ResponseDto.newBuilder()
                        .setMessage("Success")
                        .setResponseType("Participant")
                        .addParticipants(participantProto)
                        .build();
//                        .setParticipants(1, participant)

                return MotoProtobufs.IResponse.newBuilder()
                        .setResponseDto(responseDto)
                        .build();
            }

        } else if (request.hasUserDto()) {
            System.out.println(request + " has requestDTO --- SocketControler");
            synchronized (activeUsernames) {
                System.out.println("User request");
                MotoProtobufs.UserDto userDto = request.getUserDto();
                String type = userDto.getMessage();
                if (type.startsWith("addUser")) {
                    String usrn = userDto.getUsername(0).substring(5);
                    System.out.println("Login: " + usrn);
                    activeUsernames.add(usrn);

                    MotoProtobufs.UserDto.Builder builder = MotoProtobufs.UserDto.newBuilder();
                    for (String VARIABLE : activeUsernames) {
                        System.out.println(VARIABLE);
                        builder.addUsername(VARIABLE);
                    }
                    builder.setMessage("Success");
                    MotoProtobufs.UserDto dto = builder.build();
                    return MotoProtobufs.IResponse.newBuilder().setUserDto(dto).build();
                } else if (type.startsWith("Logout")) {
                    int count = userDto.getUsernameCount();
                    if (count > 0) {
                        String usrn = userDto.getUsername(0);
                        System.out.println("Logout: " + usrn);
                        activeUsernames.remove(usrn);
                        System.out.println("Actives: ");
                        for (String VARIABLE : activeUsernames) {
                            System.out.println(VARIABLE);
                        }
                    }
                }
            }
        }
        return response;
    }

    private void processClient(Socket clientSocket) throws IOException {
        MotoProtobufs.IResponse response = readFrom(clientSocket);
        if (response != null) {
            writeTo(clientSocket, response);
        }
    }

    private MotoProtobufs.IResponse readFrom(Socket clientSocket) throws IOException {
        InputStream inputStream = null;
        try {
            //read
            inputStream = clientSocket.getInputStream();
            MotoProtobufs.IRequest irequest = MotoProtobufs.IRequest.parseDelimitedFrom(inputStream);
            return handleRequest(irequest);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception);
            inputStream.close();
            clientSocket.close();
        }
        return null;
    }

    private void writeTo(Socket clientSocket, MotoProtobufs.IResponse response) throws IOException {
        OutputStream outStream = null;
        try {
            //write
            outStream = clientSocket.getOutputStream();
            response.writeDelimitedTo(outStream);
            outStream.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception);
            outStream.close();
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
}
