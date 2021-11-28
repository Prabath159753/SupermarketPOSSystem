package lk.superMarket.pos.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.superMarket.pos.bo.BoFactory;
import lk.superMarket.pos.bo.custom.CustomerIncomeBO;
import lk.superMarket.pos.entity.Orders;
import lk.superMarket.pos.view.tdm.IncomeTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class CustomerWiseIncomeFormController {
    private final CustomerIncomeBO customerIncomeBO = (CustomerIncomeBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMERINCOME);

    public AnchorPane root;
    public TableView<IncomeTM> tblCustomerWiseIncome;

    public void initialize() {

        tblCustomerWiseIncome.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("custId"));
        tblCustomerWiseIncome.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("total"));

        loadAllCustomerIncome();
    }

    private void loadAllCustomerIncome() {
        tblCustomerWiseIncome.getItems().clear();
        try {
            ArrayList<Orders> allIncome = customerIncomeBO.getAllCustomerIncome();
            for (Orders incomeDTO : allIncome) {
                tblCustomerWiseIncome.getItems().add(new IncomeTM(
                        incomeDTO.getCustId(), incomeDTO.getTotal()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void navigateToBack(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("../view/AdminMainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.setTitle("ADMIN MAIN VIEW");
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
