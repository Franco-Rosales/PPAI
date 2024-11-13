package org.bonvino.pantallas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaInicial {

    public PantallaInicial(){
    }

    public void mostrarPantalla(){
        JFrame frame = new JFrame("Importar Actualización de Vinos de Bodega");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 576);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));
        panelPrincipal.setBackground(new Color(245, 245, 245));

        JLabel usuarioLabel = new JLabel("Administrador de BonVino");
        usuarioLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        usuarioLabel.setForeground(new Color(34, 34, 34));
        usuarioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcionLabel = new JLabel("<html><h3>Descripción de Uso</h3><p>Esta aplicación tiene como finalidad actualizar o crear los datos de una bodega seleccionada por el administrador, para poder continuar con esta ejecución haga click en el siguiente botón:</p></html>");
        descripcionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descripcionLabel.setForeground(new Color(100, 100, 100));
        descripcionLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        descripcionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelPrincipal.add(usuarioLabel);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(descripcionLabel);

        JButton boton = new JButton("Importar Actualización de Vinos de Bodega");
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(41, 128, 185));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        boton.setOpaque(true);
        boton.setBorderPainted(false);

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PantallaImportarActualizacion pantalla = new PantallaImportarActualizacion();
                frame.dispose();
                pantalla.tomarOpcionActualizacionVinos();
            }
        });

        JPanel botonPanel = new JPanel();
        botonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        botonPanel.setBackground(new Color(245, 245, 245));
        botonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0));
        botonPanel.add(boton);

        frame.add(panelPrincipal, BorderLayout.CENTER);
        frame.add(botonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

}

