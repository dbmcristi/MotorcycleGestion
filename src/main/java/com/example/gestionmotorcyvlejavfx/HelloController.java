package com.example.gestionmotorcyvlejavfx;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import repository.ParticipantRepo;
import repository.RaceRepo;
import repository.RepositoryException;
import repository.UserRepo;
import service.ParticipantService;
import service.RaceService;
import service.UserService;
import utils.JdbcUtils;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class HelloController {
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
    private Properties properties;
    private UserRepo userRepo;
    private UserService userService;
    private ParticipantRepo participantRepo;
    private ParticipantService participantService;
    private RaceRepo raceRepo;
    private RaceService raceService;
    public ListView<Participant> participantListView; //observator
    @FXML
    public ComboBox<String> capacityComboBox;
    @FXML
    private final ObservableList<Participant> participantObservableList =
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
        properties = new Properties();
        try {
            properties.load(new FileReader("GestionMotorcyvleJavFX\\bd.config"));
        } catch (FileNotFoundException e) {
            //     log.error("Can't find bd.config");
            return;
        } catch (IOException e) {
            //    log.error("Can't load bd.config");
            return;
        }

        userRepo = new UserRepo(properties);
        raceRepo = new RaceRepo(properties);
        participantRepo = new ParticipantRepo(properties);

        userService = new UserService(userRepo);
        raceService = new RaceService(raceRepo);
        participantService = new ParticipantService(participantRepo, raceRepo);
    }

    @FXML
    protected void printParticipant() {
        if (UserInstance.getInstance().isLogged()) {
            participantObservableList.setAll(participantService.getAll());
            participantListView.setItems(participantObservableList);
        } else {
            System.out.println("Not logged");
        }
    }

    @FXML
    public void printUser() {

        if (UserInstance.getInstance().isLogged()) {
            userObservableList.setAll(userService.getAll());
            userListView.setItems(userObservableList);
        } else {
            System.out.println("Not logged");
        }
    }

    @FXML
    public void printRace() {

        if (UserInstance.getInstance().isLogged()) {
            raceObservableList.setAll(raceService.getAll());
            raceListView.setItems(raceObservableList);
        } else {
            System.out.println("Not logged");
        }
    }

    public void getParticipantsByRaceID(ActionEvent actionEvent) {
        if (UserInstance.getInstance().isLogged()) {
            try {
                int raceId = Integer.parseInt(idRaceSearched.getText());
                Integer result = participantService.getParticipantSize(raceId);
                nrPartsByIdRace.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getParticipantsByTeamName(ActionEvent actionEvent) {
        if (UserInstance.getInstance().isLogged()) {
            String teamName = teamNameFXID.getText();
            participantObservableList.setAll(participantService.searchByTeamName(teamName));
            participantListView.setItems(participantObservableList);
        }
    }

    public void checkIfIsLoggedNewStage(ActionEvent actionEvent) {


        String usrn = userNameNewStage.getText();
        String pwsd = passwordNewStage.getText();
        boolean isLogged = userService.isLogged(usrn, pwsd);

        System.out.println(isLogged);
        UserInstance.getInstance().setLogged(isLogged);

        Stage mainStage = buildMainStage();

        if (isLogged) {
            UserInstance.getInstance().setUser(usrn);
            mainStage.setTitle("Welcome " + usrn);
            mainStage.show();
        } else {
            mainStage.close();
            Alert alert = new Alert(ERROR, "User/pass invalid");
            alert.show();
        }
    }

    private Stage buildMainStage() {

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
        UserInstance.getInstance().setLogged(false);
        Stage currentStage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
        currentStage.close();
    }

    public void searchByEngCap(ActionEvent actionEvent) {
        if (UserInstance.getInstance().isLogged()) {
            String engCap = capacityComboBox.getValue();
            try {
                raceObservableList.setAll(raceService.showRacesByEngineSize(engCap));
                raceListView.setItems(raceObservableList);
                System.out.println(raceListView);
            } catch (RepositoryException e) {
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
            id = participantService.getLastId()+1;
        } catch (RepositoryException e) {
            Alert alert2 = new Alert(ERROR,e.getMessage());
            alert2.show();
        }
        String teamName = teamNameParticipant.getText();
        String name = nameParticipant.getText();
        int idRace=0;
        try {
              idRace = Integer.parseInt(idRaceParticipant.getText());

        } catch (NumberFormatException en) {
            Alert alertt = new Alert(ERROR,en.getMessage());
            alertt.show();
        }
        Participant e = new Participant(id, teamName, name, idRace);
        try {
            participantService.addParticipant(e);
        } catch (RepositoryException re) {
            Alert alert1 = new Alert(ERROR,re.getMessage());
            alert1.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(ERROR,ex.getMessage());
            alert.show();
        }
    }
}
