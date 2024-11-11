package org.bonvino.pantallas;

import org.bonvino.interfaces.IObservadorActualizacionDeBodega;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class InterfazNotificacion implements IObservadorActualizacionDeBodega {

    public InterfazNotificacion() {
    }

    public void actualizarNovedades(String nombreBodega, List<String> vinos, List<String> usuarioEnofilo, Date fechaHoraActual) {
        // Crear y configurar la ventana de notificación
        JFrame frame = new JFrame("Notificación de Novedades");
        frame.setSize(600, 400);
        frame.setLocation(0, 0); // Esquina superior izquierda
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));

        // Saludo personalizado
        JLabel saludo = new JLabel("¡Hola, " + usuarioEnofilo.get(0) + "!");
        saludo.setFont(new Font("SansSerif", Font.BOLD, 24));
        saludo.setForeground(new Color(41, 128, 185));
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(saludo);

        // Mensaje de novedades de bodega
        JLabel mensajeBodega = new JLabel("Hay nuevas Actualizaciones para la bodega " + nombreBodega);
        mensajeBodega.setFont(new Font("SansSerif", Font.PLAIN, 18));
        mensajeBodega.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajeBodega.setForeground(new Color(34, 34, 34));
        panel.add(mensajeBodega);

        // Mensaje de invitación
        JLabel mensajeInvitacion = new JLabel("¡Échale un vistazo a lo nuevo!");
        mensajeInvitacion.setFont(new Font("SansSerif", Font.ITALIC, 16));
        mensajeInvitacion.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajeInvitacion.setForeground(new Color(34, 34, 34));
        panel.add(mensajeInvitacion);

        // Lista estilizada de vinos
        JPanel vinosPanel = new JPanel();
        vinosPanel.setLayout(new BoxLayout(vinosPanel, BoxLayout.Y_AXIS));
        vinosPanel.setBackground(new Color(255, 255, 255));
        vinosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String vino : vinos) {
            JPanel vinoPanel = new JPanel(new BorderLayout());
            vinoPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
            vinoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            vinoPanel.setBackground(new Color(250, 250, 250));

            // Aquí reemplazamos las comas por saltos de línea para mostrar los detalles
            String formattedVino = vino.replace(",", "<br> - ").replace("\n", "<br>");
            formattedVino = "<html> " + formattedVino + "</html>";

            JLabel vinoLabel = new JLabel(formattedVino);
            vinoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            vinoLabel.setForeground(new Color(34, 34, 34));
            vinoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            vinoPanel.add(vinoLabel, BorderLayout.CENTER);
            vinosPanel.add(vinoPanel);
            vinosPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre vinos
        }

        JScrollPane vinosScrollPane = new JScrollPane(vinosPanel);
        vinosScrollPane.setPreferredSize(new Dimension(500, 200));
        vinosScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185)),
                "Vinos Nuevos y Vinos Actualizados",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16),
                new Color(41, 128, 185)
        ));

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(vinosScrollPane);

        // Mensaje de fecha de actualización
        JLabel mensajeFecha = new JLabel("Estas actualizaciones son correspondientes con la siguiente fecha: " + fechaHoraActual.toString());
        mensajeFecha.setFont(new Font("SansSerif", Font.ITALIC, 12));
        mensajeFecha.setForeground(new Color(150, 150, 150));
        mensajeFecha.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(mensajeFecha);

        frame.add(panel);
        frame.setVisible(true);
    }



}
