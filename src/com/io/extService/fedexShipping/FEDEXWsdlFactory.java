package com.io.extService.fedexShipping;


public class FEDEXWsdlFactory {
	
	
	public static FEDEXInvoker getInvoker(String invokerType){
		
		switch (invokerType.toLowerCase()) {
		
			case "shipment":return new FEDEXShipmentInvoker();
		//  case "rate": return new UpsRateInvoker();
	    //  case  "track" :return new UpsTrackInvoker();
			default: return null;
		}
	}

}
