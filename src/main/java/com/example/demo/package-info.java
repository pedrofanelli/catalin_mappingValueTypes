
/**
 * 
 * In a Spring Boot project, the package-info.java file is typically used to define package-level 
 * annotations and documentation. This file can be placed in any package to provide additional information 
 * about the package and its contents.
 * 
 * Tenemos la generación de una entidad en la base usando 2 métodos, Hibernate/JPA y Spring Data
 * Spring Data también usa hibernate y jpa, pero permite evitar boilercode a cambio de rendimiento
 * En Spring Data tenemos la configuración que se realiza una vez, ya sea en un archivo .properties o en la configuracion de
 * beans; luego armamos repositorios con los métodos y chau.
 * En cambio, Hibernate/JPA puro necesitamos configurar un persistence.xml o una clase custom que forme el 
 * persistence unit, y luego en cada lugar que se vaya a usar debemos escribir mucho más codigo, formando
 * el entity manager, y escribiendo las queries, no tenemos la facilidad de los repositorios.
 * 
 * @author peter
 *
 */
@org.hibernate.annotations.GenericGenerator( // un generador CUSTOM de valor automático, donde la estrategia ahora se llama "type"
  name = "ID_GENERATOR",
  //strategy = "enhanced-sequence",
  type = org.hibernate.id.enhanced.SequenceStyleGenerator.class, // cambió, ahora debemos setearlo de esta manera
  parameters = {
     @org.hibernate.annotations.Parameter(
        name = "sequence_name",
        value = "SETTED_BY_ME_SEQUENCE"
     ),
     @org.hibernate.annotations.Parameter(
        name = "initial_value",
        value = "1616"
     )
})
package com.example.demo;