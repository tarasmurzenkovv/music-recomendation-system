package configuration.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/database/hibernate.properties")
@EnableTransactionManagement
@EnableJpaRepositories("repository")
public class DataBaseContextConfiguration {
    private final static int EXIT_STATUS_CODE = 666;
    Logger logger = Logger.getLogger(DataBaseContextConfiguration.class);

    @Value("${hibernate.connection.username}")
    private String login;
    @Value("${hibernate.connection.password}")
    private String password;
    @Value("${hibernate.connection.url}")
    private String jdbcUrl;
    @Value("${hibernate.connection.driver_class}")
    private String driverName;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPackagesToScan("entities");
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public DataSource getDataSource() {
        // declare properties for c3p0 connection pool
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverName);
        } catch (PropertyVetoException e) {
            // this exception occurs when a property was provided with an invalid value.
            logger.error("Invalid driver was provided for a connection pool");
            System.exit(EXIT_STATUS_CODE);
        }
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(login);
        dataSource.setPassword(password);

        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database/hibernate.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Exception occurred whiles reading property file. Check if the file is in the app context.");
            System.exit(EXIT_STATUS_CODE);
        }

        return properties;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
