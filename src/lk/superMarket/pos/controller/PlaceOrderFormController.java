package lk.superMarket.pos.controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.superMarket.pos.bo.BoFactory;
import lk.superMarket.pos.bo.custom.OrderBO;
import lk.superMarket.pos.dto.CustomerDTO;
import lk.superMarket.pos.dto.ItemDTO;
import lk.superMarket.pos.dto.OrderDTO;
import lk.superMarket.pos.dto.OrderDetailDTO;
import lk.superMarket.pos.view.tdm.OrderDetailTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public TableView<OrderDetailTM> tblOrderDetails;
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
        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblOrderDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrderDetails.getColumns().get(6);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Remove");

            btnDelete.setOnAction(event -> {
                tblOrderDetails.getItems().remove(param.getValue());
                tblOrderDetails.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        lblId.setText(generateNewOrderId());
        lblDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        btnPrintBill.setDisable(true);
        btnAddToCart.setDisable(true);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtDiscount.setOnAction(event -> btnAddToCart.fire());
        txtDiscount.setEditable(false);
        txtQty.setOnAction(event -> btnAddToCart.fire());
        txtQty.setEditable(false);

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnAddToCart.setDisable(newItemCode == null);

            if (newItemCode != null) {
                try {
                    if (!existItem(newItemCode + "")) {
                        new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + newItemCode + "").show();
                    }
                    /* Find Item */
                    ItemDTO item = OrderBO.searchItem(newItemCode + "");
                    txtDescription.setText(item.getDescription());
                    txtPackSize.setText(item.getPackSize());
                    txtDiscount.setText(String.valueOf(item.getDiscount()));
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());
                    Optional<OrderDetailTM> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtDiscount.clear();
                txtUnitPrice.clear();
                txtPackSize.clear();
            }
        });

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

        initUI();
        loadAllItemCodes();
        lblDate.setText(LocalDate.now().toString());
    }

    private void initUI() {
        txtCustomerId.clear();
        cmbCustomerTitle.getSelectionModel().clearSelection();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerCity.clear();
        txtCustomerPostalCode.clear();
        cmbCustomerProvince.getSelectionModel().clearSelection();
        txtCustomerId.setDisable(false);
        cmbCustomerTitle.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        txtCustomerCity.setDisable(true);
        cmbCustomerProvince.setDisable(true);
        txtCustomerPostalCode.setDisable(true);
        txtCustomerId.setEditable(true);
        btnSave.setDisable(true);
        btnSearch.setDisable(false);
    }

    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> all = OrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemCode.getItems().add(dto.getItemCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return OrderBO.ifItemExist(code);
    }

    public String generateNewOrderId() {
        try {
            return OrderBO.generateNewOrderId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerTitle.getSelectionModel().getSelectedItem() != null && !tblOrderDetails.getItems().isEmpty()));
        btnPrintBill.setDisable(!(cmbCustomerTitle.getSelectionModel().getSelectedItem() != null && !tblOrderDetails.getItems().isEmpty()));
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

    /* Search Customer */
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
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItemCode.getValue().toString();
        String description = txtDescription.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());

        double itemDiscount = qty * Double.parseDouble(txtUnitPrice.getText()) * (Double.parseDouble(txtDiscount.getText()) / 100);
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnAddToCart.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                orderDetailTM.setDiscount(itemDiscount);
                tblOrderDetails.getSelectionModel().clearSelection();
            } else {
                qty = orderDetailTM.getQty() + qty;
                orderDetailTM.setQty(qty);
                total = new BigDecimal(orderDetailTM.getQty()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
                itemDiscount = qty * Double.parseDouble(txtUnitPrice.getText()) * (Double.parseDouble(txtDiscount.getText()) / 100);
                orderDetailTM.setDiscount(itemDiscount);
            }
            tblOrderDetails.refresh();
        } else {
            tblOrderDetails.getItems().add(new OrderDetailTM(itemCode, description, unitPrice, qty, itemDiscount, total));
        }
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    /* Calculate Total, Discount, NetTotal */
    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        double discount = 0;
        for (OrderDetailTM detail : tblOrderDetails.getItems()) {
            total = total.add(detail.getTotal());
            discount = discount + (detail.getDiscount());
        }
        lblAmount.setText(String.valueOf(total));
        lblDiscount.setText(String.valueOf(new DecimalFormat("##.##").format(discount)));
        double netTotal = Double.valueOf(String.valueOf(total)) - discount;
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        lblId.setText(generateNewOrderId());
        cmbItemCode.getSelectionModel().clearSelection();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtDiscount.clear();
        txtQty.clear();
        txtPackSize.clear();
        tblOrderDetails.getItems().clear();

        cmbCustomerTitle.getSelectionModel().clearSelection();
        cmbCustomerProvince.getSelectionModel().clearSelection();
        calculateTotal();
        initUI();
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(lblId.getText(), txtCustomerId.getText(), Date.valueOf(lblDate.getText()), Time.valueOf(LocalTime.now()),
                tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(lblId.getText(), tm.getCode(), tm.getDiscount(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()), Double.parseDouble(lblNetTotal.getText()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Successfully Done").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Try Again").show();
        }

        lblId.setText(generateNewOrderId());
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        txtDiscount.clear();
        txtQty.clear();
        calculateTotal();
        initUI();
    }

    public boolean saveOrder(String orderId, String customerId, Date orderDate, Time orderTime, List<OrderDetailDTO> orderDetails, double total) {
        try {
            OrderDTO orderDTO = new OrderDTO(orderId, customerId, orderDate, orderTime, total, orderDetails);
            return OrderBO.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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
