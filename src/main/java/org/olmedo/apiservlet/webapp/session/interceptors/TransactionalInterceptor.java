package org.olmedo.apiservlet.webapp.session.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.olmedo.apiservlet.webapp.session.configs.MysqlConn;
import org.olmedo.apiservlet.webapp.session.service.ServiceJdbcException;

import java.sql.Connection;
import java.util.logging.Logger;

@TransactionalJdbc
@Interceptor
public class TransactionalInterceptor { // siempre las transacciones se hacen el la clase de servicios
    @Inject
    @MysqlConn
    private Connection conn;

    @Inject
    private Logger log;

    @AroundInvoke
    public Object transactional(InvocationContext invocation) throws Exception {
        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }
        try {
            log.info(" ------> Inciando transaccion " + invocation.getMethod().getName() +
                    " de la clase " + invocation.getMethod().getDeclaringClass());
            Object resultado = invocation.proceed();
            conn.commit();
            log.info(" ------> Realizando commit y finalizando transaccion " + invocation.getMethod().getName() +
                    " de la clase " + invocation.getMethod().getDeclaringClass());
            return resultado;
        } catch (ServiceJdbcException e) {
            conn.rollback();
            throw e;
        }
    }
}
