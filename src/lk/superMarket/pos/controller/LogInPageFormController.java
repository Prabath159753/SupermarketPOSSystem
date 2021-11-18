package lk.superMarket.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class LogInPageFormController {
    public AnchorPane root;
    public TextField txtUserName;
    public PasswordField pswPassword;
    public Button btnLogIn;

    public void btnLogInOnAction(ActionEvent actionEvent) throws IOException {

        if (txtUserName.getText().equals("Admin")){

            Stage logStage = (Stage) btnLogIn.getScene().getWindow();
            logStage.close();

            URL resource = this.getClass().getResource("../view/AdminMainForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setTitle("ADMIN MAIN VIEW ");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
        }else if (txtUserName.getText().equals("Cashier")){

            Stage logStage = (Stage) btnLogIn.getScene().getWindow();
            logStage.close();

            URL resource = this.getClass().getResource("../view/CashierMainForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setTitle("CASHIER MAIN VIEW ");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
        }
    }
}
