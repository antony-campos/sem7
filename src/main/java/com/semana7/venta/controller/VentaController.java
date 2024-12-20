/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semana7.venta.controller;

import com.semana7.venta.model.Venta;
import com.semana7.venta.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author LAB_P03
 */
@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService service;

    public VentaController(VentaService ventaService) {
        this.service = ventaService;
    }

    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas", this.service.listarTodas());
        return "ventas";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("venta", new Venta());
        return "formulario_venta";
    }

    @PostMapping
    public String guardarVenta(@ModelAttribute Venta venta) {
        this.service.guardar(venta);
        return "redirect:/ventas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("venta", this.service.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id)));
        return "formulario_venta";
    }

    @PostMapping("/editar/{id}")
    public String editarVenta(@PathVariable Long id, @ModelAttribute Venta venta) {
        Venta ventaExistente = this.service.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        ventaExistente.setProducto(venta.getProducto());
        ventaExistente.setCantidad(venta.getCantidad());
        ventaExistente.setPrecio_total(venta.getPrecio_total());
        ventaExistente.setFecha_venta(venta.getFecha_venta());
        this.service.guardar(ventaExistente);  // Guarda los cambios de la venta
        return "redirect:/ventas";  // Redirige a la lista de ventas
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        this.service.eliminar(id);  // Asegúrate de tener este método en el servicio
        return "redirect:/ventas";  // Redirige de nuevo a la lista de ventas
    }

    @GetMapping("/reporte/pdf")
    public void generarPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=ventas_reporte.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // Crear la fuente en negrita
        PdfFont boldFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);

        // Crear el título con fuente en negrita y tamaño 18
        Paragraph titulo = new Paragraph("Reporte de Ventas")
                .setFont(boldFont)
                .setFontSize(18);

        // Agregar el título al documento
        document.add(titulo);

        Table table = new Table(4);
        table.addCell("Producto");
        table.addCell("Cantidad");
        table.addCell("Precio");
        table.addCell("Fecha");

        List<Venta> ventas = this.service.listarTodas();
        for (Venta venta : ventas) {
            table.addCell(venta.getProducto());
            table.addCell(String.valueOf(venta.getCantidad()));
            table.addCell(String.valueOf(venta.getPrecio_total()));
            table.addCell(venta.getFecha_venta());
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/reporte/excel")
    public void generarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ventas_reporte.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ventas");

        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {"Producto", "Cantidad", "Precio", "Fecha"};
        for (int i = 0; i < columnHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
        }

        List<Venta> ventas = this.service.listarTodas();
        int rowIndex = 1;
        for (Venta venta : ventas) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(venta.getProducto());
            row.createCell(1).setCellValue(venta.getCantidad());
            row.createCell(2).setCellValue(venta.getPrecio_total());
            row.createCell(3).setCellValue(venta.getFecha_venta());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
