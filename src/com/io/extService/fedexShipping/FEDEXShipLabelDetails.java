package com.io.extService.fedexShipping;

import com.fedex.ws.ship.v15.LabelFormatType;
import com.fedex.ws.ship.v15.LabelPrintingOrientationType;
import com.fedex.ws.ship.v15.LabelSpecification;
import com.fedex.ws.ship.v15.LabelStockType;
import com.fedex.ws.ship.v15.ShippingDocumentImageType;


public class FEDEXShipLabelDetails {
	
	LabelSpecification labelSpecification = new LabelSpecification();
	
	public void setImageType(String shippingDocumentImageType){
		
		ShippingDocumentImageType shippingDocumentImageTypeValue = ShippingDocumentImageType.valueOf(shippingDocumentImageType);
		labelSpecification.setImageType(shippingDocumentImageTypeValue);
	}
	
	public void setLabelFormatType(String labelFormatType){
		
		LabelFormatType labelFormatTypeValue = LabelFormatType.valueOf(labelFormatType);
		labelSpecification.setLabelFormatType(labelFormatTypeValue);
	}
	
	// STOCK_4X6.75_LEADING_DOC_TAB
	public void setLabelStockType(String labelStockType){
		LabelStockType labelStockTypeValue = LabelStockType.valueOf(labelStockType);
		labelSpecification.setLabelStockType(labelStockTypeValue);
	}
	
	public void setLabelPrintingOrientation(String labelPrintingOrientationType){
		
		LabelPrintingOrientationType labelPrintingOrientationTypeValue = LabelPrintingOrientationType.valueOf(labelPrintingOrientationType);
		labelSpecification.setLabelPrintingOrientation(labelPrintingOrientationTypeValue);
	}
	
	public LabelSpecification getLabelInfo(){
		
		return labelSpecification;
	}
}
