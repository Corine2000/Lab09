
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML
    private ComboBox<Country> cbxCountry;

    @FXML
    private Button bottoneStatiRaggiunbili;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	
    	if(txtAnno.getText().isEmpty()) {
    		txtResult.setText("devi inserire un anno");
    		return ;
    	}
    	
    	int anno=0;
    	try {
    		anno = Integer.parseInt(txtAnno.getText());
    		
    		if(anno<1816 || anno>2016) {
    			txtResult.setText("l'anno deve essere compreso tra 1816 e 2016");
    			return;
    		}
    		
    		//se sono arrivato qua allora il formato dell'anno è stato inserito correttamente
    		model.createGraph(anno);
    		List<Country> stati = model.getCountries();
    		txtResult.appendText("grafo creato:\n numero di Vertici:"+stati.size()+"\nNumero di archi:"+model.getNumArchi()+"\n\n");
    		
    		Map<Country, Integer> verticiConGrado = model.getCountryCounts();
    		txtResult.appendText("vertici con il relativo grado(numero di vicini) \n");
    		for(Country c: verticiConGrado.keySet() ) {
    			txtResult.appendText(c.toString()+" "+verticiConGrado.get(c)+"\n");
    		}
    		
    		//avendo creato il grafo, posso riempire la combox
    		cbxCountry.getItems().addAll(stati);
    	}catch(NumberFormatException n) {
    		txtResult.setText("devi inserire un numero intero");
    		return;
    	}
    }

    @FXML
    void cercaStatiRaggiungibili(ActionEvent event) {
    	txtResult.clear();
          Country partenza = cbxCountry.getValue();
          if(partenza==null) {
        	  txtResult.setText("scegli uno stato di partenza");
        	  return;
          }
          
          List<Country> trovati = model.NodiRaggiungibili(partenza);
          for(Country c: trovati) {
        	  txtResult.appendText(c.getNomeStato()+"\n"); 
          }
          
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert bottoneStatiRaggiunbili != null : "fx:id=\"bottoneStatiRaggiunbili\" was not injected: check your FXML file 'Scene.fxml'.";
       //assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
