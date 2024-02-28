module org.techelevator.snakes {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.techelevator.snakes to javafx.fxml;
    exports org.techelevator.snakes;
}