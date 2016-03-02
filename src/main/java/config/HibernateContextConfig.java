package config;

/**
 * Created by akarthik10 on 11/14/2015.
 */
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by rg on 04-Sep-15.
 */


@Configuration
@EnableTransactionManagement
public class HibernateContextConfig {

    //    TODO : Have to remove this create after the first time server is started. It creates the tables, but second time if restarted, it will drop existing tables and add again ! so loss of data could occur. So second time, should remove !
    private static boolean createNewTables = false;

    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        if (createNewTables) {
            properties.put("hibernate.hbm2ddl.auto", "create");
        } else {
            properties.put("hibernate.hbm2ddl.auto", "update");
        }
        /*
         */
        //properties.put("hibernate.current_session_context_class", "thread");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {

    	String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		String username = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
		String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        String mysql_url = System.getenv("OPENSHIFT_MYSQL_DB_URL");
        /*String mysql_url = "mysql://localhost:3306/";
        String username = "root";
        String password = "";*/

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        String URL = "jdbc:"+ mysql_url + "seveneleven";

        String hostx = "jdbc:mysql://" 
              + System.getenv().get("OPENSHIFT_MYSQL_DB_HOST") 
              + ":" 
              + System.getenv().get("OPENSHIFT_MYSQL_DB_PORT") 
              + "/seveneleven";

        dataSource.setUrl(hostx);
        dataSource.setUsername("akarthik10");
        dataSource.setPassword("01041957");
        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.scanPackages("models");

        //sessionBuilder.addAnnotatedClasses(User.class);  need to add annotated classes when annotations are used

        // this statement tells Hibernate to load the User class into its mapping definitions:

        /*

        1) to add more classes :
        sessionBuilder.addAnnotatedClasses(User.class, Object.class);

        2) scan packages for annotated classes:
        sessionBuilder.scanPackages("common.model");

        */

        sessionBuilder.addProperties(getHibernateProperties()); // hibernate related properties added to session factory object
        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(
            SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory);

        /* Creating the transaction Manager bean and using @Transactional annotation avoids the below boiler plate code used for
            managing transactions in DAOImpl like :
                 Session session = factory.openSession();
                  Transaction tx = null;
                  try{
                     tx = session.beginTransaction();
                     Employee employee = new Employee();
                     employee.setFirstName(fname);
                     tx.commit();
                  }catch (HibernateException e) {
                     if (tx!=null) tx.rollback();
                     e.printStackTrace();
                  }finally {
                     session.close();
                  }
        */

        return transactionManager;
    }

}