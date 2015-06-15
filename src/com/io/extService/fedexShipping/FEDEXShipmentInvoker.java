package com.io.extService.fedexShipping;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import com.fedex.ws.ship.v15.ClientDetail;
import com.fedex.ws.ship.v15.DropoffType;
import com.fedex.ws.ship.v15.FreightShipmentDetail;
import com.fedex.ws.ship.v15.FreightShipmentLineItem;
import com.fedex.ws.ship.v15.FreightShipmentRoleType;
import com.fedex.ws.ship.v15.Money;
import com.fedex.ws.ship.v15.PackagingType;
import com.fedex.ws.ship.v15.PrintedReference;
import com.fedex.ws.ship.v15.PrintedReferenceType;
import com.fedex.ws.ship.v15.ProcessShipmentRequest;
import com.fedex.ws.ship.v15.RequestedPackageLineItem;
import com.fedex.ws.ship.v15.RequestedShipment;
import com.fedex.ws.ship.v15.ServiceType;
import com.fedex.ws.ship.v15.ShipPortType;
import com.fedex.ws.ship.v15.ShipService;
import com.fedex.ws.ship.v15.TrackingId;
import com.fedex.ws.ship.v15.VersionId;
import com.fedex.ws.ship.v15.WebAuthenticationDetail;

public class FEDEXShipmentInvoker extends FEDEXInvoker{
	
	ShipPortType shipPortType;
	ProcessShipmentRequest request = new ProcessShipmentRequest();
	RequestedShipment requestedShipment = new RequestedShipment();
	List<RequestedPackageLineItem> requestedPackageLineItems;
	static FEDEXShipmentInvoker _invoker;
	private int numOfPackages = 1;
	private FEDEXShipmentInvoker particularInstance;
	private FEDEXShipPackageDetails fedexShipPackageDetails;
	
	@Override
	public void initService(String endPointUrl) {
		
		ShipService service = new ShipService();
		ShipPortType shipPortType  = service.getShipServicePort();
		BindingProvider bp = (BindingProvider) shipPortType;
		bp.getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointUrl);
	/*   ((BindingProvider)shipPortType).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endPointUrl);
		*/this.shipPortType = shipPortType;
	      VersionId versionId = new VersionId();
	       versionId.setServiceId("ship");
	       versionId.setMajor(15);
	       versionId.setMinor(0);
	       versionId.setIntermediate(0);
	       request.setVersion(versionId);
	}
	ClientDetail clientDetails =  null;
	WebAuthenticationDetail webAuthenticationDetails = null;
	@Override
	public void getFEDEXLoginAccess(String accountNumber, String meterNumber,String key, String password) {
		if(clientDetails == null) { 
			clientDetails = super.getFEDEXClientDetails(accountNumber, meterNumber);
		}
		if(webAuthenticationDetails == null) {
		 webAuthenticationDetails = super.getFEDEXWebAuthenticationDetail(key, password);
		}
		request.setClientDetail(clientDetails);
        request.setWebAuthenticationDetail(webAuthenticationDetails);
	}
	
	public void setFEDEXServiceInfo(FEDEXShipServiceDetails fedexShipServiceDetails){
		
		 XMLGregorianCalendar shippingDate = fedexShipServiceDetails.getShppingDate();
		 ServiceType serviceType = fedexShipServiceDetails.getServiceType();
		 DropoffType dropOffType = fedexShipServiceDetails.getDropoffType();
		 PackagingType packagingType = fedexShipServiceDetails.getPackagingType();
		 
		 if(shippingDate != null){
			 requestedShipment.setShipTimestamp(shippingDate);
		 }
		 
		 if(serviceType != null){
			 requestedShipment.setServiceType(serviceType);
		 }
		 if(dropOffType != null){
			 requestedShipment.setDropoffType(dropOffType);
		 }
		 if(packagingType != null){
			 requestedShipment.setPackagingType(packagingType);
		 }
		 
	}
	
	public void setShipperAddressDetails(FEDEXShipAddressDetails fedexShipAddressDetails){
		
		requestedShipment.setShipper(fedexShipAddressDetails.getAddress());
	}
	
	public void setShipToAddressDetails(FEDEXShipAddressDetails fedexShipAddressDetails){
		
		requestedShipment.setRecipient(fedexShipAddressDetails.getAddress());
	}
	
	public void setPaymentInfo(FEDEXShipPaymentDetails fedexShipPaymentDetails){
		
		requestedShipment.setShippingChargesPayment(fedexShipPaymentDetails.getPaymentInfo());
	}
	
	public void setLabelSpecification(FEDEXShipLabelDetails fedexShipLabelDetails){
		
		requestedShipment.setLabelSpecification(fedexShipLabelDetails.getLabelInfo());
	}
	
	public void setPackageCount(int numberOfPackages){
		requestedShipment.setPackageCount(BigInteger.valueOf(numberOfPackages));
		
	}
	
	public void setFreightShipmentDetail(FreightShipmentDetail freightShipmentDetail ) {
		requestedShipment.setFreightShipmentDetail(freightShipmentDetail);
	}
	
	
	
	public void addPackage(FEDEXShipPackageDetails fedexShipPackageDetails){
		requestedPackageLineItems = requestedShipment.getRequestedPackageLineItems();
		requestedPackageLineItems.add(fedexShipPackageDetails.getPackageDetails());
	}
	
	public void addPackages(FEDEXShipPackageDetails fedexShipPackageDetails , int numOfPackages, FEDEXShipmentInvoker fEDEXShipmentInvoker){
		
		if(numOfPackages >1){
			
			this.numOfPackages = numOfPackages;
			this.particularInstance = fEDEXShipmentInvoker;
			fEDEXShipmentInvoker.setPackageCount(numOfPackages);
			fedexShipPackageDetails.setSequenceNumber("1");
			this.fedexShipPackageDetails = fedexShipPackageDetails;
			requestedPackageLineItems = requestedShipment.getRequestedPackageLineItems();
			requestedPackageLineItems.add(fedexShipPackageDetails.getPackageDetails());
			
		}else{
			setPackageCount(1);
			requestedPackageLineItems = requestedShipment.getRequestedPackageLineItems();
			requestedPackageLineItems.add(fedexShipPackageDetails.getPackageDetails());
		}
		
	}
	
	public FEDEXShipmentResponse processShipment(){
		
		 request.setRequestedShipment(requestedShipment);
		 return new FEDEXShipmentResponse(shipPortType.processShipment(request));
	}
	
	public List<FEDEXShipmentResponse> processMultiPieceShipment(FEDEXShipmentInvoker newInstance){
		
		List<FEDEXShipmentResponse> fedexShipmentResponses = new ArrayList<FEDEXShipmentResponse>();
		
		if(numOfPackages > 1){
			
			FEDEXShipmentResponse fedexShipmentResponse = particularInstance.processShipment(); 
			
			if(fedexShipmentResponse.isResponseSuccessful()){
				
				fedexShipmentResponses.add(fedexShipmentResponse);
				TrackingId masterTrackingId = fedexShipmentResponse.getMasterTrackingId();
				
				if(masterTrackingId != null){
					
					for(int childPackageIndex = 1; childPackageIndex < numOfPackages; childPackageIndex++ ){
						
						FEDEXShipmentInvoker childShipmentInvoker = newInstance;
						childShipmentInvoker.setPackageCount(1);
						childShipmentInvoker.setMasterTrackingId(masterTrackingId);
						fedexShipPackageDetails.setSequenceNumber(String.valueOf(childPackageIndex+1));
						childShipmentInvoker.addPackage(fedexShipPackageDetails);
						
						FEDEXShipmentResponse childFedexShipmentResponse = childShipmentInvoker.processShipment(); 
						fedexShipmentResponses.add(childFedexShipmentResponse);
					}
					
				}
			}
		}
		 return fedexShipmentResponses;
		
	}
	
	public void setMasterTrackingId(TrackingId masterTrackingId){
		if(masterTrackingId != null){
			requestedShipment.setMasterTrackingId(masterTrackingId);
		}	
	}

	public ProcessShipmentRequest getRequest() {
		return request;
	}

	public void setRequest(ProcessShipmentRequest request) {
		this.request = request;
	}

	public RequestedShipment getRequestedShipment() {
		return requestedShipment;
	}

	public void setRequestedShipment(RequestedShipment requestedShipment) {
		this.requestedShipment = requestedShipment;
	}

	public List<RequestedPackageLineItem> getRequestedPackageLineItems() {
		return requestedPackageLineItems;
	}

	public void setRequestedPackageLineItems(
			List<RequestedPackageLineItem> requestedPackageLineItems) {
		this.requestedPackageLineItems = requestedPackageLineItems;
	}
	
	
	
}
