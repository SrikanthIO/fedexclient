package com.io.extService.fedexShipping;

import com.fedex.ws.ship.v15.Payment;
import com.fedex.ws.ship.v15.PaymentType;
import com.fedex.ws.ship.v15.Payor;

public class FEDEXShipPaymentDetails {
	
	 Payment payment = new Payment();
	 Payor payor = new Payor();
	 
	 
	 public void setPaymentType(String paymentType){
		 PaymentType paymentTypeValue = PaymentType.valueOf(paymentType);
		 payment.setPaymentType(paymentTypeValue);
	 }
	 
	 public void setResponsiblePartyDetails(FEDEXShipAddressDetails fedexShipAddressDetails){
		 
		 payor.setResponsibleParty(fedexShipAddressDetails.getAddress());
	 }
	 
	 public Payment getPaymentInfo(){
		 payment.setPayor(payor);
		 return payment;
	 }
	 

}
