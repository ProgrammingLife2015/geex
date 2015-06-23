package nl.tudelft.context.controller.graphlist;


import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

/**
 * Trashcan, for removing Destroyable objects.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 22-6-2015
 */
public class TrashCan extends Label {
    /**
     * Create a new TrashCan.
     *
     * TrashCan extends Label and when something of type Destroyable
     * is dropped onto it, it will try to destroy it.
     */
    public TrashCan() {
        setText("Remove filter");
        getStyleClass().addAll("graph-item", "trashcan");

        setOnDragOver(acceptDrag());
        setOnDragDropped(removeItem());
    }

    /**
     * Destroy a destroyable object.
     *
     * The source is of type Destroyable, because it only accepts items of this type.
     *
     * @return EventHandler for dragDropped.
     */
    private EventHandler<? super DragEvent> removeItem() {
        return event -> {
            Destroyable label = (Destroyable) event.getGestureSource();

            label.destroy();
        };
    }

    /**
     * Accept drag for objects of type Destroyable.
     *
     * @return EventHandler for dragOver
     */
    private EventHandler<? super DragEvent> acceptDrag() {
        return event -> {
            if (event.getGestureSource() instanceof Destroyable) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        };
    }
}
