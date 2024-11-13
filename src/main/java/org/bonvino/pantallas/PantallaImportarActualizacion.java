package org.bonvino.pantallas;

import org.bonvino.gestores.GestorImportarActualizacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class PantallaImportarActualizacion {

    private GestorImportarActualizacion gestor;

    private JList<String> listaBodegas;

    public PantallaImportarActualizacion() {
        GestorImportarActualizacion gestor = new GestorImportarActualizacion();
        this.setGestor(gestor);
    }

    public void tomarOpcionActualizacionVinos() {
        this.habilitarPantalla();
    }

    public void habilitarPantalla() {
        JFrame nuevaVentana = new JFrame("Bodegas Actualizables");
        nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaVentana.setSize(1024, 576);
        nuevaVentana.setLocationRelativeTo(null);
        nuevaVentana.setLayout(new BorderLayout());

        // Crea una lista para mostrar las bodegas
        listaBodegas = new JList<>();
        listaBodegas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaBodegas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("SansSerif", Font.PLAIN, 16));
                label.setForeground(new Color(41, 128, 185)); // Color del texto
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(41, 128, 185), 1, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                if (isSelected) {
                    label.setBackground(new Color(220, 220, 220));
                }
                return label;
            }
        });

        // Crea un panel principal para contener todos los componentes
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 245, 245)); // Fondo claro

        // Añade un título a la ventana
        JLabel tituloLabel = new JLabel("Bodegas Actualizables");
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloLabel.setForeground(new Color(34, 34, 34)); // Color del texto
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(tituloLabel, BorderLayout.NORTH);

        // Añade un mensaje de advertencia
        JLabel advertenciaLabel = new JLabel("Adevertencia:  " +
                "Seleccione solo una bodega a la vez!!");
        advertenciaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        advertenciaLabel.setForeground(new Color(255, 0, 0));
        advertenciaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(advertenciaLabel, BorderLayout.CENTER);

        // Agrega la lista de bodegas dentro de un JScrollPane al panel principal
        JScrollPane scrollPane = new JScrollPane(listaBodegas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panelPrincipal.add(scrollPane, BorderLayout.SOUTH);

        // Añade el panel principal a la ventana
        nuevaVentana.getContentPane().add(panelPrincipal);

        // Muestra la ventana
        nuevaVentana.setVisible(true);

        this.gestor.opcionActualizacionVinos(this);
    }

    private void setGestor(GestorImportarActualizacion gestor) {
        this.gestor = gestor;
    }

    public void mostrarBodegasActualizables(List<String> bodegasActualizables) {
        // Convertir la lista a un array para el JList
        listaBodegas.clearSelection();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String bodega : bodegasActualizables) {
            listModel.addElement(bodega);
        }
        listaBodegas.setModel(listModel);

        // Crea un botón para confirmar la selección
        JButton botonSeleccionar = new JButton("Seleccionar Bodega");
        botonSeleccionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonSeleccionar.setFocusPainted(false);
        botonSeleccionar.setForeground(Color.WHITE);
        botonSeleccionar.setBackground(new Color(41, 128, 185)); // Azul moderno
        botonSeleccionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonSeleccionar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        botonSeleccionar.setOpaque(true);
        botonSeleccionar.setBorderPainted(false);

        botonSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarSeleccionBodegaActualizar();
            }
        });

        // Crea un panel para el botón y agregarlo a la ventana
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(listaBodegas);
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBorder(new EmptyBorder(20, 0, 20, 0));
        panelBoton.setBackground(new Color(245, 245, 245)); // Fondo claro
        panelBoton.add(botonSeleccionar);
        ventana.getContentPane().add(panelBoton, BorderLayout.SOUTH);
    }

    public void tomarSeleccionBodegaActualizar(){
        String bodegaSeleccionada = listaBodegas.getSelectedValue();
        if (bodegaSeleccionada != null) {
            JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(listaBodegas);
            ventanaActual.dispose();  // Cierra la ventana actual
            gestor.tomarSeleccionBodega(bodegaSeleccionada, this);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una bodega.");
        }
    }


    public boolean mostrarResumenActualizacionesBodega(String bodegaSeleccionada, List<String> vinosActualizadosOCreados) {
        JFrame frame = new JFrame("Resumen de Bodegas Actualizadas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1024, 576);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Panel para el botón de retroceso
        JPanel panelRetroceso = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRetroceso.setBackground(new Color(245, 245, 245));

        JButton botonRetroceso = new JButton("⬅");
        botonRetroceso.setFont(new Font("SansSerif", Font.BOLD, 18));
        botonRetroceso.setFocusPainted(false);
        botonRetroceso.setForeground(Color.BLACK);
        botonRetroceso.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        botonRetroceso.setBackground(new Color(245, 245, 245));
        botonRetroceso.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botonRetroceso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cierra la pantalla actual
                habilitarPantalla(); // Vuelve a la pantalla anterior
            }
        });

        panelRetroceso.add(botonRetroceso);
        frame.add(panelRetroceso, BorderLayout.NORTH);

        JPanel panelNombreBodega = new JPanel();
        panelNombreBodega.setBackground(new Color(245, 245, 245));
        panelNombreBodega.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel labelNombreBodega = new JLabel(bodegaSeleccionada, SwingConstants.CENTER);
        labelNombreBodega.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelNombreBodega.setForeground(new Color(41, 128, 185));
        panelNombreBodega.add(labelNombreBodega);
        frame.add(panelNombreBodega, BorderLayout.CENTER);


        JPanel panelDatosVinos = new JPanel();
        panelDatosVinos.setLayout(new BoxLayout(panelDatosVinos, BoxLayout.Y_AXIS));
        panelDatosVinos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185)),
                "Datos de los Vinos Actualizados/Creados",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16),
                new Color(41, 128, 185)));
        panelDatosVinos.setBackground(new Color(255, 255, 255));


        if (vinosActualizadosOCreados.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "No se encontraron vinos actualizados o creados para la bodega seleccionada.",
                    "Sin vinos encontrados", JOptionPane.INFORMATION_MESSAGE);

            frame.dispose();
            new PantallaImportarActualizacion().habilitarPantalla(); // Regresa a la pantalla anterior
            return false;
        }


        for (String vinoInfo : vinosActualizadosOCreados) {
            JPanel vinoPanel = new JPanel();
            vinoPanel.setLayout(new BoxLayout(vinoPanel, BoxLayout.Y_AXIS)); // Usa BoxLayout en Y para apilar elementos
            vinoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            vinoPanel.setBackground(new Color(250, 250, 250)); // Fondo más claro
            vinoPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

            // Título del vino
            JLabel titleLabel = new JLabel("Vino Actualizado / Creado:"); // Título para cada vino
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            titleLabel.setForeground(new Color(41, 128, 185));
            titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
            vinoPanel.add(titleLabel);


            String formattedVinoInfo = vinoInfo.replace(", ", "<br> * ");


            if (formattedVinoInfo.contains("Maridajes:")) {
                String[] parts = formattedVinoInfo.split("Maridajes:");
                String maridajes = parts[1].replace("<br>", ", ");
                formattedVinoInfo = parts[0] + "Maridajes:" + maridajes;
            }

            formattedVinoInfo = "<html>* " + formattedVinoInfo.replace("\n", "<br>* ") + "</html>";
            formattedVinoInfo = formattedVinoInfo.replace("Maridajes:", "Maridajes:").replace("*", "");

            JLabel textArea = new JLabel(formattedVinoInfo);
            textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
            textArea.setForeground(new Color(34, 34, 34));

            textArea.setBackground(new Color(250, 250, 250));
            vinoPanel.add(textArea);
            vinoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            panelDatosVinos.add(vinoPanel);
            panelDatosVinos.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        JScrollPane scrollPaneVinos = new JScrollPane(panelDatosVinos);
        scrollPaneVinos.setBorder(BorderFactory.createEmptyBorder());
        frame.add(scrollPaneVinos, BorderLayout.CENTER);

        // Mostrar la ventana
        frame.setVisible(true);
        return !vinosActualizadosOCreados.isEmpty();
    }



}
