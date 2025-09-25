package Database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.Server;

import API.ParkingScheduler;

@SuppressWarnings("unused")
@WebListener
public class Database implements ServletContextListener {

    private static final String URL = "jdbc:h2:mem:smartpark;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";
    private static boolean initialized = false;

    private static final Logger logger = LogManager.getLogger(Database.class);

    private Server webServer; // H2 Web console server

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("INITIALIZED: " + initialized);
        if (!initialized) {
        	
            try {
                // Load H2 driver
                Class.forName("org.h2.Driver");

                // Start H2 Web Console (optional, at http://localhost:8082)
                webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8085").start();
                logger.info("H2 Web Console started at: http://localhost:8085");

                
                DriverManager.getConnection(URL, USER, PASS);
                
                /*
                // Initialize schema and sample data
                try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                		
                	
                     Statement st = conn.createStatement()) {

                    st.execute("CREATE TABLE IF NOT EXISTS users(" +
                               "id INT AUTO_INCREMENT PRIMARY KEY, " +
                               "name VARCHAR(50))");

                    st.execute("INSERT INTO users(name) VALUES('Alice'),('Bob')");

                    logger.info("H2 in-memory DB initialized with sample data");
                             
                	}
             		
				*/
                initialized = true;
                
                //RUN SCHEDULER..
                ParkingScheduler scheduler = new ParkingScheduler(URL, USER, PASS);
                scheduler.start();
                
            } catch (ClassNotFoundException e) {
                logger.error("H2 Driver not found!", e);
               
            } catch (SQLException e) {
                logger.error("DB init failed", e);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down DB with app");
        if (webServer != null) {
            webServer.stop();
            logger.info("H2 Web Console stopped");
        }
    }
}