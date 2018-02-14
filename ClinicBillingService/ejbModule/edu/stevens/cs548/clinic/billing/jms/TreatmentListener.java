package edu.stevens.cs548.clinic.billing.jms;

import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sound.midi.MidiDevice.Info;

import edu.stevens.cs548.clinic.billing.BillingRecord;
import edu.stevens.cs548.clinic.billing.domain.BillingRecordDAO;
import edu.stevens.cs548.clinic.billing.domain.BillingRecordFactory;
import edu.stevens.cs548.clinic.billing.service.BillingDtoFactory;
import edu.stevens.cs548.clinic.billing.service.BillingService;
import edu.stevens.cs548.clinic.billing.service.IBillingService;
import edu.stevens.cs548.clinic.billing.service.IBillingService.BillingDTO;
import edu.stevens.cs548.clinic.billing.service.IBillingServiceLocal;
import edu.stevens.cs548.clinic.domain.TreatmentDAO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.domain.*;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Message-Driven Bean implementation class for: TreatmentListener
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destination", 
				propertyValue = "Treatment"), 
				@ActivationConfigProperty(
				propertyName = "destinationType", 
				propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms/Treatment")
public class TreatmentListener implements MessageListener {

    /**
     * Default constructor. 
     */
    @Inject
    @EJB(beanName="BillingRecordServiceBean")
	IBillingServiceLocal service;
    
    private static Logger logger = Logger.getLogger(BillingService.class.getCanonicalName());
    public TreatmentListener() {
    }
	
    @PersistenceContext(unitName="ClinicDomain")
    private EntityManager em;
    
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
    	logger.info("----Billing Message received.");
    	ObjectMessage objectMessage = (ObjectMessage)message;
    	TreatmentDto treatment;
    	try{
    		
    		treatment = (TreatmentDto) objectMessage.getObject();
    	
    		Random generator = new Random();
    		float amount = generator.nextFloat()*500;
    		
    		BillingDTO dto = new BillingDtoFactory().createBillingDTO();
    		dto.setAmount(amount);
    		dto.setDate(new Date());
    		dto.setDescription(treatment.getDiagnosis());
    		dto.setTreatmentId(treatment.getId());
    		//logger.info(dto.getDescription()+dto.getAmount()+dto.getTreatmentId());
    		service.addBillingRecord(dto);
    	}catch (JMSException e) {
			logger.info("----jms exp:"+e.toString());
		}
    }
}
