package com.cts.javafxdemo.cell;

import com.cts.javafxdemo.models.Booking;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class StatusCellFactory implements Callback<TableColumn<Booking, String>, TableCell<Booking, String>> {

    @Override
    public TableCell<Booking, String> call(TableColumn<Booking, String> param) {
        return new TableCell<>() {
            private final Label label = new Label();

            {
                label.setAlignment(Pos.CENTER);
                label.getStyleClass().add("status-badge");
                setGraphic(label);
            }

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null || status.isBlank()) {
                    setGraphic(null);
                    return;
                }

                label.setText(status);
                label.getStyleClass().removeAll("status-pending", "status-confirmed", "status-checked-in", "status-checked-out", "status-cancelled");

                switch (status.toLowerCase()) {
                    case "pending" -> label.getStyleClass().add("status-pending");
                    case "confirmed" -> label.getStyleClass().add("status-confirmed");
                    case "checked in", "checked_in" -> label.getStyleClass().add("status-checked-in");
                    case "checked out", "checked_out" -> label.getStyleClass().add("status-checked-out");
                    case "cancelled" -> label.getStyleClass().add("status-cancelled");
                    default -> label.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white;");
                }
                setGraphic(label);
            }
        };
    }
}
