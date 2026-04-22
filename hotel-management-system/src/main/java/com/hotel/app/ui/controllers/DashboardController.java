// src/main/java/com/hotel/app/ui/controllers/DashboardController.java
package com.hotel.app.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.hotel.app.modules.inventory.Room;
import com.hotel.app.modules.inventory.RoomInventory;

public class DashboardController {

    // =========================================================================
    // FXML-INJECTED FIELDS (MUST MATCH fx:id EXACTLY)
    // =========================================================================
    
    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, String> roomNumberColumn;
    @FXML private TableColumn<Room, String> typeColumn;
    @FXML private TableColumn<Room, String> statusColumn;
    @FXML private TableColumn<Room, String> priceColumn;
    
    @FXML private Label lastUpdatedLabel;
    @FXML private Label roomCountLabel;
    @FXML private ProgressBar syncProgress;
    
    @FXML private Button addRoomButton;
    @FXML private Button editRoomButton;
    @FXML private Button deleteRoomButton;
    @FXML private Button refreshButton;
    
    @FXML private Label statusLabel;
    @FXML private Label errorLabel;
    
    // =========================================================================
    // INITIALIZATION (UDGP M2: Lifecycle boundary - UI setup only)
    // =========================================================================
    
    @FXML
    public void initialize() {
        // M3: Defensive null checks (FXML injection can fail silently)
        assert roomTable != null : "roomTable injection failed - check fx:id";
        assert roomNumberColumn != null : "roomNumberColumn injection failed";
        
        // M3: Set cellValueFactory (REQUIRED - FXML cannot bind to Java methods)
        setupColumnBindings();
        
        // M2: Connect UI to domain layer (RoomInventory singleton)        bindInventoryToTable();
        
        // M5: Initial freshness timestamp
        updateFreshnessIndicator();
        
        // M4: Button state management (disable edit/delete until selection)
        setupButtonStates();
    }
    
    private void setupColumnBindings() {
        // UDGP M3: Explicit binding - no magic strings
        roomNumberColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getRoomNumber()));
        
        typeColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getType().name()));
        
        statusColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getStatus().name()));
        
        priceColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                "$" + cellData.getValue().getPricePerNight().toString()));
    }
    
    private void bindInventoryToTable() {
        // M2: ObservableList binding - auto-refreshes when inventory changes
        ObservableList<Room> rooms = RoomInventory.getInstance().getAllRooms();
        roomTable.setItems(rooms);
        
        // M5: Update freshness when data changes
        rooms.addListener((javafx.collections.ListChangeListener<Room>) change -> 
            updateFreshnessIndicator());
    }
    
    private void updateFreshnessIndicator() {
        String timestamp = DateTimeFormatter.ISO_INSTANT
            .format(Instant.now().minusSeconds(2)); // Simulate "2s ago"
        lastUpdatedLabel.setText("Last updated: " + timestamp);
        roomCountLabel.setText("Rooms: " + roomTable.getItems().size());
    }
    
    private void setupButtonStates() {
        // M4: Fallback-first - disable actions until valid selection
        editRoomButton.disableProperty().bind(
            roomTable.getSelectionModel().selectedItemProperty().isNull());
        deleteRoomButton.disableProperty().bind(            roomTable.getSelectionModel().selectedItemProperty().isNull());
    }
    
    // =========================================================================
    // ACTION HANDLERS (UDGP M2: Creation/Update/Delete lifecycle separation)
    // =========================================================================
    
    @FXML
    private void handleAddRoom() {
        statusLabel.setText("Adding room...");
        // TODO: Open dialog, call RoomInventory.addRoom(), handle exceptions
        statusLabel.setText("Ready");
    }
    
    @FXML
    private void handleEditRoom() {
        Room selected = roomTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            statusLabel.setText("Editing room " + selected.getRoomNumber());
            // TODO: Open dialog, call RoomInventory.updateRoom()
        }
    }
    
    @FXML
    private void handleDeleteRoom() {
        Room selected = roomTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // M4: Confirm before destructive action
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
                "Delete room " + selected.getRoomNumber() + "?");
            if (confirm.showAndWait().orElse(false) == ButtonType.OK) {
                RoomInventory.getInstance().deleteRoom(selected.getId());
                statusLabel.setText("Room deleted");
            }
        }
    }
    
    @FXML
    private void handleRefresh() {
        syncProgress.setVisible(true);
        syncProgress.setProgress(-1); // Indeterminate
        
        // Simulate async reload (in production: reload from RoomRepositoryJson)
        javafx.concurrent.Task<Void> refreshTask = new javafx.concurrent.Task<>() {
            @Override protected Void call() {
                // Force rebind to trigger UI refresh
                roomTable.refresh();
                return null;
            }
        };        refreshTask.setOnSucceeded(e -> {
            syncProgress.setVisible(false);
            updateFreshnessIndicator();
            statusLabel.setText("Refreshed");
        });
        refreshTask.setOnFailed(e -> {
            syncProgress.setVisible(false);
            errorLabel.setText("Refresh failed: " + e.getSource().getException().getMessage());
            errorLabel.setVisible(true);
        });
        new Thread(refreshTask).start();
    
}