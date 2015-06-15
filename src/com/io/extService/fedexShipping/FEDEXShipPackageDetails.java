package com.io.extService.fedexShipping;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fedex.ws.ship.v15.Dimensions;
import com.fedex.ws.ship.v15.LinearUnits;
import com.fedex.ws.ship.v15.RequestedPackageLineItem;
import com.fedex.ws.ship.v15.Weight;
import com.fedex.ws.ship.v15.WeightUnits;


public class FEDEXShipPackageDetails {
	
	RequestedPackageLineItem requestedPackageLineItem = new RequestedPackageLineItem();
	
	public void setSequenceNumber(String sequenceNumber){
		
		requestedPackageLineItem.setSequenceNumber(new BigInteger(sequenceNumber));
	}
	
	public void setGroupPackageCount(int count){
		
		requestedPackageLineItem.setGroupPackageCount(BigInteger.valueOf(count));
	}
	
	public void setWeight(Double weightValue, String unit  ){
		WeightUnits unitValue = WeightUnits.valueOf(unit);
		Weight weight = new Weight();
		weight.setUnits(unitValue);
		weight.setValue(new BigDecimal(weightValue));
		requestedPackageLineItem.setWeight(weight);
	}
	
	public void setDimensions(int length, int height, int width, String unit){
		
		LinearUnits unitValue = LinearUnits.valueOf(unit);
		Dimensions dimensions = new Dimensions();
		dimensions.setLength(BigInteger.valueOf(length));
		dimensions.setHeight(BigInteger.valueOf(height));
		dimensions.setWidth(BigInteger.valueOf(width));
		dimensions.setUnits(unitValue);
		requestedPackageLineItem.setDimensions(dimensions);
	} 
	
	public RequestedPackageLineItem getPackageDetails(){
		
		return requestedPackageLineItem;
	}
	
	public void setSpecialServices(FEDEXShipSpecialServiceDetails fedexShipSpecialServiceDetails){
		
		requestedPackageLineItem.setSpecialServicesRequested(fedexShipSpecialServiceDetails.getRequestedSpecialServices());
	}
}
