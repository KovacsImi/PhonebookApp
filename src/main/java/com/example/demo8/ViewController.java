package com.example.demo8;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class ViewController implements Initializable {

    @FXML
    TableView table;
    @FXML
    TextField inputLastname;
    @FXML
    TextField inputFirstname;
    @FXML
    TextField inputEmail;
    @FXML
    TextField addnewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;

    @FXML
    TextField inputExportName;

    @FXML
    Button exportButton;

    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";


    private final ObservableList<Human> data =
            FXCollections.observableArrayList(
                    new Human("Gábor", "Dénes", "gabordenes@pelda.hu"),
                    new Human("Gábor2", "Dénes2", "gabordenes2@pelda.hu"));

    @FXML
    private void addContact(ActionEvent event) {
        Pattern pattern = Pattern.compile(".+@.+\\..+");
        Matcher matcher = pattern.matcher(inputEmail.getText());

        if (matcher.find()) {
            data.add(new Human(inputFirstname.getText(), inputLastname.getText(), inputEmail.getText()));
            inputFirstname.clear();
            inputLastname.clear();
            inputEmail.clear();
        }
    }

    @FXML
    private void exportList() {
        String fileName = inputExportName.getText();
        //fileName = fileName.trim();
        fileName = fileName.replace("\\s+", "");
        if (!fileName.isEmpty()) {
            PdfGeneration pdfGeneration = new PdfGeneration();
            pdfGeneration.pdfGeneration(fileName, data);
            inputExportName.clear();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();


    }


    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);

        Node contactsNode = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/contacts.png"))));
        Node exportNode = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/export.png"))));

        nodeItemA.setExpanded(true);

        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, contactsNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);


        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (selectedMenu != null) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            try {
                                selectedItem.setExpanded(true);
                            } catch (Exception e) {
                            }
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            exportPane.setVisible(true);
                            contactPane.setVisible(false);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }


        });

    }

    private void setTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Human, SimpleStringProperty>("lastName"));

        // Event handler
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Human, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Human, String> t) {
                        ((Human) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());

                    }
                }
        );


        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Human, String>("firstName"));

        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Human, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Human, String> t) {
                        ((Human) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());

                    }
                }
        );

        TableColumn emailCol = new TableColumn("E-mail cím");
        emailCol.setMinWidth(200);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Human, String>("email"));

        emailCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Human, String>>) t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setEmail(t.getNewValue())
        );

        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol);
        table.setItems(data);
    }
}