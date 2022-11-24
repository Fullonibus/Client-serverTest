package com.example.nurabotaypls;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller {
    public File file;
    private Stage stage;
    static final String HOST = "127.0.0.1";
    static final int PORT = 8007;
    public String pack;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField desciption_text_field;

    @FXML
    private TextField name_text_field;

    @FXML
    private TextField textField1;
    @FXML
    private ImageView image_field;

    @FXML
    private Label welcomeText;

    @FXML
    private TableColumn<TableRow, String> description_column;
    @FXML
    private TableColumn<TableRow, String> name_column;

    @FXML
    private TableColumn<TableRow, Integer> id_column;

    @FXML
    private TableView<TableRow> tableView;
    @FXML
    void doFileChoose(ActionEvent event) throws Exception {
        FileChooser fileopen = new FileChooser();
         file = fileopen.showOpenDialog(stage);
        textField1.setText(file.getPath());
    }

    @FXML
    void onInputButtonClick(ActionEvent event) throws Exception {
        Connection connection = new Connection();
        connection.sendPack(file, name_text_field.getText(), desciption_text_field.getText());
        connection.refreshTableView(tableView);
    }

    @FXML
    void initialize() throws Exception {
        id_column.setCellValueFactory(new PropertyValueFactory<>("id_column"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name_column"));
        description_column.setCellValueFactory(new PropertyValueFactory<>("description_column"));
        Connection connection = new Connection();
        connection.refreshTableView(tableView);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    void onMouseClicked(MouseEvent event) throws Exception {
        System.out.println("Clicked");
        System.out.println(tableView.getSelectionModel().getSelectedItem().id_column.toString());
        Connection connection = new Connection();
        connection.setImageFromServer(tableView.getSelectionModel().getSelectedItem().id_column, image_field);
    }
    @FXML
    void onMousePressed(MouseEvent event) {
        System.out.println("Pressed");

    }

}
