package ekud.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's
 * face and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the dialog box.", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on
     * the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Tints the reply bubble based on the command that produced it.
     *
     * @param dialogStyle style chosen from {@link ekud.Ekud#getDialogStyle}
     */
    private void changeDialogStyle(DialogStyle dialogStyle) {
        if (dialogStyle == null || dialogStyle == DialogStyle.NONE) {
            return;
        }
        dialog.getStyleClass().add(dialogStyle.getCssClass());
    }

    /**
     * Returns a dialog box for a message sent by the user.
     *
     * @param text message text
     * @param img  user avatar
     * @return an unflipped dialog box
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a dialog box for a reply from Ekud.
     *
     * @param text        reply text
     * @param img         Ekud avatar
     * @param dialogStyle style used to tint the bubble
     * @return a flipped dialog box
     */
    public static DialogBox getEkudDialog(String text, Image img, DialogStyle dialogStyle) {
        DialogBox dialogBox = new DialogBox(text, img);
        dialogBox.flip();
        dialogBox.changeDialogStyle(dialogStyle);
        return dialogBox;
    }
}
