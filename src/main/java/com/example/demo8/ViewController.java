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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.util.Callback;


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
    Button addNewContactButton;
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

    @FXML
    AnchorPane anchorPane;

    @FXML
    SplitPane splitPane;

    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";
    private final ObservableList<Human> data =
            FXCollections.observableArrayList();
    DataBase dataBase = new DataBase();


    @FXML
    private void addContact(ActionEvent event) {
        Pattern pattern = Pattern.compile(".+@.+\\..+");
        Matcher matcher = pattern.matcher(inputEmail.getText());
        if (matcher.find()) {
            Human newHuman = new Human(inputFirstname.getText(), inputLastname.getText(), inputEmail.getText());
            dataBase.addContact(newHuman);
            data.add(newHuman);
            inputFirstname.clear();
            inputLastname.clear();
            inputEmail.clear();
        } else {
            alert("Please give a proper e-mail address!");
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
        } else {
            alert("Please give a proper file name!");
        }

    }

    private void setMenuData() {
        menuPane.setMinWidth(200);
        menuPane.setMaxWidth(200);

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
                            //contactPane.prefHeightProperty().bind(anchorPane.widthProperty());
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
        TableColumn removeCol = new TableColumn("Törlés");
        removeCol.setMinWidth(100);
        removeCol.setCellFactory(TextFieldTableCell.forTableColumn());


        Callback<TableColumn<Human, String>, TableCell<Human, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<Human, String> param) {
                        return new TableCell<Human, String>() {
                            final Button button = new Button("Törlés");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction((ActionEvent event) ->
                                    {
                                        Human person = getTableView().getItems().get(getIndex());
                                        data.remove(person);
                                        dataBase.removeContact(person);
                                    });
                                    setGraphic(button);
                                    setText(null);
                                }
                            }
                        };
                    }
                };

        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(130);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Human, SimpleStringProperty>("lastName"));

        // Event handler
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Human, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Human, String> t) {
                        Human actualHuman = (Human) t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        actualHuman.setLastName(t.getNewValue());
                        dataBase.updateContact(actualHuman);
                    }
                }
        );


        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(130);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Human, String>("firstName"));

        firstNameCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Human, String>>) t -> {
                    Human actualHuman = (Human) t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    actualHuman.setFirstName(t.getNewValue());
                    dataBase.updateContact(actualHuman);

                }
        );

        TableColumn emailCol = new TableColumn("E-mail cím");
        emailCol.setMinWidth(220);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Human, String>("email"));

        emailCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Human, String>>) t -> {

                    Human actualHuman = (Human) t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    actualHuman.setEmail(t.getNewValue());
                    dataBase.updateContact(actualHuman);
                }
        );

        removeCol.setCellFactory(cellFactory);
        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol, removeCol);
        data.addAll(dataBase.getAllContacts());
        table.setItems(data);

    }

    private void alert(String text) {
        splitPane.setDisable(true);
        splitPane.setOpacity(0.4);

        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vBox = new VBox(label, alertButton);
        vBox.setAlignment(Pos.CENTER);

        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                splitPane.setDisable(false);
                splitPane.setOpacity(1);
                vBox.setVisible(false);
            }
        });

        anchorPane.getChildren().add(vBox);
        anchorPane.setTopAnchor(vBox, 300.0);
        anchorPane.setLeftAnchor(vBox, 300.0);


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();

    }
}