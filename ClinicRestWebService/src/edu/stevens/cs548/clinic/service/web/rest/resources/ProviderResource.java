package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.net.URL;
import java.security.ProviderException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.representations.PatientRepresentation;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(ProviderResource.class.getCanonicalName());
	
	/*
	 * TODO inject using HK2 (not CDI)
	 */
	@SuppressWarnings("unused")
	@Context
    private UriInfo uriInfo;
    
    private ProviderDtoFactory providerDtoFactory;

    /**
     * Default constructor. 
     */
    public ProviderResource() {
		/*
		 * TODO finish this
		 */
    	providerDtoFactory = new ProviderDtoFactory();
    }
    
	/*
	 * TODO inject using CDI
	 */
    @Inject
    @EJB(beanName="ProviderServiceBean")
    private IProviderServiceLocal providerService;
    
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return providerService.siteInfo();
    }

	/*
	 * TODO input should be application/xml
	 */
    @POST
    @Consumes("application/xml")
    public Response addProvider(ProviderRepresentation providerRep) {
    	try {
    		ProviderDto dto = providerDtoFactory.createProviderDto();
    		dto.setName(providerRep.getName());
    		dto.setProviderId(providerRep.getProviderId());
    		dto.setSpec(providerRep.getSpec());
    		long id = providerService.addProvider(dto);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    @POST
    @Path("{id}/treatments")
    @Consumes("application/xml")
    public Response addTreatment(TreatmentRepresentation treatmentRepresentation, @PathParam("id") String id) {
    		try {
    			TreatmentDto dto = treatmentRepresentation.getTreatment();
    			long prid_ = Long.parseLong(id);
    			long tid = providerService.addTreatmnet(dto, prid_, dto.getPatient());
    			UriBuilder uBuilder = uriInfo.getAbsolutePathBuilder().path("{tid}");
    			URI uri = uBuilder.build(Long.toString(tid));
    			return Response.created(uri).build();
			} catch (PatientNotFoundExn e) {
				throw new WebApplicationException();
			} catch (ProviderServiceExn e) {
				throw new WebApplicationException();
			}

	}
    
	/**
	 * Query methods for patient resources.
	 */
	/*
	 * TODO output should be application/xml
	 */
    @GET
    @Path("{id}")
    @Produces("application/xml")
	public ProviderRepresentation getProvider(@PathParam("id") String id) {
		try {
			long key = Long.parseLong(id);
			ProviderDto providerDTO = providerService.getProvider(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException();
		}
	}
    
	/*
	 * TODO output should be application/xml
	 */
    @GET
    @Path("byNPI")
    @Produces("application/xml")
	public ProviderRepresentation getProviderByProviderId(@QueryParam("id") String providerId) {
		try {
			long pid = Long.parseLong(providerId);
			ProviderDto providerDTO = providerService.getProviderByPId(pid);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException();
		}
	}
	/*
	 * TODO output should be application/xml
	 */
    @GET
    @Path("{id}/treatments/{tid}")
    @Produces("application/xml")
    public TreatmentRepresentation getProviderTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
    		return treatmentRep;
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}catch (TreatmentNotFoundExn e) {
			throw new WebApplicationException();
		}
    }

}