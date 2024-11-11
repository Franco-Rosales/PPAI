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

        // Crear una lista para mostrar las bodegas
        listaBodegas = new JList<>();
        listaBodegas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Personalizar el renderizador de celdas para estilizar los elementos de la lista
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
                label.setIcon(new ImageIcon("path/to/icon.png")); // Añadir ícono (opcional)
                if (isSelected) {
                    label.setBackground(new Color(220, 220, 220)); // Color de fondo cuando está seleccionado
                }
                return label;
            }
        });

        // Crear un panel principal para contener todos los componentes
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 245, 245)); // Fondo claro

        // Añadir un título a la ventana
        JLabel tituloLabel = new JLabel("Bodegas Actualizables");
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloLabel.setForeground(new Color(34, 34, 34)); // Color del texto
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(tituloLabel, BorderLayout.NORTH);

        // Agregar la lista de bodegas dentro de un JScrollPane al panel principal
        JScrollPane scrollPane = new JScrollPane(listaBodegas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Añadir el panel principal a la ventana
        nuevaVentana.getContentPane().add(panelPrincipal);

        // Mostrar la ventana
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

        // Crear un botón para confirmar la selección
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

        // Crear un panel para el botón y agregarlo a la ventana
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
            gestor.tomarSeleccionBodega(bodegaSeleccionada, this);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una bodega.");
        }
    }

    public boolean mostrarResumenActualizacionesBodega(String bodegaSeleccionada, List<String> vinosActualizadosOCreados) {
        // Crear la ventana
        JFrame frame = new JFrame("Resumen de Bodegas Actualizadas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1024, 576); // Tamaño más grande para acomodar más datos
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Panel para el nombre de la bodega
        JPanel panelNombreBodega = new JPanel();
        panelNombreBodega.setBackground(new Color(245, 245, 245));
        panelNombreBodega.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel labelNombreBodega = new JLabel(bodegaSeleccionada, SwingConstants.CENTER);
        labelNombreBodega.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelNombreBodega.setForeground(new Color(41, 128, 185));
        panelNombreBodega.add(labelNombreBodega);
        frame.add(panelNombreBodega, BorderLayout.NORTH);

        // Panel para los datos de los vinos
        JPanel panelDatosVinos = new JPanel();
        panelDatosVinos.setLayout(new BoxLayout(panelDatosVinos, BoxLayout.Y_AXIS));
        panelDatosVinos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185)),
                "Datos de los Vinos Actualizados/Creados",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16),
                new Color(41, 128, 185)));
        panelDatosVinos.setBackground(new Color(255, 255, 255)); // Fondo blanco

        if (vinosActualizadosOCreados.isEmpty()) {
            // Mostrar mensaje de advertencia si no hay vinos
            JOptionPane.showMessageDialog(frame,
                    "No se encontraron vinos actualizados o creados para la bodega seleccionada.",
                    "Sin vinos encontrados", JOptionPane.INFORMATION_MESSAGE);

            // Cerrar la ventana actual y permitir volver a la selección
            frame.dispose();
            return false;
        }

        // Estilo para los datos de los vinos
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

            // Procesar la información de cada vino para mostrarla como lista
            // Eliminar las comas para todos los campos excepto "Maridaje"
            String formattedVinoInfo = vinoInfo.replace(", ", "<br> * "); // Reemplazar comas por salto de línea con asterisco

            // Para Maridaje, debemos asegurarnos de que las comas se mantengan y no haya asteriscos
            if (formattedVinoInfo.contains("Maridajes:")) {
                // Procesamos Maridaje para mantener las comas en una sola línea y eliminar los asteriscos
                String[] parts = formattedVinoInfo.split("Maridajes:");
                String maridajes = parts[1].replace("<br>", ", ");  // Reemplazar saltos de línea con coma y espacio
                formattedVinoInfo = parts[0] + "Maridajes:" + maridajes;  // Reintegrar Maridaje con comas intactas
            }

            // Preparar la información en formato HTML para mostrar como lista
            formattedVinoInfo = "<html>* " + formattedVinoInfo.replace("\n", "<br>* ") + "</html>"; // Formato de lista con asteriscos

            // Eliminar los asteriscos de Maridaje
            formattedVinoInfo = formattedVinoInfo.replace("Maridajes:", "Maridajes:").replace("*", "");

            // Crear un JLabel con el texto formateado
            JLabel textArea = new JLabel(formattedVinoInfo);
            textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
            textArea.setForeground(new Color(34, 34, 34)); // Color del texto

            textArea.setBackground(new Color(250, 250, 250)); // Fondo más claro

            vinoPanel.add(textArea);

            // Añadir un espacio entre los vinos
            vinoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Agregar el panel del vino al panel principal
            panelDatosVinos.add(vinoPanel);
            panelDatosVinos.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre vinos
        }


        // Agregar el panel de datos de los vinos a un JScrollPane para scroll
        JScrollPane scrollPaneVinos = new JScrollPane(panelDatosVinos);
        scrollPaneVinos.setBorder(BorderFactory.createEmptyBorder());
        frame.add(scrollPaneVinos, BorderLayout.CENTER);

        // Mostrar la ventana
        frame.setVisible(true);
        return true;
    }



}
