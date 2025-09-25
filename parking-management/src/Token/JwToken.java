package Token;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import Config.Config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwToken {


    private static JwToken jwTokenHelper = null;
	public static String username = "";
	public static String password = "";

    //private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
	
	//SECRET KEY CAN USE TO ENCRYPT FOR MORE SECURE..
    private static String SECRET = "xK9vQ7s8Lp4wT2uZr1yE3cV5bH6nA0mD";
    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    
    private JwToken() {
    	
    }

    
    public static JwToken getInstance() {
        if (jwTokenHelper == null) {
            jwTokenHelper = new JwToken();
        }
        
        return jwTokenHelper;
    }


    
    //VALIDATION OF JWTOKEN
    public ValidateToken validateToken(String token) {

        	Jws<Claims> claims = Jwts
                    .parserBuilder() 
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
        	
        	
        	String subject = claims.getBody().getSubject();
        	String psswd = claims.getBody().get("pwd", String.class);
        	
      
        	if (subject == null) {
        		return null;
        	}

        	Config config = new Config();
        	
			username = config.getString("test.username");
			password = config.getString("test.password");
			
			if(!username.equals(subject) || !password.equals(psswd))
			{
				//invalid token..
				System.out.println("INVALID TOKEN..");
				return null;
			}
			
            System.out.println("Token is valid");

        	ValidateToken ret = new ValidateToken();
        	ret.setSubject(subject);
        	
        	return ret;
        	

      
    }
    
}
