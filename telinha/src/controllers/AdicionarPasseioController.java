package controllers;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AdicionarPasseioController {

    @FXML
    private DatePicker data_passeio_label;

    @FXML
    private ComboBox<?> menu_responsavel;

    @FXML
    private TextField nome_hospede_label;

    @FXML
    private TextField valor_passeio_label;

    @FXML
    private Button registrar_passeio_botao;

    @FXML
    private ComboBox<?> menu_tipo_passeio;

    @FXML
    private TextField valor_pago_label;

    @FXML
    private TextField comprovante_label;

    @FXML
    void Cadastrar_Passeio(ActionEvent event) {
    	LocalDate data_passeio = data_passeio_label.getValue();
    	System.out.print(data_passeio);


    }

    @FXML
    void Copiar_Caminho(ActionEvent event) {
    	System.out.print("Aoba");
    }

}
   
