package it.synclab.patred.sem.rest;

import it.synclab.patred.sem.services.InfinispanService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/system")
public class SystemController extends BaseController {
	
	public static final String OK = "OK\n";
	public static final String KO = "KO (%s)\n";
	
	@Inject
	private InfinispanService infinispanService;
	
	@Inject
	public SystemController() {
	}
	
	@GET
	@Path("health")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSystemHealt() throws NamingException {
		
		boolean okStatus = true;
		StringBuilder sb = new StringBuilder("Stato del sistema:\n");
		
		InitialContext initContext = new InitialContext();
		
		// Connessione db inapp
		Connection connection = null;
		CallableStatement statement = null;
		
		try {
			
			sb.append("!!Connessione database sem:\t");
			oracle.ucp.jdbc.PoolDataSourceImpl dsSem = (oracle.ucp.jdbc.PoolDataSourceImpl) initContext.lookup("java:/comp/env/jdbc/semDbConnPool");
			connection = dsSem.getConnection();
			connection.isValid(10);
			//connection.commit();
			sb.append(OK);
			
		} catch (Exception e) {
			okStatus = false;
			sb.append(String.format(KO, e.getMessage()));
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				logger.error("Error cleaning resources", e);
			}
		}
		
		// Connessione Cache Condivisa
		try {
			sb.append("!!Connessione Cache Condivisa:\t");
			infinispanService.checkConnection();
			sb.append(OK);
		} catch (Exception e) {
			okStatus = false;
			sb.append(String.format(KO, e.getMessage()));
		}
		
		String entity = sb.toString();
		
		if (okStatus)
			return Response.ok(entity).build();
		return Response.serverError().entity(entity).build();
		
	}
	
}