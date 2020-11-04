/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VistaCajero;

import Conexion.ConexionSQL;
import static VistaCajero.aaLogearTarjeta.idTarjeta;
import static VistaCajero.bbPrincipal.saldoTarjeta;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jamt_
 */
public class cdConfirmarRecargaa extends javax.swing.JInternalFrame {

    /**
     * Creates new form cdConfirmarRecargaa
     */
    PlaceHolder holder;
    public static double total = 0;
    String nroOpeacion;
    double saldoIniciall;
    
    
    public cdConfirmarRecargaa() {
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        holder = new PlaceHolder(txtContraTarjeta, "Contraseña");
        
    }
    
    void confirmarTarjeta(String contra){
        String mostrar = "select * from Cliente c INNER JOIN Tarjeta t ON c.idCliente = t.idCliente WHERE t.contrasenia = '"+contra+"'" + " AND t.idTarjeta = " + aaLogearTarjeta.idTarjeta;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(mostrar);
            int cont=0;
            
            if(rs.next()){
                cont++;
            }
            
            total = bbPrincipal.saldoTarjeta + ccMenuRecargaa.monto;
            saldoIniciall = bbPrincipal.saldoTarjeta;
            bbPrincipal.saldoTarjeta = total;
            System.out.println("Monto Total >> " + total);
            
            if(cont==1){
                String sql = "Update Tarjeta set "
                        + "saldoTarjeta="+total+" "
                        + "Where idTarjeta = " + aaLogearTarjeta.idTarjeta;
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.executeUpdate();
                
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "No existe Cliente");
            }
            
            if(total>0){
                bbPrincipal.panelAlquiler.setVisible(true);
                bbPrincipal.panelDevolucion.setVisible(true);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error Logear Cliente -- " + ex);
        }   
    }
    
    String codigosclientes(){
     int j;
        int cont=1;
        String num="";
        String c="";
        String SQL="select max(nroOperacion) from VoucherTarjeta";
        
       // String SQL="select count(*) from factura";
        //String SQL="SELECT MAX(cod_emp) AS cod_emp FROM empleado";
        //String SQL="SELECT @@identity AS ID";
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(SQL);
            if(rs.next())
            {              
                 c=rs.getString(1);
            }
                    
            if(c==null){
                nroOpeacion = "RR0001";
            }
            else{
                char r1=c.charAt(2);
                char r2=c.charAt(3);
                char r3=c.charAt(4);
                char r4=c.charAt(5);
                String r="";
                r=""+r1+r2+r3+r4;
            
                 j=Integer.parseInt(r);
                 GenerarCodigos gen= new GenerarCodigos();
                 gen.generar(j);
                 nroOpeacion = "RR"+gen.serie();            
            }            
         
        } catch (SQLException ex) {
            System.out.println("Error codigo recarga voucher -- " + ex);
        }
        return nroOpeacion;
    }
    
    void ingresarDatosDeRecarga(){
        double importeCargado = ccMenuRecargaa.monto;
        double saldoFinal = total;
        double saldoInicial = saldoIniciall;
        nroOpeacion = codigosclientes();
        int idTarjeta = aaLogearTarjeta.idTarjeta;
        
        String sql = "INSERT INTO VoucherTarjeta (nroOperacion,importeCargado,saldoInicial,saldoFinal,fechaOperacion,idTarjeta) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement pst  = cn.prepareStatement(sql);
                pst.setString(1, nroOpeacion);
                pst.setDouble(2, importeCargado);
                pst.setDouble(3, saldoInicial);
                pst.setDouble(4, saldoFinal);

                Date fechaActual = new Date();
                int anioactual = fechaActual.getYear()+1900;
                int mesactual = fechaActual.getMonth()+1;
                int diaactual = fechaActual.getDate();

                int hora = fechaActual.getHours();
                int minuto = fechaActual.getMinutes();
                int segundo = fechaActual.getSeconds();

                String fecha = anioactual+"-"+mesactual+"-"+diaactual+" "+hora+":"+minuto+":"+segundo;

                pst.setString(5, fecha);
                pst.setInt(6, idTarjeta);  
                
                int n=pst.executeUpdate();
                if(n>0){
                JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
                }

            } catch (SQLException ex) {
                System.out.println("Error al ingresar datos del VoucherTarjeta: " + ex);
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
        setTitle("Confirmar Recarga");

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
        ingresarDatosDeRecarga();
        ccdOtroMontoRecarga.otroMonto = 0;
        ccMenuRecargaa.monto = 0;
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
