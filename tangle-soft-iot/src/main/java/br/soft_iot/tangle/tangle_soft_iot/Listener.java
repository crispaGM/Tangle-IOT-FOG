package br.soft_iot.tangle.tangle_soft_iot;
import java.util.List;
import java.util.Random;

import javax.naming.ServiceUnavailableException;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetBundleResponse;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;
import org.iota.jota.utils.SeedRandomGenerator;
import org.iota.jota.utils.TrytesConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.util.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Map.Entry.*;



public class Listener  implements IMqttMessageListener{
	
	private boolean debugModeValue;
	private static ControllerImpl impl;
	private ClientMQTT clienteMQTT;
	Map<String,Map<String,Integer>> topk_k_scoresByIdrequi = new HashMap<String,Map<String,Integer>>(); // Lista de Top-ks de Requis diferentes
// teste iota
	 private static final String myRandomSeed = SeedRandomGenerator.generateNewSeed();
;
	 private static final String address = "ZLGVEQ9JUZZWCZXLWVNTHBDX9G9KZTJP9VEERIIFHY9SIQKYBVAHIMLHXPQVE9IXFDDXNHQINXJDRPFDXNYVAPLZAW";
	 private static final int depth = 3;
	 private static final int minimumWeightMagnitude = 9;
	 private IotaAPI iotaClient;
	

    public Listener(ControllerImpl impl, ClientMQTT clienteMQTT, String topico, int qos,IotaAPI iotaClient) throws Exception {
        clienteMQTT.subscribe(qos, this, topico);
        this.clienteMQTT = clienteMQTT;
        this.impl = impl;
        this.iotaClient = iotaClient;
        messageArrived("teste", new MqttMessage());
    }
    
    public Listener(ControllerImpl impl) {       
        this.impl = impl;
    }
    

    
    public void shouldSendMessage(String message,String tag) throws ArgumentException {
    	int securityLevel = 2;
		
		
		int value = 0;

		String message_convert = TrytesConverter.asciiToTrytes(message);
		
		String tag_convert = TrytesConverter.asciiToTrytes(tag);
		Transfer zeroValueTransaction = new Transfer(address, value, message_convert, tag_convert);
		    
		ArrayList<Transfer> transfers = new ArrayList<Transfer>();

		transfers.add(zeroValueTransaction);
		
		
		try { 
		    SendTransferResponse response = iotaClient.sendTransfer(myRandomSeed, securityLevel, depth, minimumWeightMagnitude, transfers, null, null, false, false, null);


		    try { 
		        GetBundleResponse teste = iotaClient.getBundle(response.getTransactions().get(0).getHash());
		        System.out.println("Mensagem lida do ledge");

		        System.out.println(TrytesConverter.trytesToAscii(teste.getTransactions().get(0).getSignatureFragments().substring(0,2186)));
		        System.out.println("Transação completa");

		        System.out.println(teste.getTransactions().get(0).toString());
		        
		    } catch (org.iota.jota.error.ArgumentException e) { 
			    iotaClient.sendTransfer(myRandomSeed, securityLevel, depth, minimumWeightMagnitude, transfers, null, null, false, false, null);

		    }
		    
		    
		    
		} catch (ArgumentException e) { 
		    // Handle error
		    e.printStackTrace(); 
		 }
		
		

    } 
    

    
    public synchronized void messageArrived( final String topic, final MqttMessage message) throws Exception {
        System.out.println("Mensagem recebida :");
        System.out.println("\tTopico: " + topic);
        System.out.println("\tMensagem: " + new String(message.getPayload()));
        System.out.println("");
        String teste = new String(message.getPayload());

        shouldSendMessage(teste,"TESTE");
        
    	

    
}
   
    
    public void saveMsg() {
    	
    	
    }
    
    private void printlnDebug(String str){
		if (debugModeValue)
			System.out.println(str);
	}

	

}