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
import lk.superMarket.pos.bo.BoFactory;
import lk.superMarket.pos.bo.custom.OrderBO;
import lk.superMarket.pos.dto.CustomerDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class PlaceOrderFormController {

    private final OrderBO OrderBO = (OrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);

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

    public void initialize() throws SQLException, ClassNotFoundException {
        cmbCustomerProvince.getItems().addAll(
                "Southern Province", "Western Province", "Central Province", "Eastern Province", "Northern Province", "North Western Province", "North Central Province", "Uva Province", "Sabaragamuwa Province"
        );
        cmbCustomerProvince.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });

        cmbCustomerTitle.getItems().addAll(
                "Mr.", "Mrs.", "Miss.", "Dr."
        );

        cmbCustomerTitle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });
    }

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
        btnSave.setDisable(true);

        try {
            if (!existCustomer(txtCustomerId.getText())) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + txtCustomerId.getText()).show();
                txtCustomerId.clear();
                cmbCustomerTitle.getSelectionModel().clearSelection();
                txtCustomerName.clear();
                txtCustomerAddress.clear();
                cmbCustomerProvince.getSelectionModel().clearSelection();
                txtCustomerCity.clear();
                txtCustomerPostalCode.clear();
                txtCustomerId.requestFocus();

            } else {
                /* Search Customer */
                CustomerDTO customerDTO = OrderBO.searchCustomer(txtCustomerId.getText());
                cmbCustomerTitle.setValue(customerDTO.getTitle());
                cmbCustomerProvince.setValue(customerDTO.getProvince());
                txtCustomerName.setText(customerDTO.getName());
                txtCustomerAddress.setText(customerDTO.getAddress());
                txtCustomerCity.setText(customerDTO.getCity());
                txtCustomerPostalCode.setText(customerDTO.getPostalCode());

                btnSave.setDisable(true);
                cmbCustomerTitle.setDisable(true);
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);
                cmbCustomerProvince.setDisable(true);
                txtCustomerCity.setDisable(false);
                txtCustomerPostalCode.setDisable(false);
                txtCustomerName.setEditable(false);
                txtCustomerAddress.setEditable(false);
                txtCustomerCity.setEditable(false);
                txtCustomerPostalCode.setEditable(false);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to search the customer " + txtCustomerId.getText() + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCustomerId.setDisable(false);
        cmbCustomerTitle.setDisable(false);
        txtCustomerName.setDisable(false);
        txtCustomerAddress.setDisable(false);
        cmbCustomerProvince.setDisable(false);
        txtCustomerCity.setDisable(false);
        txtCustomerPostalCode.setDisable(false);
        txtCustomerId.clear();
        txtCustomerId.setText(generateNewId());
        cmbCustomerTitle.getSelectionModel().clearSelection();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        cmbCustomerProvince.getSelectionModel().clearSelection();
        txtCustomerCity.clear();
        txtCustomerPostalCode.clear();
        cmbCustomerTitle.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
    }

    /* Save customer */
    public void btnSave_OnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String title = cmbCustomerTitle.getValue().toString();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String city = txtCustomerCity.getText();
        String province = cmbCustomerProvince.getValue().toString();
        String postalCode = txtCustomerPostalCode.getText();

        /* Validation */
        if (!name.matches("^[A-z ]{3,30}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtCustomerName.requestFocus();
            return;
        } else if (!address.matches("^[A-z0-9/ ]{3,40}$")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtCustomerAddress.requestFocus();
            return;
        } else if (!city.matches("^[A-z]{3,30}$")) {
            new Alert(Alert.AlertType.ERROR, "City should be at least 3 characters long").show();
            txtCustomerCity.requestFocus();
            return;
        } else if (!postalCode.matches("^[0-9]{3,10}$")) {
            new Alert(Alert.AlertType.ERROR, "Postal Code should be at least 3 characters long").show();
            txtCustomerPostalCode.requestFocus();
            return;
        }

        try {
            if (existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }
            /* Save Customer */
            CustomerDTO customerDTO = new CustomerDTO(id, title, name, address, city, province, postalCode);
            OrderBO.addCustomer(customerDTO);
            new Alert(Alert.AlertType.CONFIRMATION, "The customer save successfully associated with customer id " + id).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        txtCustomerId.setEditable(false);
        cmbCustomerTitle.setEditable(false);
        txtCustomerName.setEditable(false);
        txtCustomerAddress.setEditable(false);
        txtCustomerCity.setEditable(false);
        cmbCustomerProvince.setEditable(false);
        txtCustomerPostalCode.setEditable(false);
        btnSave.setDisable(true);
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
    }

    public void cancelOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
    }

    public void btnPrintBill_OnAction(ActionEvent actionEvent) {
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return OrderBO.ifCustomerExist(id);
    }

    /* Generate new customer id */
    private String generateNewId() {
        try {
            return OrderBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
