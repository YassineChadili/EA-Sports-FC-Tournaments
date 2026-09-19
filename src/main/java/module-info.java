module com.yassine.easportsfc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.yassine.easportsfc to javafx.fxml;
    exports com.yassine.easportsfc;
}