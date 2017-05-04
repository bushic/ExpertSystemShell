package model;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.input.*;

import java.util.ArrayList;

public class RuleCell extends ListCell<Rule> {

    private static final DataFormat dataFormat = new DataFormat("Rule");

    public RuleCell() {
        ListCell thisCell = this;

        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(dataFormat,getItem());
            dragboard.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasContent(dataFormat)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasContent(dataFormat)) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasContent(dataFormat)) {
                setOpacity(1);
            }
        });

        setOnDragDropped(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasContent(dataFormat)) {
                Rule temp = (Rule) db.getContent(dataFormat);
                ObservableList<Rule> items = getListView().getItems();
                int draggedIdx = 0;
                for(int i = 0; i < items.size(); i++){
                    if (items.get(i).toString().equals(temp.toString())){
                        draggedIdx = i;
                        break;
                    }
                }
                int thisIdx = items.indexOf(getItem());

                items.remove(draggedIdx);
                items.add(thisIdx, temp);

                /*ArrayList itemscopy = new ArrayList<>(getListView().getItems());
                getListView().getItems().setAll(items);*/

                getListView().getSelectionModel().select(temp);

                success = true;
            }
            event.setDropCompleted(success);

            event.consume();
        });

        setOnDragDone(DragEvent::consume);
    }

    @Override
    protected void updateItem(Rule item, boolean empty) {
        super.updateItem(item, empty);

        /*if (empty || item == null) {
            setGraphic(null);
        } else {

        }*/
        if (item != null)
            this.setText(item.toString());
        else
            this.setText("");
    }
}
