package com.mycompany.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    private JLabel eUsuario, eContraseña;
    private JTextField cUsuario;
    private JPasswordField campoContraseña;
    private JButton botonAceptar, botonLimpiar, botonSalir;

    public Login() {
        setLayout(null);

        eUsuario = new JLabel("Usuario");
        eUsuario.setBounds(10, 10, 100, 20);
        add(eUsuario);

        eContraseña = new JLabel("Contraseña");
        eContraseña.setBounds(10, 50, 100, 20);
        add(eContraseña);

        cUsuario = new JTextField();
       cUsuario.setBounds(120, 10, 150, 20);
        add(cUsuario);

        campoContraseña = new JPasswordField();
        campoContraseña.setBounds(120, 50, 150, 20);
        add(campoContraseña);

        botonAceptar = new JButton("Aceptar");
        botonAceptar.setBounds(40, 100, 100, 30);
        add(botonAceptar);
        botonAceptar.addActionListener(this);

        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setBounds(160, 100, 100, 30);
        add(botonLimpiar);
        botonLimpiar.addActionListener(this);

        botonSalir = new JButton("Salir");
        botonSalir.setBounds(280, 100, 100, 30);
        add(botonSalir);
        botonSalir.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAceptar) {
            String usuario = cUsuario.getText();
            String contraseña = new String(campoContraseña.getPassword());

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Alguno de los campos esta vacio");
            } else {
                if (usuario.equals("Sergio") && contraseña.equals("0101")) {
                    JOptionPane.showMessageDialog(null, "Inciando....");
                } else {
                    JOptionPane.showMessageDialog(null, "Error...revisa tu usuario o contraseña");
                }
            }
        }

        if (e.getSource() == botonLimpiar) {
            cUsuario.setText("");
            campoContraseña.setText("");
        }

        if (e.getSource() == botonSalir) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        Login ventanaLogin = new Login();
        ventanaLogin.setBounds(0, 0, 400, 200);
        ventanaLogin.setVisible(true);
        ventanaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
