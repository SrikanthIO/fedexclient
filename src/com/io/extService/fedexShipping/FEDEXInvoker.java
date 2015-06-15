package com.io.extService.fedexShipping;

import com.fedex.ws.ship.v15.ClientDetail;
import com.fedex.ws.ship.v15.WebAuthenticationCredential;
import com.fedex.ws.ship.v15.WebAuthenticationDetail;


public abstract class FEDEXInvoker {
	
	
	public ClientDetail getFEDEXClientDetails(String accountNumber, String meterNumber){
		
		 	ClientDetail clientDetail = new ClientDetail();
	        clientDetail.setAccountNumber(accountNumber);
	        clientDetail.setMeterNumber(meterNumber);
	        return clientDetail;
	}
	
	public static WebAuthenticationDetail getFEDEXWebAuthenticationDetail( String key, String password ) {
		
        WebAuthenticationCredential webAuthenticationCredential = new WebAuthenticationCredential();
        webAuthenticationCredential.setKey(key);
        webAuthenticationCredential.setPassword(password);
        WebAuthenticationDetail webAuthenticationDetail = new WebAuthenticationDetail();
        webAuthenticationDetail.setUserCredential(webAuthenticationCredential);
        return webAuthenticationDetail;
	}
	
	public abstract void initService(String endPointUrl);
	
	public abstract void getFEDEXLoginAccess(String accountNumber, String meterNumber, String key, String password);
}
