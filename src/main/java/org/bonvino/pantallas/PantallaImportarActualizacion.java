package org.bonvino.pantallas;

import org.bonvino.gestores.GestorImportarActualizacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        String bodegaSeleccionada = listaBodegas.getSelectedValue();
        if (bodegaSeleccionada != null) {
            // Llamar al método del controlador con la bodega seleccionada
            gestor.tomarSeleccionBodega(bodegaSeleccionada, this);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una bodega.");
        }
    }

    public void mostarResumenBodegasActualizadasCreadas(List<String> resumenVinosActualizados){

    }


}
