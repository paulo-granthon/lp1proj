package org.openjfx.lpi.controller.utils;

import java.util.Arrays;
import java.util.LinkedList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;

public class LookupTextField<T extends HasName> extends TextField {

    @SuppressWarnings("unchecked") public static <T extends HasName, P extends Pane> LookupTextField<T> overrideTextField (TextField textField, T[] items) {
        LookupTextField<T> lookupTextField = new LookupTextField<T>(items);
        ((P)textField.getParent()).getChildren().set(
            ((P)textField.getParent()).getChildren().indexOf(textField),
            lookupTextField
        );
        return lookupTextField;
    }

    private final ObservableList<T> suggestions = FXCollections.observableList(new LinkedList<T>());
    private final ObjectProperty<T> selectedItem;
    private final ObservableList<T> listViewSuggestions;
    private ListView<T> suggestionsListView;

    public LookupTextField (T[] suggestions) { this(new LinkedList<T>(Arrays.asList(suggestions))); }
    public LookupTextField (LinkedList<T> suggestions) {
        this.suggestions.setAll(suggestions);
        this.listViewSuggestions = FXCollections.observableArrayList();
        this.selectedItem = new SimpleObjectProperty<>();

        suggestionsListView = new ListView<>(this.listViewSuggestions);
        suggestionsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        Popup popup = new Popup();
        popup.getContent().add(suggestionsListView);

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) if (getText().isEmpty()) {
                    updateListViewSuggestions();
                    showPopup(popup);
                }                    
                else popup.hide();
            }
        });

        textProperty().addListener((observable, oldValue, newValue) -> {
            T selItem = selectedItem.get();
            if (newValue.isBlank() || (selItem != null && newValue.equals(selItem.getName()))) {
                setStyle("-fx-text-fill: white;");
            } else {
                selectedItem.set(null);
                setStyle("-fx-text-fill: red;");
            }
            if (focusedProperty().get()) {
                suggestionsListView.getItems().clear();
                for (T suggestion : this.suggestions) {
                    if (!suggestion.getName().toLowerCase().contains(newValue.toLowerCase())) continue;
                    suggestionsListView.getItems().add(suggestion);
                };        
                if (!showPopup(popup)) {
                    popup.hide();
                }
            }
        });

        suggestionsListView.setOnMouseClicked(event -> {
            T selectedItem = suggestionsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                setText(selectedItem.getName());
                this.selectedItem.set(selectedItem);
                setStyle("-fx-text-fill: white;");
            }
            popup.hide();
        });
    }

    private boolean showPopup (Popup popup) {
        if (suggestionsListView.getItems().isEmpty()) return false;
        Bounds bounds = localToScreen(getBoundsInLocal());
        double popupY = bounds.getMaxY();
        double popupHeight = 24 * suggestionsListView.getItems().size();
        suggestionsListView.setMaxHeight(popupHeight);
        suggestionsListView.setMinHeight(popupHeight);
        suggestionsListView.setPrefHeight(popupHeight);
        suggestionsListView.refresh();
        popup.show(this, bounds.getMinX(), popupY);
        return true;
    }

    private void updateListViewSuggestions() {
        suggestionsListView.getItems().clear();
        for (T item : this.suggestions) {
            suggestionsListView.getItems().add(item);
        }
    }

    public T getSelectedItem() {
        return selectedItem.get();
    }

    public void clear() {
        selectedItem.set(null);
        setText("");
    }

    public ObjectProperty<T> selectedItemProperty() {
        return selectedItem;
    }

    public void updateSuggestions (T[] newSuggestions) {
        suggestions.setAll(newSuggestions);
    }

    public void addSuggestion (T suggestion) {
        this.suggestions.add(suggestion);
    }

    public void removeSuggestion (T suggestion) {
        this.suggestions.remove(suggestion);
    }
}
