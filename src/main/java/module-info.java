module org.openjfx.lpi {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive org.apache.commons.text;
    requires transitive java.sql;

    opens org.openjfx.lpi.controller to javafx.fxml;

    opens org.openjfx.lpi.controller.utils to javafx.base;
    opens org.openjfx.lpi.model.relation to javafx.base;
    opens org.openjfx.lpi.model to javafx.base;
    opens org.openjfx.lpi to javafx.base;

    exports org.openjfx.lpi;
}
