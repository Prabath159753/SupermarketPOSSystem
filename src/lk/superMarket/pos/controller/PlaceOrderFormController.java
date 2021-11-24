package lk.superMarket.pos.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class PlaceOrderFormController {
    public AnchorPane root;
    public Label lblDate;
    public Label lblId;
    /* item */
    public ComboBox cmbItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtDiscount;
    public TextField txtQty;
    /* customer */
    public TextField txtCustomerId;
    public TextField txtCustomerCity;
    public TextField txtCustomerName;
    public ComboBox cmbCustomerTitle;
    public TextArea txtCustomerAddress;
    public ComboBox cmbCustomerProvince;
    public TextField txtCustomerPostalCode;

    public TableView tblOrderDetails;

    public Label lblNetTotal;
    public Label lblDiscount;
    public Label lblAmount;

    public Button btnAddToCart;
    public Button btnCancel;
    public Button btnSearch;
    public Button btnAddNewCustomer;
    public Button btnSave;
    public Button btnPlaceOrder;
    public Button btnPrintBill;

    public void navigateToBack(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("../view/CashierMainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.setTitle("CASHIER MAIN VIEW");
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void btnSearch_OnAction(ActionEvent actionEvent) {
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
    }

    public void cancelOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
    }

    public void btnPrintBill_OnAction(ActionEvent actionEvent) {
    }
}
