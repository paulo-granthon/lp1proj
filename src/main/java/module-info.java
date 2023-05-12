module org.openjfx.lpi {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive org.controlsfx.controls;
    requires transitive java.sql;

    opens org.openjfx.lpi.controller to javafx.fxml;
    opens org.openjfx.lpi.data to javafx.base;
    opens org.openjfx.lpi to javafx.base;
    
    exports org.openjfx.lpi;
}
