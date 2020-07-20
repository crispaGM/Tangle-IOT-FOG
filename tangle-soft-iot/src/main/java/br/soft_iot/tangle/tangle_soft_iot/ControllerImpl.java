package br.soft_iot.tangle.tangle_soft_iot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.iota.jota.IotaAPI;
import org.iota.jota.config.types.FileConfig;
import org.iota.jota.dto.response.GetNodeInfoResponse;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;




public class ControllerImpl {
	
	private String ip;
	private String port;
	private String user;
	private String pass;
	private boolean debugModeValue;
	private  ClientMQTT clienteMQTT;
	 private IotaAPI iotaClient;

	

	
	
	
	
	    public void createApiClientInstance() throws Exception {
	        iotaClient = new IotaAPI.Builder().config(new FileConfig()).build();
	    }
	 
	    
	    
	    
	   public void start() throws Exception{
		   final String protocol = "https";
		   final String url = "nodes.devnet.iota.org"; 
		   final int port = 443;
		   IotaAPI iotaClient = new IotaAPI.Builder().protocol(protocol).host(url).port(port).build();
        //iotaClient = new IotaAPI.Builder().config(new FileConfig()).build();
        
		   
		   
	    printlnDebug("Starting mapping of connected devices...");		
	 	clienteMQTT = new ClientMQTT("tcp://"  + "localhost" + ":" + "1883", this.user, this.pass);
	 	clienteMQTT.iniciar();
//        clienteMQTT.iniciar();
//        String devices = clienteIot.getApiIot("http://localhost:8181/cxf/iot-service/devices");
//        if(devices != null) System.out.println("Conectado com Broker de FOG com sucesso!!!");
//       	this.loadConnectedDevices(devices);
       	new Listener(this, clienteMQTT, "teste", 1,iotaClient);
		System.out.println("getNodes");

		GetNodeInfoResponse response = iotaClient.getNodeInfo();
		System.out.println(response);
        System.out.println("Response");

		System.out.println("TEstando");
		
		
		
	        
	}
	
	public static void main(String[] args) throws Exception {
		ControllerImpl ctrl= new ControllerImpl();
    	ctrl.start();
//    	ctrl.updateValuesSensors();
//    	System.out.print(ctrl.getListDevices().get(0).getSensors().get(0).getValue());
       
    }
	
	public void stop(){
		
	        this.clienteMQTT.finalizar();
	    
	}
//	
	
	
	
	
	
	
	
	private void printlnDebug(String str){
		if (debugModeValue)
			System.out.println(str);
	}



	public void setDebugModeValue(boolean debugModeValue) {
		this.debugModeValue = debugModeValue;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
