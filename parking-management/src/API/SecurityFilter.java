package API;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import Auth.ErrorCode;
import Token.JwToken;
import Token.ValidateToken;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class SecurityFilter implements ContainerRequestFilter{

	public static ValidateToken validateToken = null;
	private Logger logger = null;
	public static String token = null;
	
	public JSONObject result = null;

	public static String username = "";
	public static String password = "";
	

    
	public SecurityFilter() {
		this.logger = LogManager.getLogger(SecurityFilter.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		this.logger.debug("Processing authorization");
		
		result = new JSONObject();
		   if (isPreflightRequest(requestContext)) {
			   
			   logger.info("THIS IS REQUEST PREFLIGHT..");
			   requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
			   requestContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
			   requestContext.getHeaders().add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD");
			   requestContext.getHeaders().add("Access-Control-Allow-Headers",
	                // Whatever other non-standard/safe headers (see list above) 
	                // you want the client to be able to send to the server,
	                // put it in this list. And remove the ones you don't want.
	                "X-Requested-With, Authorization, Accept-Version, Content-MD5, CSRF-Token, Content-Type");
	       return;
		   }
		   
	 
		   
	     
	        
	            
	        
		   requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		   requestContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		   requestContext.getHeaders().add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD");
		   requestContext.getHeaders().add("Access-Control-Allow-Headers",
                // Whatever other non-standard/safe headers (see list above) 
                // you want the client to be able to send to the server,
                // put it in this list. And remove the ones you don't want.
                "X-Requested-With, Authorization, Accept-Version, Content-MD5, CSRF-Token, Content-Type");
		   
		   if (requestContext.getHeaderString("Origin") != null) {
			   requestContext.getHeaders().add("Access-Control-Allow-Origin",
	                    requestContext.getHeaderString("Origin"));
	 
			   requestContext
	                    .getHeaders()
	                    .add("Access-Control-Expose-Headers",
	                            "Content-Type, Location, Link, Accept, Allow, Retry-After");
	 
			   requestContext.getHeaders().add("Access-Control-Allow-Headers",
	                    "Origin, Content-Type, Accept");
	 
			   requestContext.getHeaders().add(
	                    "Access-Control-Allow-Credentials", "true");
	 
			   requestContext.getHeaders().add("Access-Control-Allow-Methods",
	                    "OPTIONS, HEAD, GET, POST, PUT, PATCH, DELETE");
			   
			   requestContext.getHeaders().add("Cache-Control", "no-cache, no-store, must-revalidate");
			   requestContext.getHeaders().add("Pragma", "no-cache");
	        }

		
		this.logger.info("ALL HEADERS: " + requestContext.getHeaders());
		this.logger.info("REQUEST CONTEXT: " + requestContext.getHeaderString("OPTIONS"));
		
		
		this.logger.debug("Processing authorization");
		
				String authorization = requestContext.getHeaderString("Authorization");
				
				
				if (authorization == null) {
					this.logger.debug("Authorization header is missing");
		

					result.put("message",ErrorCode.INVALID_CLIENT);
					result.put("status","unauthorized");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(result).build());
					return;
				}
			
				String[] splitAuth = StringUtils.split(authorization);
				if (splitAuth.length != 2) {
					this.logger.debug("Authorization header is invalid");
					result.put("message",ErrorCode.INVALID_CLIENT);
					result.put("status","unauthorized");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(result).build());
					return;
				}
				
				
				String method = splitAuth[0];
				if (method.equals("Bearer") == false) {
					this.logger.debug("Unsupported authorization method");
					result.put("message",ErrorCode.INVALID_CLIENT);
					result.put("status","unauthorized");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(result).build());
					return;
				}
				
				JwToken tokenHelper = JwToken.getInstance();
				token = splitAuth[1];
				
				
				try {
					this.logger.debug("Validating token..");
					
					
					validateToken = tokenHelper.validateToken(splitAuth[1]);
					

					
				} catch (ExpiredJwtException ex) {
					this.logger.debug("Token expired");
					result.put("message",ErrorCode.TOKEN_EXPIRED);
					result.put("status","unauthorized");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(result).build());
					return;
				} catch (MalformedJwtException ex) {
					this.logger.debug("Malformed token");
					result.put("message",ErrorCode.INVALID_CLIENT);
					result.put("status","unauthorized");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(result).build());
					return;
				} catch (SignatureException ex) {
					this.logger.debug("Invalid token signature");
					
					result.put("message",ErrorCode.INVALID_CLIENT);
					result.put("status","unauthorized");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(result).build());
					return;
				}
				
		
				
				if (validateToken == null) {
					this.logger.debug("Token is invalid");
					result.put("message",ErrorCode.INVALID_CLIENT);
					result.put("status","unauthorized");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(result).build());
					return;
				}


				this.logger.trace("Request is valid");
				
				  if (requestContext.getHeaderString("Origin") == null) {
			            return;
				  }
				
			

	}


	  private static boolean isPreflightRequest(ContainerRequestContext request) {
	        return request.getHeaderString("Origin") != null
	                && request.getMethod().equalsIgnoreCase("OPTIONS");
	    }

	public static String getMd5(String input)
	    {
	        try {
	 
	            // Static getInstance method is called with hashing MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	 
	            // digest() method is called to calculate message digest
	            // of an input digest() return array of byte
	            byte[] messageDigest = md.digest(input.getBytes());
	 
	            // Convert byte array into signum representation
	            BigInteger no = new BigInteger(1, messageDigest);
	 
	            // Convert message digest into hex value
	            String hashtext = no.toString(16);
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	            return hashtext;
	        }
	 
	        // For specifying wrong message digest algorithms
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }
	
}
