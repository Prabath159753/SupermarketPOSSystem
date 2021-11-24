package lk.superMarket.pos.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class AdminMainFormController {
    public AnchorPane root;
    public ImageView imgItem;
    public ImageView imgBusinessIncome;
    public ImageView imgItemMove;
    public ImageView imgLogOut;
    public ImageView imgViewReport;
    public ImageView imgCustomerIncome;
    public Label lblDescription;
    public Label lblMenu;

    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;
            Stage primaryStage = (Stage) (this.root.getScene().getWindow());

            switch (icon.getId()) {
                case "imgItem":
                    root = FXMLLoader.load(this.getClass().getResource("../view/ManageItemForm.fxml"));
                    primaryStage.setTitle("ADMIN MAIN VIEW | MANAGE ITEM FORM");
                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();
            }
        }
    }

    public void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgCustomerIncome":
                    lblMenu.setText("Customer Wise Income");
                    lblDescription.setText("Click to View Customer wise Income");
                    break;
                case "imgItem":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Click to add, edit, delete, search or view items");
                    break;
                case "imgItemMove":
                    lblMenu.setText("Item Movable");
                    lblDescription.setText("Click to view most movable items and least movable items");
                    break;
                case "imgBusinessIncome":
                    lblMenu.setText("Business Income");
                    lblDescription.setText("Click to view daily, monthly or annual income");
                    break;
                case "imgViewReport":
                    lblMenu.setText("Reports");
                    lblDescription.setText("Click to view all reports");
                    break;
                case "imgLogOut":
                    lblMenu.setText("LogOut");
                    lblDescription.setText("Click here if you want to LogOut in Cashier Form");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}
