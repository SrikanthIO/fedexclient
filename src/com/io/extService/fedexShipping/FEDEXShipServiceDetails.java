package com.io.extService.fedexShipping;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.fedex.ws.ship.v15.DropoffType;
import com.fedex.ws.ship.v15.PackagingType;
import com.fedex.ws.ship.v15.ServiceType;

public class FEDEXShipServiceDetails {
	
	
	private XMLGregorianCalendar shippingDate = null;
	private ServiceType serviceType = null;
	private DropoffType dropOffType = null;
	private PackagingType packagingType = null;
	
	
	public void setShppingDate(Date date) throws DatatypeConfigurationException{
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setGregorianChange(date);
        this.shippingDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}
	
	public void setServiceType(String serServiceType){
		ServiceType serServiceTypeValue = ServiceType.valueOf(serServiceType);
		this.serviceType = serServiceTypeValue;
	}
	
	public void setDropoffType(String dropoffType){
		DropoffType dropoffTypeValue = DropoffType.valueOf(dropoffType);
		this.dropOffType = dropoffTypeValue;
	}
	
	public void setPackagingType(String packagingType){
		PackagingType packagingTypeValue = PackagingType.valueOf(packagingType);
		this.packagingType = packagingTypeValue;
	}
	
	public XMLGregorianCalendar getShppingDate(){
		return this.shippingDate;
	}
	
	public ServiceType getServiceType(){
		return this.serviceType;
	}
	
	public DropoffType getDropoffType(){
		
		return this.dropOffType;
	}
	
	public PackagingType getPackagingType(){
		
		return this.packagingType;
	}
	
	
}
