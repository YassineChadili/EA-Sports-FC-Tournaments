module com.yassine.easportsfc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.yassine.easportsfc to javafx.fxml;
    exports com.yassine.easportsfc;
}