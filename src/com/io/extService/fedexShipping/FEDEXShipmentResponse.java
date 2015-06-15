package com.io.extService.fedexShipping;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.fedex.ws.ship.v15.CodReturnPackageDetail;
import com.fedex.ws.ship.v15.CompletedPackageDetail;
import com.fedex.ws.ship.v15.CompletedShipmentDetail;
import com.fedex.ws.ship.v15.Notification;
import com.fedex.ws.ship.v15.NotificationSeverityType;
import com.fedex.ws.ship.v15.ProcessShipmentReply;
import com.fedex.ws.ship.v15.ShippingDocument;
import com.fedex.ws.ship.v15.ShippingDocumentPart;
import com.fedex.ws.ship.v15.TrackingId;

public class FEDEXShipmentResponse {
	
	private ProcessShipmentReply processShipmentReply = null;
	private CompletedShipmentDetail completedShipmentDetail;
	private String responseErrorCode = "";
	private String responseErrorMessage = "";
 	
	public FEDEXShipmentResponse(ProcessShipmentReply processShipmentReply){
		
		this.processShipmentReply = processShipmentReply;
		completedShipmentDetail = processShipmentReply.getCompletedShipmentDetail(); 
	}
	
	public boolean isResponseSuccessful(){
		
		
		if(processShipmentReply != null ){
			
			NotificationSeverityType notificationSeverityType = processShipmentReply.getHighestSeverity();
			
			if (notificationSeverityType.equals(NotificationSeverityType.WARNING) ||
					notificationSeverityType.equals(NotificationSeverityType.NOTE)    ||
					notificationSeverityType.equals(NotificationSeverityType.SUCCESS)) {
					return true;
			}
			
			if(notificationSeverityType.equals(NotificationSeverityType.ERROR)){
				List<Notification> notifications = processShipmentReply.getNotifications();
				if (notifications == null || notifications.size() == 0) {
					responseErrorCode = "0";
					responseErrorMessage = "  No notifications returned";
				}else{
				
						for (int i=0; i < notifications.size(); i++){
							Notification notification = notifications.get(i);
							responseErrorCode = notification.getCode();
							responseErrorMessage =  notification.getMessage();
						}
				}
			}
		}
			return false;
	}
	
	public String getResponseErrorCode(){
		
		return responseErrorCode;
	}
	
	public String getResponseErrorMessage(){
		
		return responseErrorMessage;
	}
	
	public TrackingId getMasterTrackingId(){
		
		if(completedShipmentDetail != null && completedShipmentDetail.getMasterTrackingId() != null){
			
			return completedShipmentDetail.getMasterTrackingId();
		}
		return null;
	}
	
	public String getTrackingNumber(){
		
		if(processShipmentReply != null && completedShipmentDetail != null){
			return completedShipmentDetail.getCompletedPackageDetails().get(0).getTrackingIds().get(0).getTrackingNumber();
		}
		return "0";
	}
	
	public void saveLableAsPDF(String saveLocation) throws Exception{
		
		if(processShipmentReply != null && completedShipmentDetail != null){
			
			List<CompletedPackageDetail> completedPackageDetails = completedShipmentDetail.getCompletedPackageDetails();
			
			 for(int completedPackageDetailIndex = 0; completedPackageDetailIndex < completedPackageDetails.size() ; completedPackageDetailIndex++ ){
				 
				 String trackingNumber = completedPackageDetails.get(completedPackageDetailIndex).getTrackingIds().get(completedPackageDetailIndex).getTrackingNumber();
				 ShippingDocument shippingDocument = completedPackageDetails.get(completedPackageDetailIndex).getLabel();
				 saveLabelToFile(shippingDocument, trackingNumber, saveLocation);
				 saveGroundCodLabelToFile(completedPackageDetails.get(completedPackageDetailIndex), trackingNumber, saveLocation);
			 }
		}
	}
	
	
	
	private static void saveLabelToFile(ShippingDocument shippingDocument, String trackingNumber, String saveLocation) throws Exception {
		
		List<ShippingDocumentPart> sdparts = shippingDocument.getParts();
		for (int a=0; a < sdparts.size(); a++) {
			
			ShippingDocumentPart sdpart = sdparts.get(a);
		
			String shippingDocumentType = shippingDocument.getType().value();
			String labelFileName =  new String(saveLocation + shippingDocumentType + "." + trackingNumber + "_" + a + ".pdf");					
			File labelFile = new File(labelFileName);
			FileOutputStream fos = new FileOutputStream( labelFile );
			fos.write(sdpart.getImage());
			fos.close();
		}
	}
	 public void printLabel(String saveLocation, String fileFormat) throws Exception
	 /*     */   {
	 /*  90 */     if ((this.processShipmentReply != null) && (this.completedShipmentDetail != null))
	 /*     */     {
	 /*  92 */       List completedPackageDetails = this.completedShipmentDetail.getCompletedPackageDetails();
	 /*     */ 
	 /*  94 */       for (int completedPackageDetailIndex = 0; completedPackageDetailIndex < completedPackageDetails.size(); completedPackageDetailIndex++)
	 /*     */       {
	 /*  96 */         String trackingNumber = ((TrackingId)((CompletedPackageDetail)completedPackageDetails.get(completedPackageDetailIndex)).getTrackingIds().get(completedPackageDetailIndex)).getTrackingNumber();
	 /*  97 */         ShippingDocument shippingDocument = ((CompletedPackageDetail)completedPackageDetails.get(completedPackageDetailIndex)).getLabel();
	 /*  98 */         saveLabelToFile(shippingDocument, trackingNumber, saveLocation, fileFormat);
	 /*  99 */         saveGroundCodLabelToFile((CompletedPackageDetail)completedPackageDetails.get(completedPackageDetailIndex), trackingNumber, saveLocation);
	 /*     */       }
	 /*     */     }
	 /*     */   }
	 public CompletedShipmentDetail getCompleteShipmentDetail()
		/*     */   {
		/* 107 */     return this.completedShipmentDetail;
		/*     */   }
	 private static void saveLabelToFile(ShippingDocument shippingDocument, String trackingNumber, String saveLocation, String fileFormat)
			 /*     */     throws Exception
			 /*     */   {
			 /* 113 */     List sdparts = shippingDocument.getParts();
			 /* 114 */     for (int a = 0; a < sdparts.size(); a++)
			 /*     */     {
			 /* 116 */       ShippingDocumentPart sdpart = (ShippingDocumentPart)sdparts.get(a);
			 /*     */ 
			 /* 118 */       String shippingDocumentType = shippingDocument.getType().value();
			 /* 119 */       String labelFileName = new String(saveLocation + shippingDocumentType + "." + trackingNumber + "_" + a + "." + fileFormat.toUpperCase());
			 /* 120 */       File labelFile = new File(labelFileName);
			 /* 121 */       FileOutputStream fos = new FileOutputStream(labelFile);
			 /* 122 */       fos.write(sdpart.getImage());
			 /* 123 */       fos.close();
			 /*     */     }
			 /*     */   }
			 /*     */ 
	 private static void saveGroundCodLabelToFile(CompletedPackageDetail completedPackageDetail, String trackingNumber, String saveLocation)throws Exception{
		
		CodReturnPackageDetail codReturnPackageDetail = completedPackageDetail.getCodReturnDetail();
		
		if(codReturnPackageDetail!=null && codReturnPackageDetail.getLabel()!=null){
			
			
			String labelName = codReturnPackageDetail.getLabel().getType().value();
			
			List<ShippingDocumentPart> parts = codReturnPackageDetail.getLabel().getParts();
			
			for(int i=0; i<parts.size(); i++){
				
				String codLabelFileName =  new String(saveLocation + labelName + "." + trackingNumber + "_" + i + ".pdf");					
				
				File codLabelFile = new File(codLabelFileName);
				
				FileOutputStream fos = new FileOutputStream( codLabelFile );
				fos.write(parts.get(i).getImage());
				fos.close();
				
			}
		}	
	}
	
		

}
