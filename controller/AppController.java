package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import view.AppView;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML private Tab tabVeiculos;
    @FXML private Tab tabVagas;
    @FXML private Tab tabCheckin;
    @FXML private Tab tabCheckout;
    @FXML private Tab tabPagamento;

    public static void main(String[] args) {
        Application.launch(AppView.class, args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tabVeiculos.setContent(loadView("view/veiculo.fxml"));
            tabVagas.setContent(loadView("view/vaga.fxml"));
            tabCheckin.setContent(loadView("view/checkin.fxml"));
            tabCheckout.setContent(loadView("view/checkout.fxml"));
            tabPagamento.setContent(loadView("view/pagamento.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Parent loadView(String resource) throws Exception {
        return FXMLLoader.load(new java.io.File(resource).toURI().toURL());
    }
}