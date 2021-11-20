package lk.superMarket.pos.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.superMarket.pos.bo.BoFactory;
import lk.superMarket.pos.bo.custom.CustomerBO;
import lk.superMarket.pos.dto.CustomerDTO;
import lk.superMarket.pos.view.tdm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class ManageCustomerFormController {
    public AnchorPane root;
    public TextField txtCustomerId;
    public ComboBox cmbCustomerTitle;
    public TextField txtCustomerName;
    public TextArea txtCustomerAddress;
    public TextField txtCustomerCity;
    public ComboBox cmbCustomerProvince;
    public TextField txtCustomerPostalCode;
    public TableView<CustomerTM> tblCustomers;
    public Button btnSearch;
    public Button btnAddNewCustomer;
    public Button btnSave;
    public Button btnDelete;

    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    public void initialize() {
        initUI();

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

    private void initUI() {
        txtCustomerId.clear();
        cmbCustomerTitle.getSelectionModel().clearSelection();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerCity.clear();
        txtCustomerPostalCode.clear();
        cmbCustomerProvince.getSelectionModel().clearSelection();
        txtCustomerId.setDisable(true);
        cmbCustomerTitle.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        txtCustomerCity.setDisable(true);
        cmbCustomerProvince.setDisable(true);
        txtCustomerPostalCode.setDisable(true);
        txtCustomerId.setEditable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnSearch.setDisable(false);
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCustomerId.setDisable(false);
        cmbCustomerTitle.setDisable(false);
        txtCustomerName.setDisable(false);
        txtCustomerAddress.setDisable(false);
        cmbCustomerProvince.setDisable(false);
        txtCustomerCity.setDisable(false);
        txtCustomerPostalCode.setDisable(false);
        txtCustomerId.setEditable(false);
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
        tblCustomers.getSelectionModel().clearSelection();
    }

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

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                /* Save Customer */
                CustomerDTO customerDTO = new CustomerDTO(id, title, name, address, city, province, postalCode);
                customerBO.addCustomer(customerDTO);
                new Alert(Alert.AlertType.CONFIRMATION, "The customer save successfully associated with customer id " + id).show();
                tblCustomers.getItems().add(new CustomerTM(id, title, name, address, city, province, postalCode));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }
                /* Update Customer */
                CustomerDTO customerDTO = new CustomerDTO(id, title, name, address, city, province, postalCode);
                customerBO.updateCustomer(customerDTO);
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully update customer details associated with customer id " + id).show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();
            selectedCustomer.setTitle(title);
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setCity(city);
            selectedCustomer.setProvince(province);
            selectedCustomer.setPostalCode(postalCode);
            tblCustomers.refresh();
        }
        btnAddNewCustomer.fire();
    }

    public void btnSearch_OnAction(ActionEvent actionEvent) {
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
    }

    public void navigateToHome(MouseEvent mouseEvent) {
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }

    /* Generate new customer id */
    private String generateNewId() {
        try {
            return customerBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblCustomers.getItems().isEmpty()) {
            return "C-001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C-", "")) + 1;
            return String.format("C-%03d", newCustomerId);
        }
    }

    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomers.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getId();
    }
}
