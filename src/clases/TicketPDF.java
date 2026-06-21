package clases;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle; // 👈 NUEVO IMPORT PARA EL TAMAÑO
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import clasesDAO.OrdenDAO;

public class TicketPDF {

    private static final String CARPETA_TICKETS = "Tickets/";

    public TicketPDF() {
        File carpeta = new File(CARPETA_TICKETS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    public void generarTicket(int idOrden) {
        String rutaArchivo = CARPETA_TICKETS + "Ticket_" + idOrden + ".pdf";
        File archivoExistente = new File(rutaArchivo);

        if (archivoExistente.exists()) {
            archivoExistente.delete();
            System.out.println("Ticket viejo eliminado. Creando actualización...");
        }

        // =======================================================
        // 🔥 AQUÍ ESTÁ LA MAGIA DEL DISEÑO TIPO "IMPRESORA TÉRMICA"
        // Ancho: 220 puntos (aprox 78mm), Alto: 500 puntos
        Rectangle tamanoTicket = new Rectangle(220, 500);
        // Instanciamos con márgenes súper pequeñitos (10 puntos a los lados)
        Document documento = new Document(tamanoTicket, 10, 10, 15, 15);
        // =======================================================

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // 1. Fuentes más pequeñas para que todo quepa en el ticket
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            Font fontNegrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 7);

            // 2. Encabezado del Ticket
            Paragraph titulo = new Paragraph("TAQUERÍA EL METAPANECO", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // Separador más corto adaptado al nuevo ancho
            Paragraph separador = new Paragraph("--------------------------------------------------", fontNormal);
            separador.setAlignment(Element.ALIGN_CENTER);
            documento.add(separador);

            Paragraph infoOrden = new Paragraph("Orden N°: " + idOrden, fontSubtitulo);
            infoOrden.setAlignment(Element.ALIGN_CENTER);
            documento.add(infoOrden);

            documento.add(separador);
            documento.add(new Paragraph(" ", fontNormal)); // Espacio

            // 3. Obtener los detalles reales
            OrdenDAO dao = new OrdenDAO();
            List<DetalleOrden> detalles = dao.cargarDetalle(idOrden);

            // 4. Crear la Tabla (con proporciones ajustadas al ticket)
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1.2f, 4f, 1.8f, 1.8f});

            // Función rápida para crear celdas SIN bordes (así parece más un ticket real)
            agregarCelda(tabla, "CANT", fontNegrita);
            agregarCelda(tabla, "DESCRIPCIÓN", fontNegrita);
            agregarCelda(tabla, "PRECIO", fontNegrita);
            agregarCelda(tabla, "SUBTOT", fontNegrita);

            double totalPagar = 0.0;

            // Llenar la tabla
            for (DetalleOrden d : detalles) {
                agregarCelda(tabla, String.valueOf(d.getCantidad()), fontNormal);

                String nombreItem = d.getNombre() != null ? d.getNombre() : "Item";
                agregarCelda(tabla, nombreItem, fontNormal);

                agregarCelda(tabla, String.format("$%.2f", d.getPrecioUnitario()), fontNormal);
                agregarCelda(tabla, String.format("$%.2f", d.getSubtotal()), fontNormal);

                totalPagar += d.getSubtotal();
            }

            documento.add(tabla);
            documento.add(new Paragraph(" ", fontNormal));

            // 5. Total Final
            Paragraph pTotal = new Paragraph(String.format("TOTAL: $%.2f", totalPagar), fontSubtitulo);
            pTotal.setAlignment(Element.ALIGN_RIGHT);
            documento.add(pTotal);

            // 6. Pie de página
            documento.add(new Paragraph(" ", fontNormal));
            documento.add(separador);
            Paragraph footer = new Paragraph("¡Gracias por su preferencia!", fontSubtitulo);
            footer.setAlignment(Element.ALIGN_CENTER);
            documento.add(footer);

            // Cerrar y guardar
            documento.close();
            System.out.println("Ticket formato recibo guardado en: " + rutaArchivo);

        } catch (Exception e) {
            System.out.println("Error al generar PDF: " + e.getMessage());
        }
    }

    // Método auxiliar para evitar repetir tanto código al quitarle los bordes a la tabla
    private void agregarCelda(PdfPTable tabla, String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Paragraph(texto, fuente));
        celda.setBorder(0); // 👈 ¡Esto le quita el borde cuadrado para que se vea como recibo!
        tabla.addCell(celda);
    }
}
