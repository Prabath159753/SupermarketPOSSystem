package lk.superMarket.pos.controller;

import javafx.application.Platform;
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
import lk.superMarket.pos.bo.custom.ItemBO;
import lk.superMarket.pos.dto.ItemDTO;
import lk.superMarket.pos.view.tdm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class ManageItemFormController {

    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    public AnchorPane root;
    public TextField txtItemCode;
    public TextArea txtItemDescription;
    public ComboBox cmbItemSize;
    public TextField txtItemUnitPrice;
    public TextField txtItemQtyOnHand;
    public TextField txtDiscount;
    public TableView<ItemTM> tblItems;
    public Button btnAddNewItem;
    public Button btnSave;
    public Button btnDelete;
    public Button btnSearch;

    public void initialize() {
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItems.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItems.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("discount"));

        initUI();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtItemCode.setText(newValue.getItemCode());
                txtItemDescription.setText(newValue.getDescription());
                cmbItemSize.setValue(newValue.getPackSize());
                txtItemUnitPrice.setText(newValue.getUnitPrice().setScale(2).toString());
                txtItemQtyOnHand.setText(newValue.getQtyOnHand() + "");
                txtDiscount.setText(newValue.getDiscount() + "");
                txtItemCode.setDisable(false);
                txtItemDescription.setDisable(false);
                cmbItemSize.setDisable(false);
                txtItemUnitPrice.setDisable(false);
                txtItemQtyOnHand.setDisable(false);
                txtDiscount.setDisable(false);
            }
        });

        txtItemQtyOnHand.setOnAction(event -> btnSave.fire());
        loadAllItems();

        cmbItemSize.getItems().addAll(
                "Small", "Medium", "Large", "Bottle"
        );
        cmbItemSize.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });

    }

    private void initUI() {
        txtItemCode.clear();
        txtItemDescription.clear();
        cmbItemSize.getSelectionModel().clearSelection();
        txtItemUnitPrice.clear();
        txtItemQtyOnHand.clear();
        txtDiscount.clear();
        txtItemCode.setDisable(false);
        txtItemDescription.setDisable(true);
        cmbItemSize.setDisable(true);
        txtItemUnitPrice.setDisable(true);
        txtItemQtyOnHand.setDisable(true);
        txtItemCode.setEditable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnSearch.setDisable(false);
    }

    private void loadAllItems() {
        tblItems.getItems().clear();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItems.getItems().add(new ItemTM(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand(), item.getDiscount()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtItemCode.setDisable(false);
        txtItemDescription.setDisable(false);
        cmbItemSize.setDisable(false);
        txtItemUnitPrice.setDisable(false);
        txtItemQtyOnHand.setDisable(false);
        txtItemCode.clear();
        txtItemCode.setText(generateNewId());
        txtItemDescription.clear();
        cmbItemSize.getSelectionModel().clearSelection();
        txtItemUnitPrice.clear();
        txtItemQtyOnHand.clear();
        txtDiscount.clear();
        txtItemDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblItems.getSelectionModel().clearSelection();
        btnSearch.setDisable(true);
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

        String itemCode = txtItemCode.getText();
        String description = txtItemDescription.getText();
        String size = cmbItemSize.getValue().toString();
        int qtyOnHand = Integer.parseInt(txtItemQtyOnHand.getText());
        int discount = Integer.parseInt(txtDiscount.getText());
        BigDecimal unitPrice = new BigDecimal(txtItemUnitPrice.getText()).setScale(2);

        /* Validation */
        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtItemDescription.requestFocus();
            return;
        } else if (!txtItemUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtItemUnitPrice.requestFocus();
            return;
        } else if (!txtItemQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtItemQtyOnHand.requestFocus();
            return;
        } else if (!txtDiscount.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Discount").show();
            txtDiscount.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existItem(itemCode)) {
                    new Alert(Alert.AlertType.ERROR, itemCode + " already exists").show();
                }

                /* Save Item */
                ItemDTO dto = new ItemDTO(itemCode, description, size, unitPrice, qtyOnHand, discount);
                itemBO.addItem(dto);
                tblItems.getItems().add(new ItemTM(itemCode, description, size, unitPrice, qtyOnHand, discount));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (!existItem(itemCode)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + itemCode).show();
                }

                /* Update Item */
                ItemDTO dto = new ItemDTO(itemCode, description, size, unitPrice, qtyOnHand, discount);
                itemBO.updateItem(dto);
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully update item details associated with customer itemCode " + itemCode).show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();
            selectedItem.setDescription(description);
            selectedItem.setPackSize(size);
            selectedItem.setUnitPrice(unitPrice);
            selectedItem.setQtyOnHand(qtyOnHand);
            selectedItem.setDiscount(discount);

            tblItems.refresh();
        }
        btnSearch.setDisable(false);
        btnAddNewItem.fire();
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        String itemCode = tblItems.getSelectionModel().getSelectedItem().getItemCode();
        try {
            if (!existItem(itemCode)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + itemCode).show();
            }

            /* Delete item */
            itemBO.deleteItem(itemCode);
            tblItems.getItems().remove(tblItems.getSelectionModel().getSelectedItem());
            tblItems.getSelectionModel().clearSelection();
            initUI();
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully delete item details associated with itemCode " + itemCode).show();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + itemCode).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnSearch.setDisable(false);
    }

    public void btnSearch_OnAction(ActionEvent actionEvent) {
        btnSave.setDisable(true);
        txtItemCode.setEditable(true);

        try {
            if (!existItem(txtItemCode.getText())) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + txtItemCode.getText()).show();
                txtItemCode.clear();
                cmbItemSize.getSelectionModel().clearSelection();
                txtItemDescription.clear();
                txtItemUnitPrice.clear();
                txtItemQtyOnHand.clear();
                txtDiscount.clear();

            } else {
                /* Search Item */
                ItemDTO itemDTO = itemBO.searchItem(txtItemCode.getText());
                txtItemCode.setText(itemDTO.getItemCode());
                txtItemDescription.setText(itemDTO.getDescription());
                cmbItemSize.setValue(itemDTO.getPackSize());
                txtItemUnitPrice.setText(itemDTO.getUnitPrice().setScale(2).toString());
                txtItemQtyOnHand.setText(itemDTO.getQtyOnHand() + "");
                txtDiscount.setText(itemDTO.getDiscount() + "");

                btnSave.setDisable(false);
                txtItemCode.setDisable(false);
                cmbItemSize.setDisable(false);
                txtItemDescription.setDisable(false);
                txtItemUnitPrice.setDisable(false);
                txtItemQtyOnHand.setDisable(false);
                txtDiscount.setDisable(false);
                cmbItemSize.setEditable(false);
                txtItemDescription.setEditable(false);
                txtItemUnitPrice.setEditable(false);
                txtItemQtyOnHand.setEditable(false);
                txtDiscount.setEditable(false);

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to search the customer " + txtItemCode.getText() + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblItems.refresh();
    }

    private String generateNewId() {
        try {
            return itemBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I-001";
    }

    private boolean existItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(itemCode);
    }
}
