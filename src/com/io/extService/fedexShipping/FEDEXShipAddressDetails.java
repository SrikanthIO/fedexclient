package com.io.extService.fedexShipping;

import java.util.List;

import com.fedex.ws.ship.v15.Address;
import com.fedex.ws.ship.v15.Contact;
import com.fedex.ws.ship.v15.Party;

public class FEDEXShipAddressDetails {
	
	Party party = new Party();
	Contact contact = new Contact();
	Address address = new Address();
	
	public void setAccountNumber(String accountNumber){
		
		party.setAccountNumber(accountNumber);
	}
	
	public void setPersonName(String contactName){
		
		contact.setPersonName(contactName);
	}
	
	public void setCompanyName(String companyName){
		
		contact.setCompanyName(companyName);
	}
	
	public void setPhoneNumber(String phoneNumber, String ext){
		
		contact.setPhoneNumber(phoneNumber);
		
		if(ext != null && !"".equals(ext)){
			
			contact.setPhoneExtension(ext);
		}
		
	}
	
	public void setEmailAddress(String emailAddress){
		
		contact.setEMailAddress(emailAddress);
	}
	
	public void setFaxNumber(String faxNumber){
		
		contact.setFaxNumber(faxNumber);
	}
	
	public void setAddressLines(String addressLine1, String addressLine2, String addressLine3){
		
		
		List<String> addressLines = address.getStreetLines();
		addressLines.add(addressLine1);
		
		if(addressLine2 != null && ! "".equals(addressLine2)){
			
			addressLines.add(addressLine2);
		}
		
		if(addressLine3 != null && ! "".equals(addressLine3)){
			
			addressLines.add(addressLine2);
		}
		
	}
	
	public void setCity(String city){
		address.setCity(city);
	}
	
	public void setStateOrProvinceCode(String stateOrProvinceCode){
		address.setStateOrProvinceCode(stateOrProvinceCode);
	}
	
	public void setZipCode(String zipCode){
		
		address.setPostalCode(zipCode);
	}
	
	public void setCountryCode(String countryCode){
		address.setCountryCode(countryCode);	    
	}
	
	public Party getAddress(){
		
		party.setContact(contact);
		party.setAddress(address);
		return party; 
				
	}

}
