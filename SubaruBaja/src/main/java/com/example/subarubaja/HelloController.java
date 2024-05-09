package com.example.subarubaja;


import common.ResponseDto;
import common.UserDto;
import domain.*;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import myproject.hibernate.service.Service;
//import service.ParticipantService;
//import service.RaceService;
//import service.UserService;
import common.RequestDto;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.security.Provider;
import java.util.*;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

//import service.ParticipantService;
public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    Properties properties;

    public ListView<Race> raceListView;
    public ListView<User> userListView;
    public TextField idRaceSearched;
    public TextField nrPartsByIdRace;
    public TextField teamNameFXID;
    public TextField userNameNewStage;
    public TextField passwordNewStage;
    public Button loginButton;
    public Button logOut;
    public Button searchByEG;
    public Button registerParticipant;
    public TextField idRaceParticipant;
    public TextField nameParticipant;
    public TextField teamNameParticipant;
    public Service service;
    //    private final UserService userService;
//    private final ParticipantService participantService;
//    private final RaceService raceService;
    public ListView<Participant> participantListView = new ListView<>(); //observator
    @FXML
    public ComboBox<String> capacityComboBox;
    @FXML
    private ObservableList<Participant> participantObservableList =
            javafx.collections.FXCollections.observableList(new ArrayList<>());
    @FXML
    private final ObservableList<User> userObservableList =
            javafx.collections.FXCollections.observableList(new ArrayList<>());
    @FXML
    private final ObservableList<Race> raceObservableList =
            javafx.collections.FXCollections.observableList(new ArrayList<>());

    @FXML
    private ObservableList<EngineCapacity> engineCapacities =
            javafx.collections.FXCollections.observableList(new ArrayList<>());


    public HelloController() throws IOException {
//        userService = new UserService();
//        raceService = new RaceService();
//        participantService = new ParticipantService();

        service = new Service();
        //        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // Invoke the refreshListView method
//                refreshListView();
//            }
//        }, 0, 4000);

    }

    private void refreshListView() {
        if (UserInstance.getInstance().isLogged()) {
            var participants = service.getAllParticipant().stream()
                    .map(val -> new domain.Participant(val.getId(),val.getTeamName(),val.getName(),val.getRace().getId()))
                    .toList();
            participantListView.refresh();
            participantObservableList.setAll(participants);
            participantListView.setItems(participantObservableList);
        }
    }

    @FXML
    protected void printParticipant() {
        //if mesaj server then refresh lista
        if (UserInstance.getInstance().isLogged()) {
            try {
                // participantService.addParticipant(e);
                //notifyServer
                Socket serverSocket = new Socket("127.0.0.1", 9999);
                ObjectOutputStream channelWrite = new ObjectOutputStream(serverSocket.getOutputStream());
                channelWrite.writeObject(new RequestDto("findAll", null));
                ObjectInputStream channelRead = new ObjectInputStream(serverSocket.getInputStream());
                try {
                    ResponseDto responseDTO = (ResponseDto) channelRead.readObject();
                    if ("Success".equals(responseDTO.getMessage())) {
                        List<Participant> participants = responseDTO.getParticipants();
                        participantObservableList.setAll(participants);
                        participantListView.setItems(participantObservableList);
                    }

                } catch (IOException | ClassNotFoundException exception) {
                    throw new RuntimeException(exception);
                }

                channelRead.close();
                channelWrite.close();
                serverSocket.close();

            } catch (Exception re) {
                Alert alert1 = new Alert(ERROR, re.getMessage());
                alert1.show();
//        } catch (SQLException ex) {
//            Alert alert = new Alert(ERROR,ex.getMessage());
//            alert.show();
//        }
            }
//            participantObservableList.setAll(participantService.getAll());
//            participantListView.setItems(participantObservableList);

        } else {
            System.out.println("Not logged");
        }
    }

    @FXML
    public void printUser() {
        if (UserInstance.getInstance().isLogged()) {
//            userObservableList.setAll(userService.getAll());
            var list = service.getAllUser().stream()
                    .map(val -> new domain.User(val.getId(), val.getUsername(), val.getPassword()))
                    .toList();
            userObservableList.setAll(list);

            userListView.setItems(userObservableList);
        } else {
            System.out.println("Not logged");
        }
    }

    @FXML
    public void printRace() {
        if (UserInstance.getInstance().isLogged()) {
//            raceObservableList.setAll(raceService.getAll());
            var list = service.getAllRace().stream()
                    .map(val -> new domain.Race(val.getId(), val.getName(), val.getEngineCapacity()))
                    .toList();
            raceObservableList.setAll(list);
            raceListView.setItems(raceObservableList);
        } else {
            System.out.println("Not logged");
        }
    }

    public void getParticipantsByRaceID(ActionEvent actionEvent) {
        if (UserInstance.getInstance().isLogged()) {
            try {
                int raceId = Integer.parseInt(idRaceSearched.getText());
//                Integer result = participantService.getParticipantSize(raceId);
                Integer result = service.getParticipantSize(raceId);
                nrPartsByIdRace.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getParticipantsByTeamName(ActionEvent actionEvent) {
        if (UserInstance.getInstance().isLogged()) {
            String teamName = teamNameFXID.getText();
//            participantObservableList.setAll(participantService.searchByTeamName(teamName));
            var list = service.searchByTeamName(teamName).stream()
                    .map(val -> new domain.Participant(val.getId(), val.getTeamName(), val.getName(), val.getRace().getId()))
                    .toList();
            participantObservableList.setAll(list);
            participantListView.setItems(participantObservableList);
        }
    }

    public void checkIfIsLoggedNewStage(ActionEvent actionEvent) {

        String usrn = userNameNewStage.getText();
        String pwsd = passwordNewStage.getText();
//        boolean isLogged = userService.isLogged(usrn, pwsd);
        boolean isLogged = service.isLogged(usrn, pwsd);
        System.out.println(isLogged);
        UserInstance.getInstance().setLogged(isLogged);
        Stage mainStage = buildMainStage(actionEvent);
        if (isLogged) {
            UserInstance.getInstance().setUser(usrn);
            mainStage.setTitle("Welcome " + usrn);
            mainStage.show();

            Socket serverSocket = null;
            try {
                serverSocket = new Socket("127.0.0.1", 9999);
                ObjectOutputStream channelWrite = new ObjectOutputStream(serverSocket.getOutputStream());
                channelWrite.writeObject(new RequestDto("Login" + usrn, null));
                ObjectInputStream channelRead = new ObjectInputStream(serverSocket.getInputStream());
                try {
                    UserDto userDto = (UserDto) channelRead.readObject();
                    if ("Success".equals(userDto.getMessage())) {
                        Set<String> userDtos = userDto.getUsernames();
                        System.out.println(userDtos.toString());
                        Alert alert = new Alert(INFORMATION, "Active users:" + userDtos.toString());
                        alert.show();
                    }

                } catch (IOException | ClassNotFoundException exception) {
                    throw new RuntimeException(exception);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            mainStage.close();
            Alert alert = new Alert(ERROR, "User/pass invalid");
            alert.show();
        }
    }

    private Stage buildMainStage(ActionEvent actionEvent) {
        try {
            Stage secondStage = new Stage();
            FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene2 = new Scene(fxmlLoader2.load(), 800, 550);
            secondStage.setTitle("Hello " + UserInstance.getInstance().getUser());
            secondStage.setScene(scene2);

            return secondStage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logOut(ActionEvent actionEvent) {
        int len = "Welcome ".length() - 1;
        Stage currentStage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
        String title = currentStage.getTitle();
        String usrn = title.substring(len);
        UserInstance.getInstance().setLogged(false);
        currentStage.close();
        try {
            var serverSocket = new Socket("127.0.0.1", 9999);
            ObjectOutputStream channelWrite = new ObjectOutputStream(serverSocket.getOutputStream());
            channelWrite.writeObject(new RequestDto("Logout" + usrn, null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchByEngCap(ActionEvent actionEvent) {
        if (UserInstance.getInstance().isLogged()) {
            String engCap = capacityComboBox.getValue();
            try {
//                raceObservableList.setAll(raceService.showRacesByEngineSize(engCap));
                var list = service.showRacesByEngineSize(engCap).stream()
                        .map(val -> new domain.Race(val.getId(), val.getName(), val.getEngineCapacity()))
                        .toList();
                raceObservableList.setAll(list);
                raceListView.setItems(raceObservableList);
                System.out.println(raceListView);
            } catch (Exception e) {
                Alert alert = new Alert(ERROR, e.getMessage());
                alert.show();
            }
        } else {
            System.out.println("Not logged");
        }
    }

    public void addParticipant(ActionEvent actionEvent) {
        int id = 0;
        try {
//            id = participantService.getLastId() + 1;

        } catch (Exception e) {
            Alert alert2 = new Alert(ERROR, e.getMessage());
            alert2.show();
        }
        String teamName = teamNameParticipant.getText();
        String name = nameParticipant.getText();
        int idRace = 0;
        try {
            idRace = Integer.parseInt(idRaceParticipant.getText());

        } catch (NumberFormatException en) {
            Alert alertt = new Alert(ERROR, en.getMessage());
            alertt.show();
        }
        Participant e = new Participant(id, teamName, name, idRace);
        System.out.print(e);
        try {
            // participantService.addParticipant(e);
            //notifyServer
            Socket serverSocket = new Socket("127.0.0.1", 9999);
            ObjectOutputStream channelWrite = new ObjectOutputStream(serverSocket.getOutputStream());
            channelWrite.writeObject(new RequestDto("addParticipant", e));
            ObjectInputStream channelRead = new ObjectInputStream(serverSocket.getInputStream());
            try {
                ResponseDto responseDTO = (ResponseDto) channelRead.readObject();
                if ("Success".equals(responseDTO.getMessage())) {
                    List<Participant> participants = responseDTO.getParticipants();
                    participantObservableList.add(participants.get(0));
                   // participantListView.setItems(participantObservableList);
                    participantListView.refresh();
                }

            } catch (IOException | ClassNotFoundException exception) {
                throw new RuntimeException(exception);
            }

            channelRead.close();
            channelWrite.close();
            serverSocket.close();

        } catch (Exception re) {
            Alert alert1 = new Alert(ERROR, re.getMessage());
            alert1.show();
//        } catch (SQLException ex) {
//            Alert alert = new Alert(ERROR,ex.getMessage());
//            alert.show();
//        }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        participantObservableList = FXCollections.observableArrayList(participantService.getAll());
        var list = service.getAllParticipant().stream()
                .map(val -> new domain.Participant(val.getId(), val.getTeamName(), val.getName(), val.getRace().getId()))
                .toList();
        participantObservableList = FXCollections.observableArrayList(list);

        participantListView.setItems(participantObservableList);
    }
}