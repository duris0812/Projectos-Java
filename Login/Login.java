package poo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Login implements ActionListener, LoginInterface {
    private JFrame frame;
    private JPanel panel;
    private JLabel labelTitulo, labelUsuario, labelContraseña, labelAdvertencia;
    private JTextField campoUsuario;
    private JPasswordField campoContraseña;
    private JButton botonAceptar, botonSalir;
    private int intentosFallidos = 0;
    private static final int max_intentos = 3;
    private static final String CORRECT_PASSWORD = "0101";
    private String usuarioPermitido;

    public static void main(String[] args) {
        new Login().createAndShowGUI();
    }

    public void createAndShowGUI() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondoIcon = new ImageIcon(getClass().getResource("/poo/Background.jpg"));
                Image fondoOriginal = fondoIcon.getImage();
                g.drawImage(fondoOriginal, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        labelTitulo = new JLabel("Bienvenido");
        labelTitulo.setBounds(150, 10, 100, 20);
        labelTitulo.setForeground(Color.white);
        labelTitulo.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        panel.add(labelTitulo);

        labelUsuario = new JLabel("ID");
        labelUsuario.setBounds(30, 50, 100, 20);
        labelUsuario.setForeground(Color.white);
        labelUsuario.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        panel.add(labelUsuario);

        labelContraseña = new JLabel("Contraseña");
        labelContraseña.setBounds(30, 100, 100, 20);
        labelContraseña.setForeground(Color.white);
        labelContraseña.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        panel.add(labelContraseña);
        
        labelAdvertencia = new JLabel("(Solamente números)");
        labelAdvertencia.setBounds(130, 120, 120, 20);
        labelAdvertencia.setForeground(Color.white);
        labelAdvertencia.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        panel.add(labelAdvertencia);

        campoUsuario = new JTextField();
        campoUsuario.setBounds(120, 50, 150, 20);
        campoUsuario.setBackground(new Color(139, 87, 66));
        campoUsuario.setForeground(Color.white);
        campoUsuario.setBorder(new LineBorder(Color.white, 3));
        panel.add(campoUsuario);

        campoContraseña = new JPasswordField();
        campoContraseña.setBounds(120, 100, 150, 20);
        campoContraseña.setBackground(new Color(139, 87, 66));
        campoContraseña.setForeground(Color.white);
        campoContraseña.setBorder(new LineBorder(Color.white, 3));
        panel.add(campoContraseña);

        botonAceptar = new JButton("Log in");
        botonAceptar.setBounds(50, 150, 100, 30);
        panel.add(botonAceptar);
        botonAceptar.addActionListener(this);

        botonSalir = new JButton("Exit");
        botonSalir.setBounds(180, 150, 100, 30);
        panel.add(botonSalir);
        botonSalir.addActionListener(this);

        frame.add(panel);
        frame.setVisible(true);

        cargarUsuario();
    }

    private void cargarUsuario() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            usuarioPermitido = br.readLine().trim();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(frame, "Error en el archivo");
            System.out.println("Archivo no encontrado: " + fnfe);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(frame, "Error al leer el archivo.");
            System.out.println("Error al leer el archivo: " + ioe.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAceptar) {
            String usuario = campoUsuario.getText();
            String contraseña = new String(campoContraseña.getPassword());
            login(usuario, contraseña);
        }

        if ( e.getSource() == botonSalir) {
            exit();
        }
    }

    public void login(String usuario, String contraseña) {
        try {
            if (usuario.isEmpty() || contraseña.isEmpty()) {
                throw new NullPointerException("El ID o la contraseña están vacíos");
            }

            if (usuario.equals(usuarioPermitido) && CORRECT_PASSWORD.equals(contraseña)) {
                Horario horario1 = new Horario();
                horario1.setVisible(true);
                frame.dispose();
            } else {
                intentosFallidos++;
                if (intentosFallidos >= max_intentos) {
                    throw new IllegalArgumentException("Demasiados intentos fallidos");
                } else {
                    int intentosRestantes = max_intentos - intentosFallidos;
                    throw new IllegalArgumentException("Usuario o contraseña incorrectos. Intentos restantes: " + intentosRestantes);
                }
            }
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(frame, "Error... No ingresaste datos");
            System.out.println("Error: " + npe);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(frame, iae.getMessage());
            System.out.println("Error: " + iae);
            campoUsuario.setText("");
            campoContraseña.setText("");
            if (intentosFallidos >= max_intentos) {
                frame.dispose();
            }
        }
    }

    public void exit() {
        frame.dispose(); 
    }
}

