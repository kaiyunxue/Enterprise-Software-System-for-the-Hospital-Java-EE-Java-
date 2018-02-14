package edu.stevens.cs548.clinic.research.jms;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import edu.stevens.cs548.clinic.billing.service.IResearchService;
import edu.stevens.cs548.clinic.billing.service.IResearchServiceLocal;
import edu.stevens.cs548.clinic.billing.service.IResearchService.DrugTreatmentDTO;
import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.research.service.DrugTreatmentDtoFactory;
import edu.stevens.cs548.clinic.research.service.ResearchService;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: DrugTreatmentListener
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destination", 
				propertyValue = "Treatment"),
				@ActivationConfigProperty(
				propertyName = "destinationType", 
				propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(
				propertyName = "messageSelector", 
				propertyValue = "TreatmentType='DrugTreatmentRecord'")
		}, 
		mappedName = "jms/Treatment")
public class DrugTreatmentListener implements MessageListener {

    @Inject
    @EJB(beanName="ResearchServiceBean")
	IResearchServiceLocal service;
    
    private static Logger logger = Logger.getLogger(ResearchService.class.getCanonicalName());
    
    public DrugTreatmentListener() {
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	logger.info("DrugTreatment Message Received");
        ObjectMessage objectMessage = (ObjectMessage)message;
        try{
        	TreatmentDto treatment = (TreatmentDto)objectMessage.getObject();
        	logger.info(treatment.getDiagnosis()+treatment.getId());
        	if(treatment.getDrugTreatment()==null){
        		throw new JMSException("Not a drug treatment");
        	}else{
        		DrugTreatmentDTO dto =new DrugTreatmentDtoFactory().createDrugTreatmentDTO();
        		dto.setPatientId(treatment.getPatient());
        		dto.setDate(new Date());
        		dto.setDosage(treatment.getDrugTreatment().getDosage());
        		dto.setDrugName(treatment.getDrugTreatment().getName());
        		dto.setTreatmentId(treatment.getId());
        		logger.info("..."+dto.getDrugName());
        		logger.info("..."+dto.getDosage());
        		logger.info("..."+dto.getPatientId());
        		logger.info("..."+dto.getTreatmentId());
        		service.addDrugTreatmentRecord(dto);
        	}
        }catch (JMSException e) {
        	logger.info("----JMS Exp:"+e.toString());
		}
    }

}
