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
        System.out.println("PantallaImportarActualizacion");
        GestorImportarActualizacion gestor = new GestorImportarActualizacion();
        this.setGestor(gestor);
    }


    public void tomarOpcionActualizacionVinos() {
        System.out.println("tomarOpcionActualizacionVinos");
        this.habilitarPantalla();
    }

    public void habilitarPantalla() {
        System.out.println("habilitarPantalla");
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
        System.out.println("mostrarBodegasActualizables");

        //Todo: Mostrar mensaje en la pantalla para que pueda selecccionar una sola bodega a la vez

        // Convertir la lista a un array para el JList
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
                //solicitarSeleccionBodegas();
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

    //public void solicitarSeleccionBodegas(){
        //tomarSeleccionBodegaActualizar();
    //}

    public void tomarSeleccionBodegaActualizar(){
        System.out.println("tomarSeleccionBodegaActualizar");
        String bodegaSeleccionada = listaBodegas.getSelectedValue();
        if (bodegaSeleccionada != null) {
            gestor.tomarSeleccionBodega(bodegaSeleccionada, this);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una bodega.");
        }
    }

    public void mostrarResumenActualizacionesBodega(String bodegaSeleccionada,List<String> vinosActualizadosOCreados){
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
        panelDatosVinos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(41, 128, 185)),
                "Datos de los Vinos Actualizados/Creados",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16),
                new Color(41, 128, 185)));
        panelDatosVinos.setBackground(new Color(255, 255, 255)); // Fondo blanco
        if (vinosActualizadosOCreados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron vinos actualizados o creados para la bodega seleccionada.");
        }

        // Estilo para los datos de los vinos
        for (String vinoInfo : vinosActualizadosOCreados) {
            JPanel vinoPanel = new JPanel();
            vinoPanel.setLayout(new BorderLayout());
            vinoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            vinoPanel.setBackground(new Color(250, 250, 250)); // Fondo más claro
            vinoPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

            JLabel textArea = new JLabel(vinoInfo); // Ahora `vinoInfo` es un String
            textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
            textArea.setForeground(new Color(34, 34, 34)); // Color del texto
            textArea.setOpaque(true);
            textArea.setBackground(new Color(250, 250, 250)); // Fondo más claro

            vinoPanel.add(textArea, BorderLayout.CENTER);
            panelDatosVinos.add(vinoPanel);
            panelDatosVinos.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre paneles
        }
        // Agregar el panel de datos de los vinos a un JScrollPane para scroll
        JScrollPane scrollPaneVinos = new JScrollPane(panelDatosVinos);
        scrollPaneVinos.setBorder(BorderFactory.createEmptyBorder());
        frame.add(scrollPaneVinos, BorderLayout.CENTER);

        // Mostrar la ventana
        frame.setVisible(true);


    }


}
