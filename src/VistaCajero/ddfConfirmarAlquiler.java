/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VistaCajero;

import Conexion.ConexionSQL;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jamt_
 */
public class ddfConfirmarAlquiler extends javax.swing.JInternalFrame {

    /**
     * Creates new form ddfConfirmarAlquiler
     */
    String codPP, fecha, fechaM, horaM;
    public static int idPPp;
    
    
    public ddfConfirmarAlquiler() {
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        
    }
    
    String codigoPedidoPelicula(){
        int j;
        int cont=1;
        String num="";
        String c="";
        String SQL="select max(codigoVA) from VoucherAlquiler";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(SQL);
            if(rs.next())
            {              
                 c=rs.getString(1);
            }
                    
            if(c==null){
                codPP = "CP0001";
            }
            else{
                char r1=c.charAt(2);
                char r2=c.charAt(3);
                char r3=c.charAt(4);
                char r4=c.charAt(5);
                String r="";
                r=""+r1+r2+r3+r4;
            
                 j=Integer.parseInt(r);
                 GenerarCodigos2 gen= new GenerarCodigos2();
                 gen.generar(j);
                 codPP = "CP"+gen.serie();            
            }            
         
        } catch (SQLException ex) {
            System.out.println("Error codigo PEDIDO PELICULA -- " + ex);
        }
        return codPP;
    }
    
    void ingresarPedidoPelicula(){
        codPP = codigoPedidoPelicula();
        String sql="INSERT INTO VoucherAlquiler (fechaVA,estadoVA,importeTotal,codigoVA,idCliente) VALUES (?,?,?,?,?)";
            try {
                PreparedStatement pst  = cn.prepareStatement(sql);
                
                Date fechaActual = new Date();
                int anioactual = fechaActual.getYear()+1900;
                int mesactual = fechaActual.getMonth()+1;
                int diaactual = fechaActual.getDate();

                int hora = fechaActual.getHours();
                int minuto = fechaActual.getMinutes();
                int segundo = fechaActual.getSeconds();

                fecha = anioactual+"-"+mesactual+"-"+diaactual+" "+hora+":"+minuto+":"+segundo;
                fechaM = diaactual+"/"+mesactual+"/"+anioactual;
                horaM = hora+":"+minuto;
                
                pst.setString(1, fecha);
                pst.setInt(2, 1);
                pst.setString(3, ""+ddMenuAlquierr.precioTotal);
                pst.setString(4, codPP);
                pst.setString(5, ""+bbPrincipal.idCliente);
                

                pst.executeUpdate();
                
            } catch (SQLException ex) {
                System.out.println("Error al ingresar datos pedido Pelicula: " + ex);
            }
    }

    int obtenerIdPP(){
        String mostrar="SELECT * FROM VoucherAlquiler WHERE idVA = (SELECT MAX(idVA) FROM VoucherAlquiler WHERE estadoVA="+1+")";
        
        try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(mostrar);
                if(rs.next())
                {
                    idPPp = rs.getInt("idVA");
                }
                
                System.out.println("idPP >> " + idPPp);
              
        } catch (SQLException ex) {
            System.out.println("Error en obtener id pp: " + ex);
        }
        return idPPp;
    }
    
    void ingresarVoucherPedido(){
        for(int i=0;i<ddeAñadirAlquiler.tablaPelicula.getRowCount();i++){
            String InsertarSQL="INSERT INTO DetalleVoucher(idVA,idPelicula,nombrePelicula,cantidadPelicula,precioPelicula) VALUES (?,?,?,?,?)";
            int idVp=obtenerIdPP();
            int idPeli=Integer.parseInt(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 0).toString());
            String nombrePeli=ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 1).toString();
            int cantPeli=Integer.parseInt(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 3).toString());
            double precioPeli=Double.parseDouble(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 2).toString());

            try {
                PreparedStatement pst = cn.prepareStatement(InsertarSQL);
                pst.setString(1,""+idVp);
                pst.setString(2,""+idPeli);
                pst.setString(3,nombrePeli);
                pst.setString(4,""+cantPeli);
                pst.setString(5,""+precioPeli);

                pst.executeUpdate();


            } catch (SQLException ex) {
                System.out.println("Error VOUCHER PEDIDO: " + ex);
            }
        }
    }
    
    double obtenerPrecioTarjeta(){
        double SaldoTarjeta = 0;
        String mostrar="SELECT * FROM Tarjeta WHERE idTarjeta="+aaLogearTarjeta.idTarjeta;
        
        try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(mostrar);
                if(rs.next())
                {
                    SaldoTarjeta = rs.getDouble("saldoTarjeta");
                }
                
                System.out.println("SaldoTarjeta para comparar >> >> " + SaldoTarjeta);
              
        } catch (SQLException ex) {
            System.out.println("Error en obtener id pp: " + ex);
        }
        return SaldoTarjeta;
    }
    
    void descontarSaldo(){
        double saldoFinal;
        double saldoTarjeta = obtenerPrecioTarjeta();
        double saldoADescontar = ddMenuAlquierr.precioTotal;
        
        saldoFinal = saldoTarjeta - saldoADescontar;
        
        String modi="UPDATE Tarjeta SET saldoTarjeta="+saldoFinal+", estadoTarjeta="+3+" WHERE idTarjeta="+aaLogearTarjeta.idTarjeta;
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al descontar el saldo de la tarjeta: " + e);
        }
    }
    
    public void generarPDF(String codigo) throws FileNotFoundException, DocumentException{
        FileOutputStream archivo = new FileOutputStream(codigo+".pdf");
        Document documento = new Document();
        PdfWriter.getInstance(documento, archivo);
        documento.open();
        
        Paragraph parrafo = new Paragraph("Voucher de Alquiler");
        parrafo.setAlignment(1);
        documento.add(parrafo);
        
        documento.add(new Paragraph("\nCodigo de Voucher: " + codPP));
        documento.add(new Paragraph("Fecha: " + fechaM + "          Hora: " + horaM));
        documento.add(new Paragraph("\nCliente: " + aaLogearTarjeta.nombre + " " + aaLogearTarjeta.apellidoP + " " + aaLogearTarjeta.apellidoM));
        documento.add(new Paragraph("DNI:       " + aaLogearTarjeta.dniii));
        documento.add(new Paragraph("\nPeliculas Alquiladas"));
        for(int i=0;i<ddeAñadirAlquiler.tablaPelicula.getRowCount();i++){
            int idPeli=Integer.parseInt(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 0).toString());
            String nombrePeli=ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 1).toString();
            int cantPeli=Integer.parseInt(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 3).toString());
            double precioPeli=Double.parseDouble(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 2).toString());

            documento.add(new Paragraph((i+1)+". " + nombrePeli + "      cant: " + cantPeli + "      precio: " + precioPeli));
        }
        documento.add(new Paragraph("\nPrecio Total: " + ddMenuAlquierr.precioTotal));
        documento.close();
    }
    
    public static void pdf(String codigo) throws FileNotFoundException, DocumentException{
        FileOutputStream archivo = new FileOutputStream(codigo+".pdf");
        Document documento = new Document();
        PdfWriter.getInstance(documento, archivo);
        documento.open();
                
        try {
            Image logo = Image.getInstance("src/Imagenes/logo.png");
        
            Font negrita = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD,BaseColor.WHITE);
            Font negrita3 = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD,BaseColor.BLACK);
            Font negrita2 = new Font(Font.FontFamily.HELVETICA,18,Font.BOLD,BaseColor.BLACK);
            Font negrita4 = new Font(Font.FontFamily.HELVETICA,14,Font.BOLD,BaseColor.BLACK);

            Paragraph parrafo = new Paragraph("OjxPeru",negrita4);
            PdfPTable enca = new PdfPTable(4);
            enca.setWidthPercentage(100);
            enca.getDefaultCell().setBorder(0);
            float[] clumn = new float[]{20f, 30f, 70f, 40f};
            enca.setWidths(clumn);
            enca.setHorizontalAlignment(Element.ALIGN_LEFT);

            enca.addCell("");
            enca.addCell("");
            enca.addCell(parrafo);enca.addCell("");
            documento.add(enca);
            
            Date fechaActual = new Date();
                int anioactual = fechaActual.getYear()+1900;
                int mesactual = fechaActual.getMonth()+1;
                int diaactual = fechaActual.getDate();

                int hora = fechaActual.getHours();
                int minuto = fechaActual.getMinutes();
                int segundo = fechaActual.getSeconds();
                
                String fechita=diaactual+"/"+mesactual+"/"+anioactual;
                String horitas=hora+":"+minuto;
            
            Paragraph fechaaa = new Paragraph();
            fechaaa.add("Codigo:    " + codigo + "\n Fecha:     " + fechita + "\n   Hora:     " + horitas);
            PdfPTable encabezado = new PdfPTable(4);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            float[] clumnasEncabezado = new float[]{20f, 30f, 70f, 40f};
            encabezado.setWidths(clumnasEncabezado);
            encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            encabezado.addCell(logo);
            encabezado.addCell("");
            
            String ruc = "20602636871";
            String tel = "(061) 263999";
            String dir = "Pucallpa";
            
            encabezado.addCell("Ruc: "+ruc+"\nTelefono: "+tel+"\nDirección: "+dir);
            encabezado.addCell(fechaaa);
            documento.add(encabezado);
            
            Paragraph titulo = new Paragraph("Voucher de Alquiler",negrita2);
            titulo.setAlignment(1);
            documento.add(titulo);
            
            //datos del cliente
            documento.add(new Paragraph("\nCliente:  " + aaLogearTarjeta.nombre + " " + aaLogearTarjeta.apellidoP + " " + aaLogearTarjeta.apellidoM));
            documento.add(new Paragraph("DNI:       " + aaLogearTarjeta.dniii));
            
            //peliculas
            documento.add(new Paragraph("\nPeliculas Alquiladas",negrita3));
            documento.add(new Paragraph("\n"));
            PdfPTable tablaP = new PdfPTable(4);
            tablaP.setWidthPercentage(100);
            tablaP.getDefaultCell().setBorder(0);
            float[] clumnasP = new float[]{10f, 50f, 15f, 20f};
            tablaP.setWidths(clumnasP);
            tablaP.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell p1 = new  PdfPCell(new Phrase("Nro",negrita));
            PdfPCell p2 = new  PdfPCell(new Phrase("Pelicula",negrita));
            PdfPCell p3 = new  PdfPCell(new Phrase("Cantidad",negrita));
            PdfPCell p4 = new  PdfPCell(new Phrase("Precio",negrita));
            p1.setBorder(0);
            p2.setBorder(0);
            p3.setBorder(0);
            p4.setBorder(0);
            p1.setBackgroundColor(BaseColor.DARK_GRAY);
            p2.setBackgroundColor(BaseColor.DARK_GRAY);
            p3.setBackgroundColor(BaseColor.DARK_GRAY);
            p4.setBackgroundColor(BaseColor.DARK_GRAY);
            tablaP.addCell(p1);
            tablaP.addCell(p2);
            tablaP.addCell(p3);
            tablaP.addCell(p4);
            for(int i=0;i<ddeAñadirAlquiler.tablaPelicula.getRowCount();i++){
                int idPeli=Integer.parseInt(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 0).toString());
                String nombrePeli = ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 1).toString();
                String cantPeli = ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 3).toString();
                String precioPeli = ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 2).toString();
                int nroo= i+1;
                String nro = "" + nroo;
                
                tablaP.addCell(nro);
                tablaP.addCell(nombrePeli);
                tablaP.addCell(cantPeli);
                tablaP.addCell(precioPeli);
            }
            
            documento.add(tablaP);
            
            documento.add(new Paragraph("\nImporte Total: " + ddMenuAlquierr.precioTotal, negrita3));
            
        } catch (BadElementException ex) {
            Logger.getLogger(ddfConfirmarAlquiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ddfConfirmarAlquiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        documento.close();
        
    }
    
    public void abrirPDF(String codigo){
        try {
            File path = new File(codigo + ".pdf");
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            System.out.println("Error al abrir el pdf " + e);
        }
    }
    
    void descontarstock(int codP,int can){
        int des = can;
        int cap = 0;
        int desfinal;
        String consul="SELECT * FROM Pelicula WHERE idPelicula="+codP;
        try {
            Statement st= cn.createStatement();
            ResultSet rs= st.executeQuery(consul);
            while(rs.next())
            {
                cap= rs.getInt("cantidadP");
            }
             
        } catch (Exception e) {
        }
        desfinal=cap-des;
        String modi="UPDATE Pelicula SET cantidadP="+desfinal+" WHERE idPelicula = "+codP;
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    void confirmarTarjeta(String contra){
        double saldoTarjeta = obtenerPrecioTarjeta();
        
        String mostrar = "select * from Cliente c INNER JOIN Tarjeta t ON c.idTarjeta = t.idTarjeta WHERE t.contrasenia = '"+contra+"'" + " AND t.idTarjeta = " + aaLogearTarjeta.idTarjeta;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(mostrar);
            int cont=0;
            
            if(rs.next()){
                cont++;
            }
            
            if(cont==1){
                if(saldoTarjeta>=ddMenuAlquierr.precioTotal){
                    ingresarPedidoPelicula();
                    ingresarVoucherPedido();
                    descontarSaldo();

                    int capcod,capcan;
                    for(int i=0;i<ddeAñadirAlquiler.tablaPelicula.getRowCount();i++)
                    {
                        capcod=Integer.parseInt(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 0).toString());
                        capcan=Integer.parseInt(ddeAñadirAlquiler.tablaPelicula.getValueAt(i, 3).toString());
                        descontarstock(capcod, capcan);
                    }
                    try {
                        pdf(codPP);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ddfConfirmarAlquiler.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ddfConfirmarAlquiler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    JOptionPane.showMessageDialog(null, "Tramite de Alquiler con exito","Mensaje",1);
                
                    abrirPDF(codPP);
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Usted no cuenta con saldo suficiente para realizar el alquiler");
                }
                
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
            }
            
        } catch (SQLException ex) {
            System.out.println("Error Logear Cliente -- " + ex);
        }   
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtContraTarjeta = new javax.swing.JPasswordField();
        btnConfirmar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Confirmar Alquiler");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel1.setText("Contraseña");

        btnConfirmar.setBackground(new java.awt.Color(51, 255, 51));
        btnConfirmar.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(txtContraTarjeta)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContraTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfirmar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(206, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
        String contra = new String(txtContraTarjeta.getPassword());
        
        confirmarTarjeta(contra);
    }//GEN-LAST:event_btnConfirmarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtContraTarjeta;
    // End of variables declaration//GEN-END:variables
Conexion.ConexionSQL cc = new ConexionSQL();
Connection cn= ConexionSQL.conexionn();
}
