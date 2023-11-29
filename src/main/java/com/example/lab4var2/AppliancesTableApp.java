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
    private final CheckBox isDoubleCheckBox = new CheckBox("Is Double");
    private final ComboBox<String> appliancesTypeComboBox = new ComboBox<>();


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
        TableColumn<Appliances, Boolean> isDoubleColumn = getIsDoubleColumn();
        TableColumn<Appliances, String> appliancesTypeTableColumn = new TableColumn<>("Appliance Type");
        appliancesTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("applianceType"));


        table.getColumns().addAll(nameColumn, priceColumn, capacityColumn, companyColumn, isDoubleColumn, appliancesTypeTableColumn);
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
                new Label("Is Double:"),
                isDoubleCheckBox,
                new Label("Appliance Type:"),
                appliancesTypeComboBox,
                addButton
        );


        appliancesTypeComboBox.getItems().addAll("Washer", "Refregenerator");
        appliancesTypeComboBox.setValue("Washer");

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


    private static TableColumn<Appliances, Boolean> getIsDoubleColumn() {
        TableColumn<Appliances, Boolean> isDoubleColumn = new TableColumn<>("Is Double");
        isDoubleColumn.setCellValueFactory(cellData -> {
            Appliances appliance = cellData.getValue();
            if (appliance instanceof Refregenerator) {
                return new SimpleBooleanProperty(((Refregenerator) cellData.getValue()).getIsDouble());
            }
            return new SimpleBooleanProperty().asObject();
        });
        isDoubleColumn.setCellFactory(column -> new CheckBoxTableCell<Appliances, Boolean>() {
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
        return isDoubleColumn;
    }


    private void addAppliance() {
        try {
            String name = nameTextField.getText();
            String company = companyTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            int capacity = Integer.parseInt(capacityTextField.getText());
            String applianceType = appliancesTypeComboBox.getValue();


            if (isDoubleCheckBox.isSelected()) {
                if ("washer".equalsIgnoreCase(applianceType)) {
                    throw new IllegalArgumentException("Invalid combination: 'isDouble' cannot be selected for Washer.");
                }
                boolean isDouble = true;
                applianceType = "Refregenerator";
                appliances.add(new Refregenerator(name, company, price, capacity, isDouble, 3000, applianceType));
            } else {
                if ("washer".equalsIgnoreCase(applianceType)) {
                    appliances.add(new Washer(name, company, price, capacity, 3000, applianceType));
                } else {
                    throw new IllegalArgumentException("Invalid combination: 'isDouble' must be selected for Refregenerator.");
                }
            }

            nameTextField.clear();
            priceTextField.clear();
            companyTextField.clear();
            capacityTextField.clear();
            isDoubleCheckBox.setSelected(false);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid number.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid combination");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    private void addAllAppliancesFromArray() {
        appliances.addAll(getAppliancesFromArray());
    }

    private ObservableList<Appliances> getAppliancesFromArray() {
        ObservableList<Appliances> appliancesArray = FXCollections.observableArrayList();
        appliancesArray.add(new Washer("F2V34520", "Samsung", 28000, 6, 3000, "Washer"));
        appliancesArray.add(new Refregenerator("RB-33 J1201SA", "Samsung", 39000, 50, true, 5000, "Refregenerator"));
        appliancesArray.add(new Washer("BV-4358JH", "Samsung", 28000, 6, 3000, "Washer"));
        appliancesArray.add(new Washer("Clean&Fresh", "Panasonic", 32000, 6, 3000, "Washer"));
        appliancesArray.add(new Refregenerator("RL-58 H6020DA", "Samsung", 39000, 50, false, 5000, "Refregenerator"));
        appliancesArray.add(new Refregenerator("Rmod32 comfort+", "Полюс", 35000, 40, true, 5000, "Refregenerator"));
        appliancesArray.add(new Washer("Bubbles3000", "IdeaProduction", 30000, 9, 3000, "Washer"));
        appliancesArray.add(new Refregenerator("RN-21 K5433YU", "TEKA", 24000, 35, true, 5000, "Refregenerator"));
        appliancesArray.add(new Washer("CST G2701", "Candy", 21500, 6, 3000, "Washer"));
        appliancesArray.add(new Washer("MSD 615", "Indesit", 15000, 6, 3000, "Washer"));
        appliancesArray.add(new Refregenerator("GA 71234", "LG", 50000, 70, false, 5000, "Refregenerator"));
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