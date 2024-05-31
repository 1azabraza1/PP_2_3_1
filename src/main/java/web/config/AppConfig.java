package web.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class AppConfig {

    private Environment env;

    @Autowired
    public AppConfig(Environment env) {
        this.env = env;
    }
    //Метод, который вернет объект dataSource для соединения к нашей бд, используя данные из файла db.properties
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    // метод, который создаст EntityManagerFactory, который будет управляться контейнером
    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(getDataSource());
        entityManager.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaProperties(getHibernateProperties());
        return entityManager;
    }


    //метод, который возвращает объект Properties, в который переданы свойства
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    @Bean
    public JpaTransactionManager getTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return transactionManager;
    }
}





//@Configuration
//@EnableAspectJAutoProxy
//@EnableTransactionManagement
//@PropertySource("classpath:db.properties")
//public class AppConfig {
//
//    private final Environment env;
//
//    public AppConfig(Environment env) {
//        this.env = env;
//    }
//
//    @Bean
//    public DataSource dataSource() throws PropertyVetoException {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(env.getProperty("db.driver"));
//        dataSource.setJdbcUrl(env.getProperty("db.url"));
//        dataSource.setUser(env.getProperty("db.username"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean(); // HibernateExceptions, PersistenceExceptions... to DataAccessException
//        em.setDataSource(dataSource());
//        em.setPackagesToScan(env.getRequiredProperty("db.employee.packages"));
//        em.setJpaVendorAdapter(vendorAdapter);
//        return em;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//        return transactionManager;
//    }
//}


//@Configuration
//@EnableJpaRepositories()
//@PropertySource("classpath:db.properties")
//@EnableTransactionManagement
//@ComponentScan(value = "web")
//
//
//
//public class AppConfig {
//
//    private Environment env;
//
//    @Autowired
//    public AppConfig(Environment env) {
//        this.env = env;
//    }
//
////    @Bean
////    public LocalSessionFactoryBean getSessionFactory() {
////        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
////        factoryBean.setDataSource(getDataSource());
////
////        Properties props = new Properties();
////        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
////        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
////
////        factoryBean.setHibernateProperties(props);
////
////        return factoryBean;
////    }
////
////    @Bean
////    public HibernateTransactionManager getTransactionManager() {
////        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
////        transactionManager.setSessionFactory(getSessionFactory().getObject());
////        return transactionManager;
////    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(dataSource());
//        emf.setPackagesToScan("web/models");
//        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        emf.setJpaProperties(getHibernateProperties());
//        return emf;
//    }
//
//    private Properties getHibernateProperties() {
//        Properties props = new Properties();
//        props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
//        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        return props;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("db.driver"));
//        dataSource.setUrl(env.getProperty("db.url"));
//        dataSource.setUsername(env.getProperty("db.username"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }
//@Configuration
//
//@PropertySource("classpath:db.properties")
//@EnableTransactionManagement
//@ComponentScan(value = "web")
//
//public class AppConfig {
//    private Environment env;
//
//    @Autowired
//    public AppConfig(Environment env) {
//        this.env = env;
//    }
//
//    @Bean
//    public DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("db.driver"));
//        dataSource.setUrl(env.getProperty("db.url"));
//        dataSource.setUsername(env.getProperty("db.username"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public LocalSessionFactoryBean getSessionFactory() {
//        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//        factoryBean.setDataSource(getDataSource());
//
//        Properties props = new Properties();
//        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//
//        factoryBean.setHibernateProperties(props);
//
//        return factoryBean;
//    }
//
//    @Bean
//    public HibernateTransactionManager getTransactionManager() {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(getSessionFactory().getObject());
//        return transactionManager;
//    }
//}
