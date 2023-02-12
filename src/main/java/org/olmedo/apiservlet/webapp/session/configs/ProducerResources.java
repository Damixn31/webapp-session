package org.olmedo.apiservlet.webapp.session.configs;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@ApplicationScoped// cuando le pasamos el annotated al en WEB-INF beans.xml si no lo colocamos va lanzar error si no aplicamos esta inyeccion
public class ProducerResources {

    @Inject
    private Logger log;
    @Resource(name="jdbc/mysqlDB")
    private DataSource ds;
    @Produces
    @RequestScoped
    @MysqlConn
   // @Named("conn") // le damos un nombre de connecion asi que cuando lo usemos le vamos a indicar que vamos a usar esta coneccion enlazada a este nombre
    private Connection beansConnection() throws NamingException, SQLException {
        //esto se remplaza con una simple anotacion y un atributo con Datasource ds
        //Context initContext = new InitialContext();
        //Context envContext = (Context) initContext.lookup("java:/comp/env");
       // DataSource ds = (DataSource) envContext.lookup("jdbc/mysqlDB");
        return ds.getConnection();
    }

    @Produces
    private Logger beanLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());//nos da la informacion de la meta data de la clase que vamos a inyectar el logger
    }

    public void close(@Disposes @MysqlConn Connection connection) throws SQLException {
        // Con el @Disponses podemos tener varias vases de datos y pasarle la que queremos cerrar en este caso cerramos @MysqlConn.
        // no solo con la base de datos tambien con cualquier beans que queremos finalizar
        connection.close();
        log.info("Cerrando la conexion de la bbdd mysql!");
    }
}
