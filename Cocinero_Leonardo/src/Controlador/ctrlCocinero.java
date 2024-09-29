/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author Leonardo
 */
import Modelo.ClaseConexion;
import Modelo.Cocinero;
import Modelo.CocineroDAO;
import Vista.frmDashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class ctrlCocinero implements ActionListener {

    private frmDashboard view;
    private CocineroDAO model;
    private DefaultTableModel tableModel;

    public ctrlCocinero(frmDashboard view, CocineroDAO model) {
        this.view = view;
        this.model = model;

        // Asignar eventos a los botones
        this.view.btnGuardar.addActionListener(this);
        this.view.btnActualizar.addActionListener(this);
        this.view.btnEliminar.addActionListener(this);
        this.view.jtCocineros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRowClicked(evt);
            }
        });

        listarCocineros();  // Mostrar los datos al cargar el frmDashboard
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btnGuardar) {
            guardarCocinero();
        } else if (e.getSource() == view.btnActualizar) {
            actualizarCocinero();
        } else if (e.getSource() == view.btnEliminar) {
            eliminarCocinero();
        }
    }

    public void listarCocineros() {
        // Configuración de las columnas personalizadas
        String[] columnNames = {"ID", "Nombre", "Edad", "Peso", "Correo"};

        // Limpiamos el modelo de la tabla antes de agregar nuevos datos
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        view.jtCocineros.setModel(model);  // Asignamos el modelo con los títulos correctos

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión
            con = ClaseConexion.getConexion();
            if (con != null) {
                String sql = "SELECT * FROM tbCocinero";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                // Iteramos sobre el ResultSet y llenamos la tabla
                while (rs.next()) {
                    Object[] fila = new Object[5];  // Asegúrate de que sean 5 columnas
                    fila[0] = rs.getInt("ID_Cocinero");
                    fila[1] = rs.getString("Nombre_Cocinero");
                    fila[2] = rs.getInt("Edad_Cocinero");
                    fila[3] = rs.getDouble("Peso_Cocinero");
                    fila[4] = rs.getString("Correo_Cocinero");  // Asegúrate de agregar el correo

                    model.addRow(fila);  // Añadimos la fila al modelo
                }
            } else {
                System.out.println("Conexión fallida.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para guardar un nuevo cocinero
    private void guardarCocinero() {
        if (validarCampos()) {
            Cocinero cocinero = new Cocinero(
                    0, // El ID lo genera la base de datos
                    view.txtNombre.getText(),
                    Integer.parseInt(view.txtEdad.getText()),
                    Double.parseDouble(view.txtPeso.getText()),
                    view.txtCorreo.getText()
            );

            if (model.insertarCocinero(cocinero)) {
                JOptionPane.showMessageDialog(view, "Cocinero guardado exitosamente.");
                limpiarCampos();
                listarCocineros();
            } else {
                JOptionPane.showMessageDialog(view, "Error al guardar cocinero.");
            }
        }
    }

    // Método para actualizar un cocinero
    private void actualizarCocinero() {
        if (validarCampos()) {
            int selectedRow = view.jtCocineros.getSelectedRow();  // Obtener la fila seleccionada
            if (selectedRow != -1) {
                // Obtener el ID_Cocinero desde la tabla (primera columna)
                int idCocinero = Integer.parseInt(view.jtCocineros.getValueAt(selectedRow, 0).toString());

                // Crear el objeto Cocinero con los datos del formulario y el ID obtenido de la tabla
                Cocinero cocinero = new Cocinero(
                        idCocinero, // Usamos el ID obtenido de la tabla
                        view.txtNombre.getText(),
                        Integer.parseInt(view.txtEdad.getText()),
                        Double.parseDouble(view.txtPeso.getText()),
                        view.txtCorreo.getText()
                );

                if (model.actualizarCocinero(cocinero)) {
                    JOptionPane.showMessageDialog(view, "Cocinero actualizado exitosamente.");
                    limpiarCampos();
                    listarCocineros();  // Refrescar la tabla
                } else {
                    JOptionPane.showMessageDialog(view, "Error al actualizar cocinero.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Seleccione un cocinero de la tabla para actualizar.");
            }
        }
    }

// Método para eliminar un cocinero
    private void eliminarCocinero() {
        int selectedRow = view.jtCocineros.getSelectedRow();  // Obtener la fila seleccionada
        if (selectedRow != -1) {
            // Obtener el ID_Cocinero desde la tabla (primera columna)
            int idCocinero = Integer.parseInt(view.jtCocineros.getValueAt(selectedRow, 0).toString());

            if (model.eliminarCocinero(idCocinero)) {
                JOptionPane.showMessageDialog(view, "Cocinero eliminado exitosamente.");
                limpiarCampos();
                listarCocineros();  // Refrescar la tabla
            } else {
                JOptionPane.showMessageDialog(view, "Error al eliminar cocinero.");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Seleccione un cocinero de la tabla para eliminar.");
        }
    }

    // Método para validar los campos del formulario
    private boolean validarCampos() {
        if (view.txtNombre.getText().isEmpty() || view.txtEdad.getText().isEmpty()
                || view.txtPeso.getText().isEmpty() || view.txtCorreo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos los campos deben estar llenos.");
            return false;
        }
        return true;
    }

    // Método para limpiar los campos después de una operación
    private void limpiarCampos() {
        view.txtNombre.setText("");
        view.txtEdad.setText("");
        view.txtPeso.setText("");
        view.txtCorreo.setText("");
    }

    // Método para seleccionar un cocinero de la tabla y mostrarlo en los campos de texto
    private void tableRowClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = view.jtCocineros.getSelectedRow();
        if (selectedRow != -1) {
            // Rellenar los campos con los datos de la fila seleccionada
            view.txtNombre.setText(view.jtCocineros.getValueAt(selectedRow, 1).toString());
            view.txtEdad.setText(view.jtCocineros.getValueAt(selectedRow, 2).toString());
            view.txtPeso.setText(view.jtCocineros.getValueAt(selectedRow, 3).toString());
            view.txtCorreo.setText(view.jtCocineros.getValueAt(selectedRow, 4).toString());
        }
    }
}
