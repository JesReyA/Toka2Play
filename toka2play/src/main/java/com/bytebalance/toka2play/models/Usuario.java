package com.bytebalance.toka2play.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private String telefono;
    private String correo;
    private double saldo;

}