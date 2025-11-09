module com.example.fasttype {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fasttype to javafx.fxml;
    exports com.example.fasttype;
}