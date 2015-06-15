package com.io.extService.fedexShipping;

import java.util.ArrayList;
import java.util.List;

import com.fedex.ws.ship.v15.PackageSpecialServiceType;
import com.fedex.ws.ship.v15.PackageSpecialServicesRequested;

public class FEDEXShipSpecialServiceDetails {
	
	PackageSpecialServicesRequested packageSpecialServicesRequested = new PackageSpecialServicesRequested();
	List<PackageSpecialServiceType> packageSpecialServiceTypeList = new ArrayList<PackageSpecialServiceType>();
	
	
	public void addSecialService(String packageSpecialServiceType){
		
		PackageSpecialServiceType packageSpecialServiceTypeValue = PackageSpecialServiceType.valueOf(packageSpecialServiceType);
		packageSpecialServiceTypeList.add(packageSpecialServiceTypeValue);
	}
	
	public void setCODDetails(FEDEXShipCodDetails fedexShipCodDetails){
		
		 packageSpecialServicesRequested.setCodDetail(fedexShipCodDetails.getCodDetails());
	}
	
	public PackageSpecialServicesRequested getRequestedSpecialServices(){
		
		packageSpecialServicesRequested.getSpecialServiceTypes().addAll(packageSpecialServiceTypeList);
		return packageSpecialServicesRequested;	
	}
	
	
}
