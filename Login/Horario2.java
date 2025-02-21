package poo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class Horario2 extends JFrame {

    private JLabel[] horas;
    private JLabel[] diasSemana;
    private JLabel[][] actividadesDetalle;
    private JButton botonSalir;

    public Horario2(String[][] actividadesArray, Color[][] coloresArray) {
        try {
            // Verificar arreglos
            if (actividadesArray.length != 7 || coloresArray.length != 7) {
                throw new ArrayIndexOutOfBoundsException("Los arreglos deben tener 7 días");
            }
            for (int i = 0; i < 7; i++) {
                if (actividadesArray[i].length != 8 || coloresArray[i].length != 8) {
                    throw new ArrayIndexOutOfBoundsException("Error en el arreglo");
                }
            }

            setTitle("Detalles del Horario");
            setSize(1280, 720);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(false);
            setLayout(null);

            // Fondo 
            ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/poo/Fondo.jpg"));
            JLabel backgroundLabel = new JLabel(backgroundImage);
            backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
            add(backgroundLabel);

            // Panel 
            JPanel panelDetalle = new JPanel();
            panelDetalle.setOpaque(false);
            panelDetalle.setLayout(null);
            panelDetalle.setBounds(0, 0, getWidth(), getHeight());
            backgroundLabel.add(panelDetalle);

            // Configurar las horas a la izquierda
            String[] horasTexto = {"7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM"};
            horas = new JLabel[horasTexto.length];

            for (int i = 0; i < horasTexto.length; i++) {
                horas[i] = new JLabel(horasTexto[i]);
                horas[i].setBounds(40, 120 + i * 50, 120, 30);
                horas[i].setForeground(Color.WHITE);
                horas[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
                panelDetalle.add(horas[i]);
            }

            // Configurar los días de la semana en la parte superior
            String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
            diasSemana = new JLabel[dias.length];

            for (int i = 0; i < dias.length; i++) {
                diasSemana[i] = new JLabel(dias[i]);
                diasSemana[i].setBounds(200 + i * 150, 60, 120, 30);
                diasSemana[i].setForeground(Color.WHITE);
                diasSemana[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
                panelDetalle.add(diasSemana[i]);
            }

            botonSalir = new JButton("Salir");
            botonSalir.setBounds(520, 550, 100, 30);
            botonSalir.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            botonSalir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose(); // Cierra la ventana
                }
            });
            panelDetalle.add(botonSalir);

            // Actividades
            actividadesDetalle = new JLabel[dias.length][8];

            for (int i = 0; i < dias.length; i++) {
                for (int j = 0; j < 8; j++) {
                    // Verificación de índices antes de acceder a los arreglos
                    if (i >= actividadesArray.length || j >= actividadesArray[i].length) {
                        throw new ArrayIndexOutOfBoundsException("Índice fuera de rango para actividadesArray.");
                    }
                    if (i >= coloresArray.length || j >= coloresArray[i].length) {
                        throw new ArrayIndexOutOfBoundsException("Índice fuera de rango para coloresArray.");
                    }
                    actividadesDetalle[i][j] = new JLabel(actividadesArray[i][j]);
                    actividadesDetalle[i][j].setBounds(200 + i * 150, 120 + j * 50, 120, 40);
                    actividadesDetalle[i][j].setOpaque(true);
                    actividadesDetalle[i][j].setBackground(coloresArray[i][j]);
                    actividadesDetalle[i][j].setForeground(Color.WHITE);
                    actividadesDetalle[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
                    actividadesDetalle[i][j].setBorder(new LineBorder(Color.WHITE, 1));
                    panelDetalle.add(actividadesDetalle[i][j]);
                }
            }

            setVisible(true);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            JOptionPane.showMessageDialog(this, "Error: " + aioobe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.print("Error en el arreglo "+ aioobe);
        }
    }

    public static void main(String[] args) {
        
    }
}
