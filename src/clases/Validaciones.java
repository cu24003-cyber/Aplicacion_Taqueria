package clases;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Validaciones {

    //validar solo numeros
    public void validarNumeros(KeyEvent evt) {
        char c = evt.getKeyChar();

        // Permitir borrar con backspace
        if (c == KeyEvent.VK_BACK_SPACE) {
            return;
        }

        // Si no es número, bloquear
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }
    // Método general que valida si un carácter es permitido

    public void validarLetras(KeyEvent evt, JTextField txt) {
        char c = evt.getKeyChar();
        String text = txt.getText();

        // Permitir letras
        if (Character.isLetter(c)) {
            return;
        }

        // Permitir espacio si no es el primero ni repetido
        if (c == ' ' && !text.isEmpty() && text.charAt(text.length() - 1) != ' ') {
            return;
        }

        // Bloquear lo demás
        evt.consume();
    }
 

    //Metodo para verificar si los campos estan vacios
    public boolean validarCampoVacio(JTextField campo, String mensaje) {
        if (campo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, mensaje);
            campo.requestFocus();
            return false;
        }
        return true;
    }

    // Método genérico para validar ComboBox
    public boolean validarComboVacio(JComboBox<?> combo, String mensaje) {
        if (combo.getSelectedIndex() <= 0) { // posición 0 = "Seleccione..."
            JOptionPane.showMessageDialog(null, mensaje);
            combo.requestFocus();
            return false;
        }
        return true;
    }

    // Valida que se haya seleccionado un radio button
    public boolean validarRadio(ButtonGroup grupo, String mensaje) {
        if (grupo.getSelection() == null) { // Ningún botón seleccionado
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
        return true;
    }

    // Validar formato de fecha dd/MM/yyyy
    public void validarFecha(KeyEvent evt, JTextField txt) {
        char c = evt.getKeyChar();
        String text = txt.getText();

        // Permitir borrar
        if (c == KeyEvent.VK_BACK_SPACE) {
            return;
        }

        // Solo permitir dígitos
        if (!Character.isDigit(c)) {
            evt.consume();
            return;
        }

        // Insertar '/' automáticamente después del día y del mes
        if ((text.length() == 2 || text.length() == 5)) {
            txt.setText(text + "/");
        }

        // Limitar a 10 caracteres (dd/MM/yyyy)
        if (text.length() >= 10) {
            evt.consume();
        }
    }

    // Validar si la fecha es real
    public boolean validarFechaReal(JTextField txt, String mensaje) {
        String fechaTexto = txt.getText().trim();

        if (fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, mensaje);
            SwingUtilities.invokeLater(() -> txt.requestFocusInWindow());
            return false;
        }

        // Usamos formato estricto dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // NO permitir fechas inválidas (ej: 32/15/2025)

        try {
            sdf.parse(fechaTexto);
            return true; // fecha válida
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La fecha ingresada no es válida");
            SwingUtilities.invokeLater(() -> txt.requestFocusInWindow());
            return false;
        }
    }

   



    public static class validacion {

        //validar solo numeros
        public void validarNumeros(KeyEvent evt) {
            char c = evt.getKeyChar();
            // Permitir borrar con backspace
            if (c == KeyEvent.VK_BACK_SPACE) {
                return;
            }
            // Si no es número, bloquear
            if (!Character.isDigit(c)) {
                evt.consume();
            }
        }
        // Método general que valida si un carácter es permitido

        public void validarLetras(KeyEvent evt, JTextField txt) {
            char c = evt.getKeyChar();
            String text = txt.getText();
            // Permitir letras
            if (Character.isLetter(c)) {
                return;
            }
            // Permitir espacio si no es el primero ni repetido
            if (c == ' ' && !text.isEmpty() && text.charAt(text.length() - 1) != ' ') {
                return;
            }
            // Bloquear lo demás
            evt.consume();
        }

        //Metodo para verificar si los campos estan vacios
        public boolean validarCampoVacio(JTextField campo, String mensaje) {
            if (campo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, mensaje);
                campo.requestFocus();
                return false;
            }
            return true;
        }

        // Método genérico para validar ComboBox
        public boolean validarComboVacio(JComboBox<?> combo, String mensaje) {
            if (combo.getSelectedIndex() <= 0) {
                // posición 0 = "Seleccione..."
                JOptionPane.showMessageDialog(null, mensaje);
                combo.requestFocus();
                return false;
            }
            return true;
        }

        // Valida que se haya seleccionado un radio button
        public boolean validarRadio(ButtonGroup grupo, String mensaje) {
            if (grupo.getSelection() == null) {
                // Ningún botón seleccionado
                JOptionPane.showMessageDialog(null, mensaje);
                return false;
            }
            return true;
        }

        // Validar formato de fecha dd/MM/yyyy
        public void validarFecha(KeyEvent evt, JTextField txt) {
            char c = evt.getKeyChar();
            String text = txt.getText();
            // Permitir borrar
            if (c == KeyEvent.VK_BACK_SPACE) {
                return;
            }
            // Solo permitir dígitos
            if (!Character.isDigit(c)) {
                evt.consume();
                return;
            }
            // Insertar '/' automáticamente después del día y del mes
            if (text.length() == 2 || text.length() == 5) {
                txt.setText(text + "/");
            }
            // Limitar a 10 caracteres (dd/MM/yyyy)
            if (text.length() >= 10) {
                evt.consume();
            }
        }

        // Validar si la fecha es real
        public boolean validarFechaReal(JTextField txt, String mensaje) {
            String fechaTexto = txt.getText().trim();
            if (fechaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(null, mensaje);
                SwingUtilities.invokeLater(() -> txt.requestFocusInWindow());
                return false;
            }
            // Usamos formato estricto dd/MM/yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // NO permitir fechas inválidas (ej: 32/15/2025)
            try {
                sdf.parse(fechaTexto);
                return true; // fecha válida
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "La fecha ingresada no es v\u00e1lida");
                SwingUtilities.invokeLater(() -> txt.requestFocusInWindow());
                return false;
            }
        }

       

        // Dentro de tu clase fmrMascota
        public void limpiarCampos(JTextField txtNombre, JComboBox cmbEspecies, JTextField txtEdad, ButtonGroup btngSexo, JTextField txtBuscar, JTextField ftxtFechaRegistro) {
            txtNombre.setText("");
            cmbEspecies.setSelectedIndex(0);
            txtEdad.setText("");
            btngSexo.clearSelection();
            txtBuscar.setText("");
        }
    }
}
