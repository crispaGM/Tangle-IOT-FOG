/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.iot.soft.activator;

import java.util.ArrayList;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetBundleResponse;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;
import org.iota.jota.utils.SeedRandomGenerator;
import org.iota.jota.utils.TrytesConverter;



public class MyServiceImpl implements MyService {
	 private static final String myRandomSeed = SeedRandomGenerator.generateNewSeed();
	 private static final String address = "ZLGVEQ9JUZZWCZXLWVNTHBDX9G9KZTJP9VEERIIFHY9SIQKYBVAHIMLHXPQVE9IXFDDXNHQINXJDRPFDXNYVAPLZAW";
	 private static final int depth = 3;
	 private static final int minimumWeightMagnitude = 9;
	 private IotaAPI iotaClient;
	 private  ClientMQTT clienteMQTT;
	 private String user;
	private String pass;

	 	
	
	
    
    public void start() {
		System.out.println("Opaa");

    	int securityLevel = 2;
		
		
		int value = 0;

		String message_convert = TrytesConverter.asciiToTrytes("SUPER TESTE");
		
		String tag_convert = TrytesConverter.asciiToTrytes("TESTE");
		Transfer zeroValueTransaction = new Transfer(address, value, message_convert, tag_convert);
		    
		ArrayList<Transfer> transfers = new ArrayList<Transfer>();

		transfers.add(zeroValueTransaction);
		final String protocol = "https";
		final String url = "nodes.devnet.iota.org"; 
		final int port = 443;
		iotaClient = new IotaAPI.Builder().protocol(protocol).host(url).port(port).build();
		
		try { 
		    SendTransferResponse response = iotaClient.sendTransfer(myRandomSeed, securityLevel, depth, minimumWeightMagnitude, transfers, null, null, false, false, null);


		        GetBundleResponse teste = iotaClient.getBundle(response.getTransactions().get(0).getHash());
		        System.out.println("Mensagem lida do ledge");

		        System.out.println(TrytesConverter.trytesToAscii(teste.getTransactions().get(0).getSignatureFragments().substring(0,2186)));
		        System.out.println("Transação completa");
		        
		    	clienteMQTT = new ClientMQTT("tcp://"  + "localhost" + ":" + "1883", this.user, this.pass);
			 	clienteMQTT.iniciar();
			 	clienteMQTT.publicar("teste", "vamos".getBytes(), 1);

		        System.out.println(teste.getTransactions().get(0).toString());
		}      catch (ArgumentException e) { 
				    // Handle error
				    e.printStackTrace(); 
				 }

}
    
    public void stop () {
    	
    }
    public static void main(String[] args) throws Exception {
		MyServiceImpl ctrl= new MyServiceImpl();
    	ctrl.start();
//    	ctrl.updateValuesSensors();
//    	System.out.print(ctrl.getListDevices().get(0).getSensors().get(0).getValue());
       
    }
}
