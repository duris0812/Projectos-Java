package poo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.border.LineBorder;

public class Horario extends JFrame {

    private JLabel[] horas;
    private JLabel[] diasSemana;
    private static JTextField[][] actividades;
    private JButton botonEditar;
    private JButton botonDestacar;
    private JButton botonSiguiente;
    private JTextField cuadroSeleccionado;
    private JLabel backgroundLabel;

    public Horario() {
        setTitle("Horario de la Semana");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        try {
            java.net.URL imageUrl = getClass().getResource("/poo/FondoMc.jpg");
            if (imageUrl == null) {
                throw new FileNotFoundException("Imagen no encontrada en la ruta especificada: /poo/FondoMc.jpg");
            }

            ImageIcon backgroundImage = new ImageIcon(imageUrl);
            backgroundLabel = new JLabel(backgroundImage);
            backgroundLabel.setBounds(0, 0, getWidth(), getHeight());

            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setBounds(0, 0, getWidth(), getHeight());
            add(layeredPane);

            layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

            JPanel panelPrincipal = new JPanel();
            panelPrincipal.setOpaque(false);
            panelPrincipal.setLayout(null);
            panelPrincipal.setBounds(0, 0, getWidth(), getHeight());
            layeredPane.add(panelPrincipal, JLayeredPane.PALETTE_LAYER);

            String[] horasTexto = {"7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM"};
            horas = new JLabel[horasTexto.length];

            for (int i = 0; i < horasTexto.length; i++) {
                horas[i] = new JLabel(horasTexto[i]);
                horas[i].setBounds(40, 120 + i * 50, 120, 30);
                horas[i].setForeground(Color.WHITE);
                horas[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
                panelPrincipal.add(horas[i]);
            }

            String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
            diasSemana = new JLabel[dias.length];

            for (int i = 0; i < dias.length; i++) {
                diasSemana[i] = new JLabel(dias[i]);
                diasSemana[i].setBounds(200 + i * 150, 60, 120, 30);
                diasSemana[i].setForeground(Color.WHITE);
                diasSemana[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
                panelPrincipal.add(diasSemana[i]);
            }

            actividades = new JTextField[dias.length][8];

            for (int i = 0; i < dias.length; i++) {
                for (int j = 0; j < 8; j++) {
                    actividades[i][j] = new JTextField();
                    actividades[i][j].setBounds(200 + i * 150, 120 + j * 50, 120, 40);
                    actividades[i][j].setBackground(new Color(139, 87, 66));
                    actividades[i][j].setForeground(Color.WHITE);
                    actividades[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
                    actividades[i][j].setBorder(new LineBorder(Color.WHITE, 1));
                    actividades[i][j].setEditable(false);
                    actividades[i][j].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            cuadroSeleccionado = (JTextField) e.getSource();
                        }
                    });
                    panelPrincipal.add(actividades[i][j]);
                }
            }

            botonEditar = new JButton("Editar");
            botonEditar.setBounds(480, 550, 100, 30);
            botonEditar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            botonEditar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JTextField[] dia : actividades) {
                        for (JTextField actividad : dia) {
                            actividad.setEditable(true);
                        }
                    }
                }
            });
            layeredPane.add(botonEditar, JLayeredPane.MODAL_LAYER);

            botonDestacar = new JButton("Destacar");
            botonDestacar.setBounds(600, 550, 100, 30);
            botonDestacar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            botonDestacar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cuadroSeleccionado != null) {
                        cuadroSeleccionado.setBackground(Color.RED);
                    }
                }
            });
            layeredPane.add(botonDestacar, JLayeredPane.MODAL_LAYER);

            botonSiguiente = new JButton("Siguiente");
            botonSiguiente.setBounds(720, 550, 100, 30);
            botonSiguiente.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            botonSiguiente.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        pasarDatosAHorario2();
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null, "Error al guardar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    dispose();
                }
            });
            layeredPane.add(botonSiguiente, JLayeredPane.MODAL_LAYER);

        } catch (FileNotFoundException fnf) {
            JOptionPane.showMessageDialog(this, "Error: " + fnf.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error imagen no encontrada: " + fnf);
        }

        setVisible(true);
    }

    private void pasarDatosAHorario2() throws IOException {
        String[][] actividadesArray = new String[7][8];
        Color[][] coloresArray = new Color[7][8];

        for (int i = 0; i < actividades.length; i++) {
            for (int j = 0; j < actividades[i].length; j++) {
                actividadesArray[i][j] = actividades[i][j].getText();
                coloresArray[i][j] = actividades[i][j].getBackground();
            }
        }

        guardarActividades("actividades.txt");

        new Horario2(actividadesArray, coloresArray);
    }

   public static void guardarActividades(String nombreArchivo) throws IOException {
    String[] horasTexto = {"7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM"};
    String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    int colWidth = 20;  // Ajustar a 20 para más espacio

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
        // Escribir los días de la semana
        writer.write(String.format("%-" + colWidth + "s", "Horas\\Días"));
        for (String dia : dias) {
            writer.write(String.format("%-" + colWidth + "s", dia));
        }
        writer.newLine();

        // Escribir las actividades y las horas
        for (int i = 0; i < horasTexto.length; i++) {
            writer.write(String.format("%-" + colWidth + "s", horasTexto[i]));
            for (int j = 0; j < dias.length; j++) {
                writer.write(String.format("%-" + colWidth + "s", actividades[j][i].getText()));
            }
            writer.newLine();
        }
        System.out.println("Archivo guardado exitosamente.");
    } catch (IOException ioException) {
        System.out.println("Error al guardar el archivo: " + ioException);
        throw ioException;
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Horario::new);
    }
}
