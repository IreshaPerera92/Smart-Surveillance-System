/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package humantracking.gui;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import humantracking.dbadapter.CameraDBAdapter;
import humantracking.model.CameraDetail;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG;

/**
 *
 * @author Darshana Priyasad
 */
public class LiveTest extends javax.swing.JFrame {

    static Mat imag = null;
    private DefaultTableModel model;
    private CascadeClassifier classifier;
    private opencv_core.CvMemStorage storage;
    private BackgroundSubtractor backgroundSubtractor;

    final HOGDescriptor hog;
    final MatOfFloat descriptors;
    private CascadeClassifier rightEarDetector;
    private CascadeClassifier leftEarDetector;
    private CascadeClassifier eyeDetector;
    private CascadeClassifier faceDetector;
    private CascadeClassifier upperbodyDetector;
    private CascadeClassifier lowerbodyDetector;

    final MatOfRect foundLocations;
    final MatOfDouble foundWeights;
    final Size winStride;
    final Size padding;
    final Point rectPoint1;
    final Point rectPoint2;
    final Point fontPoint;
    int frames = 0;
    int framesWithPeople = 0;
    final Scalar rectColor;
    final Scalar fontColor;

    /**
     * Creates new form LiveTest
     */
    public LiveTest() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Live Feed");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        hog = new HOGDescriptor();
        descriptors = HOGDescriptor.getDefaultPeopleDetector();
        hog.setSVMDetector(descriptors);

        foundLocations = new MatOfRect();
        foundWeights = new MatOfDouble();
        winStride = new Size(8, 8);
        padding = new Size(32, 32);
        rectPoint1 = new Point();
        rectPoint2 = new Point();
        fontPoint = new Point();

        rectColor = new Scalar(0, 255, 0);
        fontColor = new Scalar(255, 255, 255);

        eyeDetector = new CascadeClassifier();
        eyeDetector.load("./src/humantracking/support/haarcascade_eye.xml");

        rightEarDetector = new CascadeClassifier();
        rightEarDetector.load("./src/humantracking/support/haarcascade_mcs_rightear.xml");

        leftEarDetector = new CascadeClassifier();
        leftEarDetector.load("./src/humantracking/support/haarcascade_mcs_leftear.xml");

        faceDetector = new CascadeClassifier();
        faceDetector.load("./src/humantracking/support/haarcascade_frontalface_alt2.xml");

        upperbodyDetector = new CascadeClassifier();
        upperbodyDetector.load("./src/humantracking/support/haarcascade_upperbody.xml");

        lowerbodyDetector = new CascadeClassifier();
        lowerbodyDetector.load("./src/humantracking/support/haarcascade_lowerbody.xml");

        //faceDetector.load("C:\\Users\\Darshana Priyasad\\Documents\\NetBeansProjects\\HumanTracking\\src\\humantracking\\support\\haarcascade_frontalface_alt.xml");
        //descriptors = HOGDescriptor.getDaimlerPeopleDetector();
        backgroundSubtractor = new BackgroundSubtractorMOG(16, 24, 0.01, 0.8);

        model = (DefaultTableModel) jTable1.getModel();

        try {
            loadCamera();
        } catch (SQLException ex) {
            Logger.getLogger(LiveTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LiveTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        classifier = new CascadeClassifier();
        setIconImage(new ImageIcon("./src/humantracking/images/camera.png").getImage());
        classifier.load("./src/humantracking/support/haarcascade_frontalface_alt.xml");

        storage = opencv_core.CvMemStorage.create();
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/humantracking/images/cooltext149484596066259.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 3, true), "Choose Camera", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(204, 0, 204))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Camera ID", "Location", "Device ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        jScrollPane2.setViewportView(jScrollPane1);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(153, 0, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/humantracking/images/play.png"))); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(29, 29, 29))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 3, true));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel1)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jTable1.isRowSelected(jTable1.getSelectedRow())) {
            int device_id = (Integer) model.getValueAt(jTable1.getSelectedRow(), 2);
            startFeed(device_id);
        } else {
            JOptionPane.showMessageDialog(this, "Select A Camera First", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LiveTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiveTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiveTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiveTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LiveTest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void loadCamera() throws SQLException, ClassNotFoundException {

        CameraDBAdapter cameraDBAdapter = new CameraDBAdapter();
        List<CameraDetail> cameraDetails = cameraDBAdapter.getCameraDetails();
        for (CameraDetail cameraDetail : cameraDetails) {
            String id = cameraDetail.getId();
            String location = cameraDetail.getLocation();
            int device_id = cameraDetail.getDevice_id();
            Object[] data = {id, location, device_id};
            model.addRow(data);
        }
    }

    public void startFeed(int device_id) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        final VideoCapture camera = new VideoCapture(device_id);
        //final VideoCapture camera = new VideoCapture("http://192.168.137.195:8081/?dummy=param.mjpg");
        final Mat frame = new Mat();
        //camera.read(frame);
        new Thread() {

            @Override
            public void run() {

                if (!camera.isOpened()) {
                    System.out.println("Error");
                } else {
                    while (true) {
                        boolean detected = false;

                        if (camera.read(frame)) {
                            //loadClassifier(frame);
                            //int HogDetector = HogDetector(frame);
                            //System.out.println(HogDetector);
                            //Mat substractBackground = substractBackground(frame);
                            //BufferedImage image = MatToBufferedImage(substractBackground);
                            //HogDetector(frame);

                            int faceDetactor = faceDetactor(frame);
                            if (faceDetactor == 0) {
                                int eyeDetactor = eyeDetactor(frame);
                                if (eyeDetactor == 0) {
                                    int upperBodyDetactor = upperBodyDetactor(frame);
                                    if (upperBodyDetactor == 0) {
                                        int lowerBodyDetactor = lowerBodyDetactor(frame);
                                        if (lowerBodyDetactor == 0) {
                                            int HogDetection = HogDetection(frame);
                                            if (HogDetection != 0) {
                                                Core.putText(frame,
                                                        "Human Detected",
                                                        fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                                        2, Core.LINE_AA, false);
                                                detected = true;
                                            }
                                        } else {
                                            Core.putText(frame,
                                                    "Human Detected",
                                                    fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                                    2, Core.LINE_AA, false);
                                            detected = true;
                                        }
                                    } else {
                                        Core.putText(frame,
                                                "Human Detected",
                                                fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                                2, Core.LINE_AA, false);
                                        detected = true;
                                    }
                                } else {
                                    Core.putText(frame,
                                            "Human Detected",
                                            fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                            2, Core.LINE_AA, false);
                                    detected = true;
                                }
                            } else {
                                //System.out.println(",d.m.mdn,dmndmf");
                                Core.putText(frame,
                                        "Human Detected",
                                        fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                        2, Core.LINE_AA, false);
                                detected = true;
                            }
                            //upperBodyDetactor(frame);
                            //if(faceDetactor==0){
                            //}else{
                                /*int count = eyeDetactor(frame);
                             if(count==0){
                             int HogDetections = HogDetection(frame);
                             if(HogDetection==0){
                                        
                             }else{0
                                        
                             }
                             }else{
                                    
                             }
                             */
                            //}
                            BufferedImage image = MatToBufferedImage(frame);

                            if (detected) {

                                Graphics g = image.getGraphics();
                                g.setFont(g.getFont().deriveFont(30f));
                                g.drawString("Human Detectd.", 100, 100);
                                g.dispose();

                            }
                            image = resize(image, 525, 388);
                            ImageIcon icon = new ImageIcon(image);
                            jLabel3.setIcon(icon);
                            jLabel3.repaint();
                            jLabel3.revalidate();
                            jPanel3.repaint();
                            jLabel3.revalidate();
                            
                            
                            detected = false;
                        }
                        
                    }
                }
            }

        }.start();

    }

    public int HogDetection(Mat mat) {
        // CHECKSTYLE:OFF MagicNumber - Magic numbers here for illustration
        hog.detectMultiScale(mat, foundLocations, foundWeights, 0.0,
                winStride, padding, 1.05, 2.0, false);
        // CHECKSTYLE:ON MagicNumber
        if (foundLocations.rows() > 0) {
            framesWithPeople++;
            List<Double> weightList = foundWeights.toList();
            List<Rect> rectList = foundLocations.toList();
            int i = 0;
            for (Rect rect : rectList) {
                rectPoint1.x = rect.x;
                rectPoint1.y = rect.y;
                rectPoint2.x = rect.x + rect.width;
                rectPoint2.y = rect.y + rect.height;
                // Draw rectangle around fond object
                Core.rectangle(mat, rectPoint1, rectPoint2, rectColor, 2);

                fontPoint.x = rect.x;
                // CHECKSTYLE:OFF MagicNumber - Magic numbers here for
                // illustration
                fontPoint.y = rect.y - 4;
                // CHECKSTYLE:ON MagicNumber
                // Print weight
                // CHECKSTYLE:OFF MagicNumber - Magic numbers here for
                // illustration
                Core.putText(mat,
                        String.format("%1.2f", weightList.get(i)),
                        fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                        2, Core.LINE_AA, false);
                // CHECKSTYLE:ON MagicNumber
                i++;
            }

        }

        frames++;
        return foundLocations.rows();
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public BufferedImage MatToBufferedImage(Mat frame) {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

    public Mat substractBackground(Mat mat) {

        Mat mask = new Mat();

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
        backgroundSubtractor.apply(mat, mask, 0.1);
        Mat output = new Mat();
        //mask.copyTo(output, mask);

        mat.copyTo(output, mask);
        //loadClassifier(mat);

        return output;
    }

    public int HogDetector(Mat mat) {

        final MatOfRect foundLocations = new MatOfRect();
        final MatOfDouble foundWeights = new MatOfDouble();
        final Size winStride = new Size(8, 8);
        final Size padding = new Size(32, 32);
        //System.out.println("dsdsdd");
        if (mat != null) {

            hog.detectMultiScale(mat, foundLocations, foundWeights, 0.0, winStride, padding, 1.05, 2.0, false);

            double[] toArray = foundWeights.toArray();
            int good_humans = 0;
            double[] weights_array = foundWeights.toArray();

            for (int i = 0; i < weights_array.length; i++) {
                if (weights_array[i] < 0.5) {
                    good_humans++;
                }
            }

            System.out.println(good_humans);
            return good_humans;
        }
        return 0;

    }

    public int eyeDetactor(Mat image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect eyeDetections = new MatOfRect();
        eyeDetector.detectMultiScale(image, eyeDetections);
        for (Rect rect : eyeDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return eyeDetections.toArray().length;
    }

    public int faceDetactor(Mat image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect eyeDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, eyeDetections);
        for (Rect rect : eyeDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 0, 255),2);
        }
        return eyeDetections.toArray().length;
    }

    public int rightEarDetactor(Mat image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect earDetections = new MatOfRect();
        rightEarDetector.detectMultiScale(image, earDetections);
        for (Rect rect : earDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return earDetections.toArray().length;
    }

    public int leftEarDetactor(Mat image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect earDetections = new MatOfRect();
        leftEarDetector.detectMultiScale(image, earDetections);
        for (Rect rect : earDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return earDetections.toArray().length;
    }

    public int upperBodyDetactor(Mat image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect earDetections = new MatOfRect();
        upperbodyDetector.detectMultiScale(image, earDetections);
        for (Rect rect : earDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return earDetections.toArray().length;
    }

    public int lowerBodyDetactor(Mat image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect earDetections = new MatOfRect();
        lowerbodyDetector.detectMultiScale(image, earDetections);
        for (Rect rect : earDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return earDetections.toArray().length;
    }

}
