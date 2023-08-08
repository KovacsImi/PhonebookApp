package com.example.demo8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class HelloController {

    @FXML
    TableView table;
    @FXML
    TextField inputLastname;
    @FXML
    TextField inputFirstname;
    @FXML
    TextField addnewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;

    private final ObservableList<Human> data =
            FXCollections.observableArrayList(
              new Human("Gábor", "Dénes", "gabordenes@pelda.hu"));


}