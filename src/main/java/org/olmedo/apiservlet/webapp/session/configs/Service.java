package org.olmedo.apiservlet.webapp.session.configs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Stereotype;
import jakarta.inject.Named;
import org.olmedo.apiservlet.webapp.session.interceptors.Logging;
import org.olmedo.apiservlet.webapp.session.interceptors.TransactionalJdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TransactionalJdbc
@Logging  // si lo coloco al nivel de la clase de aplica para todos los metodos
@ApplicationScoped // el alternative es como excluir de la inyeccion
@Stereotype
@Named
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
}
