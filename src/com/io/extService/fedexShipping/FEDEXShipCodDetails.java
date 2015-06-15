package com.io.extService.fedexShipping;

import java.math.BigDecimal;

import com.fedex.ws.ship.v15.CodCollectionType;
import com.fedex.ws.ship.v15.CodDetail;
import com.fedex.ws.ship.v15.Money;

public class FEDEXShipCodDetails {
	
	CodDetail codDetail = new CodDetail();
	
	public void setCollectionType(String collectionType){
		
		CodCollectionType collectionTypeValue = CodCollectionType.valueOf(collectionType);
		codDetail.setCollectionType(collectionTypeValue);
		
	}
	
	public void setCollectionAmount(double amount, String currency){
		
			Money money = new Money();
			money.setCurrency(currency);
			money.setAmount(BigDecimal.valueOf(amount));
			codDetail.setCodCollectionAmount(money);
	}
	
	public CodDetail getCodDetails(){
		
		return codDetail;
	}
	 
}
