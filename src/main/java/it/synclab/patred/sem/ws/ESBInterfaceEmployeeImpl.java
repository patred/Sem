package it.synclab.patred.sem.ws;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.util.LogUtils;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@GuiceManaged
@WebService(endpointInterface = "it.synclab.patred.sem.ws.ESBInterfaceEmployee")
public class ESBInterfaceEmployeeImpl extends BaseWS implements ESBInterfaceEmployee {
	
	protected Logger logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
	
	@Override
	public void getEmployee(Long employeeId) {
		logger.info("employeeId: " + employeeId);
		
	}
	
}