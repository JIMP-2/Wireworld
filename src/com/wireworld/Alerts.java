package com.wireworld;

import javafx.scene.control.Alert;

public class Alerts {

    static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Wysokość i szerokość powinny być liczbami większymi od 0.");
        alert.showAndWait();
    }
    static void showAlertInteger() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(" Wysokość i szerokość powinny być liczbami całkowitymi.");
        alert.showAndWait();
    }
}
