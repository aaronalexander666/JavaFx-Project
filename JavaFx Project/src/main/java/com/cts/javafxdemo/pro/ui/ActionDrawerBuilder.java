package com.cts.javafxdemo.pro.ui;

import com.cts.javafxdemo.models.Booking;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Builds a contextual action drawer for booking operations.
 * Uses only standard JavaFX nodes and CSS classes for light-weight rendering.
 */
public class ActionDrawerBuilder {

    private final VBox container;

    public ActionDrawerBuilder(VBox container) {
        this.container = container;
    }

    public void clear() {
        container.getChildren().clear();
        Label placeholder = new Label("👈 Select a booking to manage");
        placeholder.getStyleClass().add("status-pill");
        placeholder.setPadding(new Insets(20));
        container.getChildren().add(placeholder);
    }

    public void render(Booking booking, Runnable onCheckIn, Runnable onCheckOut, Runnable onPrint) {
        container.getChildren().clear();

        String status = safeText(booking.getStatus(), "Unknown");
        String vipTier = safeText(extractVipTier(booking), "Standard");
        String folio = safeText(extractFolio(booking), "Pending");
        String notes = safeText(extractNotes(booking), "No special notes.");

        Label header = new Label(safeText(booking.getCustomerName(), "Guest"));
        header.getStyleClass().add("guest-header");

        Label vipBadge = new Label(formatVipBadge(vipTier));
        vipBadge.getStyleClass().addAll("vip-" + vipTier.toLowerCase().replaceAll("\\s+", ""));

        Label statusLabel = new Label(status);
        statusLabel.getStyleClass().addAll("status-pill", mapStatusCss(status));

        VBox infoBox = new VBox(8);
        infoBox.setPadding(new Insets(10, 0, 10, 0));
        infoBox.getChildren().addAll(
                new Label("Room: " + safeText(booking.getRoomNumber(), "TBD")),
                new Label("Folio: " + folio),
                new Label("Check-In: " + safeText(formatDate(booking.getCheckInDate()), "TBD")),
                new Label("Check-Out: " + safeText(formatDate(booking.getCheckOutDate()), "TBD"))
        );

        Button checkInButton = createActionButton("Check In", "action-button-checkin", onCheckIn);
        Button checkOutButton = createActionButton("Check Out", "action-button-checkout", onCheckOut);
        Button printButton = createActionButton("Print Folio", "action-button-print", onPrint);

        boolean isConfirmed = "Confirmed".equalsIgnoreCase(status);
        boolean isCheckedIn = "Checked-In".equalsIgnoreCase(status);
        boolean isCheckedOut = "Checked-Out".equalsIgnoreCase(status);

        setButtonVisibility(checkInButton, isConfirmed);
        setButtonVisibility(checkOutButton, isCheckedIn);
        setButtonVisibility(printButton, isCheckedIn || isCheckedOut);

        Label notesLabel = new Label("Special Notes");
        notesLabel.setStyle("-fx-font-weight: bold;");

        TextArea notesArea = new TextArea(notes);
        notesArea.setEditable(false);
        notesArea.setWrapText(true);
        notesArea.setPrefRowCount(4);
        notesArea.getStyleClass().add("notes-area");

        HBox headerBox = new HBox(10, header, vipBadge);
        HBox.setHgrow(header, Priority.ALWAYS);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        container.getChildren().addAll(
                headerBox,
                statusLabel,
                new Separator(),
                infoBox,
                new Separator(),
                checkInButton,
                checkOutButton,
                printButton,
                new Separator(),
                notesLabel,
                notesArea
        );
    }

    private Button createActionButton(String text, String cssClass, Runnable action) {
        Button button = new Button(text);
        button.getStyleClass().addAll("action-button", cssClass);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> action.run());
        return button;
    }

    private void setButtonVisibility(Button button, boolean visible) {
        button.setVisible(visible);
        button.setManaged(visible);
    }

    private String mapStatusCss(String status) {
        return switch (status) {
            case "Confirmed" -> "status-confirmed";
            case "Checked-In" -> "status-checked-in";
            case "Checked-Out" -> "status-checked-out";
            case "Pending" -> "status-pending";
            case "Cancelled" -> "status-cancelled";
            default -> "status-pill";
        };
    }

    private String formatVipBadge(String vipTier) {
        return switch (vipTier.toLowerCase()) {
            case "gold" -> "★ GOLD VIP ★";
            case "silver" -> "☆ SILVER VIP ☆";
            default -> "Standard Guest";
        };
    }

    private String safeText(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private String extractVipTier(Booking booking) {
        return invokeStringGetter(booking, "getVipTier", "Standard");
    }

    private String extractFolio(Booking booking) {
        return invokeStringGetter(booking, "getFolioNumber", "Pending");
    }

    private String extractNotes(Booking booking) {
        return invokeStringGetter(booking, "getSpecialNotes", "No special notes.");
    }

    private String invokeStringGetter(Booking booking, String methodName, String fallback) {
        try {
            var method = booking.getClass().getMethod(methodName);
            Object result = method.invoke(booking);
            return result instanceof String ? (String) result : fallback;
        } catch (ReflectiveOperationException | RuntimeException ex) {
            return fallback;
        }
    }

    private String formatDate(Object dateValue) {
        return dateValue == null ? "TBD" : dateValue.toString();
    }
}
