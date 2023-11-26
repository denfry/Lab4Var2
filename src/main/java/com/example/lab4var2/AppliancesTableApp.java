package com.example.lab4var2;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

public class AppliancesTableApp extends Application {
    private final TableView<Appliances> table = new TableView<>();
    private final ObservableList<Appliances> appliances = FXCollections.observableArrayList();

    private boolean ascendingSort = true;

    private final TextField nameTextField = new TextField();
    private final TextField priceTextField = new TextField();
    private final TextField companyTextField = new TextField();
    private final TextField capacityTextField = new TextField();
    private final CheckBox isOnCheckBox = new CheckBox("Is On");


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Appliances Table App");

        TableColumn<Appliances, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Appliances, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Appliances, Integer> capacityColumn = getCapacityColumn();

        TableColumn<Appliances, String> companyColumn = new TableColumn<>("Company");
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        TableColumn<Appliances, Boolean> isOnColumn = getIsOnColumn();


        table.getColumns().addAll(nameColumn, priceColumn, capacityColumn, companyColumn, isOnColumn);
        table.setItems(appliances);

        Button addButton = new Button("Add Appliance");
        addButton.setOnAction(e -> addAppliance());

        Button addFromArrayButton = new Button("Add Appliances from Array");
        addFromArrayButton.setOnAction(e -> addAllAppliancesFromArray());

        Button sortButton = new Button("Sort by Price");
        sortButton.setOnAction(e -> sortAppliancesByPrice());


        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(sortButton);
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(
                new Label("Name:"),
                nameTextField,
                new Label("Price:"),
                priceTextField,
                new Label("Company:"),
                companyTextField,
                new Label("Capacity:"),
                capacityTextField,
                new Label("Is On:"),
                isOnCheckBox,
                addButton
        );

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(table, inputBox, addFromArrayButton, buttonBox);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableColumn<Appliances, Integer> getCapacityColumn() {
        TableColumn<Appliances, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        capacityColumn.setCellFactory(column -> new TableCell<Appliances, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item));
                    int capacityValue = item;

                    // Здесь задайте условия для изменения цвета фона в зависимости от значения capacity
                    if (capacityValue >= 20) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        setStyle("-fx-background-color: lightcoral;");
                    }
                }
            }
        });

        return capacityColumn;
    }


    private static TableColumn<Appliances, Boolean> getIsOnColumn() {
        TableColumn<Appliances, Boolean> isOnColumn = new TableColumn<>("Is On");
        isOnColumn.setCellValueFactory(cellData -> {
            Appliances appliance = cellData.getValue();
            if (appliance instanceof Refregenerator) {
                return new SimpleBooleanProperty(((Refregenerator) cellData.getValue()).getIsOn());
            }
            return new SimpleBooleanProperty().asObject();
        });
        isOnColumn.setCellFactory(column -> new CheckBoxTableCell<Appliances, Boolean>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setAlignment(Pos.CENTER);
                setAlignment(Pos.CENTER);

                checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                    if (isSelected) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        setStyle("-fx-background-color: lightcoral;");
                    }
                });
            }

            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                    if (!item) {
                        setStyle("-fx-background-color: lightcoral;");
                    } else {
                        setStyle("-fx-background-color: lightgreen;");
                    }
                }
            }
        });
        return isOnColumn;
    }


    private void addAppliance() {
        try {
            nameTextField.clear();
            priceTextField.clear();
            companyTextField.clear();
            capacityTextField.clear();
            isOnCheckBox.setSelected(false);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid numeric value for Price or Screen Size.");
            alert.showAndWait();
        }
    }


    private void addAllAppliancesFromArray() {
        appliances.addAll(getAppliancesFromArray());
    }

    private ObservableList<Appliances> getAppliancesFromArray() {
        ObservableList<Appliances> appliancesArray = FXCollections.observableArrayList();
        appliancesArray.add(new Washer("F2V34520", "Samsung", 28000, 6, false, 3000));
        appliancesArray.add(new Refregenerator("RB-33 J1201SA", "Samsung", 39000, 50, true, 5000));
        appliancesArray.add(new Washer("BV-4358JH", "Samsung", 28000, 6, false, 3000));
        appliancesArray.add(new Washer("Clean&Fresh", "Panasonic", 32000, 6, false, 3000));
        appliancesArray.add(new Refregenerator("RL-58 H6020DA", "Samsung", 39000, 50, false, 5000));
        appliancesArray.add(new Refregenerator("Rmod32 comfort+", "Полюс", 35000, 40, true, 5000));
        appliancesArray.add(new Washer("Bubbles3000", "IdeaProduction", 30000, 9, true, 3000));
        appliancesArray.add(new Refregenerator("RN-21 K5433YU", "TEKA", 24000, 35, true, 5000));
        appliancesArray.add(new Washer("CST G2701", "Candy", 21500, 6, false, 3000));
        appliancesArray.add(new Washer("MSD 615", "Indesit", 15000, 6, true, 3000));
        appliancesArray.add(new Refregenerator("GA 71234", "LG", 50000, 70, false, 5000));
        return appliancesArray;
    }

    private void sortAppliancesByPrice() {
        ascendingSort = !ascendingSort;
        Comparator<Appliances> comparator = ascendingSort
                ? Comparator.comparingDouble(Appliances::getPrice)
                : (r1, r2) -> Double.compare(r2.getPrice(), r1.getPrice());

        appliances.sort(comparator);
    }
}