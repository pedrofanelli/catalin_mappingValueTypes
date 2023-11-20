

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