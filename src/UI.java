import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;



public class UI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextArea txtrTextMessage;
	private String EncryptKey, imagePath;
	private JTextArea log;
	private JScrollPane scrollPane;
	private JButton btnBeginEncode;
	private JLabel imgLabel;
	private JRadioButton rdbtnEncode;
	private JRadioButton rdbtnDecode;
	
	//vars
	private ByteArrayInputStream byteStreamIn;
	private byte[] encodedImage;
	private BufferedImage newImage;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setFont(new Font("Vrinda", Font.BOLD, 12));
		setTitle("Image Encoder Decoder - Archit Enterprises");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1016, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ButtonGroup group = new ButtonGroup();
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 162, 191, 33);
		contentPane.add(panel);
		
		rdbtnEncode = new JRadioButton("Encode");
		rdbtnEncode.setFont(new Font("Vrinda", Font.BOLD, 12));
		panel.add(rdbtnEncode);
		group.add(rdbtnEncode);
		rdbtnEncode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrTextMessage.setText("Encode");
				log.append("Encode has been selected \n");
				txtrTextMessage.setEditable(true);
				btnBeginEncode.setText("Begin Encode");
				
			}
		});
		
		rdbtnDecode = new JRadioButton("Decode");
		rdbtnDecode.setFont(new Font("Vrinda", Font.BOLD, 12));
		panel.add(rdbtnDecode);
		group.add(rdbtnDecode);
		rdbtnDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrTextMessage.setText("Decode");
				log.append("Decode has been selected \n");
				txtrTextMessage.setEditable(false);
				btnBeginEncode.setText("Begin Decode");
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 83, 568, 68);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblImage = new JLabel("Image:");
		lblImage.setFont(new Font("Vrinda", Font.BOLD, 12));
		lblImage.setBounds(12, 9, 34, 14);
		panel_1.add(lblImage);
		
		textField = new JTextField();
		textField.setFont(new Font("Vrinda", Font.PLAIN, 11));
		textField.setBounds(56, 6, 386, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setFont(new Font("Vrinda", Font.BOLD, 12));
		btnBrowse.setBounds(452, 5, 90, 23);
		panel_1.add(btnBrowse);
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.append("Currently selecting image...\n");
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				File selectedPfile = chooser.getSelectedFile();
				if(returnVal == JFileChooser.APPROVE_OPTION){
				String temp = selectedPfile.getAbsolutePath();
				imagePath = temp;
				String extension = temp.substring(temp.lastIndexOf('.') + 1);
				if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg")){
				textField.setText(selectedPfile.getAbsolutePath());
				log.append("Image selected file path set as: \n \t" + temp + "\n");
				//image Manipulation
				ImageIcon iconTemp = new ImageIcon(temp);
				Image img = iconTemp.getImage();
				BufferedImage buffer;
				try {
					buffer = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
					Graphics gImage = buffer.createGraphics();
					gImage.drawImage(img, 0, 0, 412, 369, null);
					ImageIcon iconTemp2 = new ImageIcon(buffer);
					imgLabel.setIcon(iconTemp2);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error: Image does not exist");
					textField.setText("");
					imagePath = "";
				}
				}else{
				log.append("SELECTED IMAGE IS NOT A JPEG/JPG FORMAT. Please select an approriatly formatted image.\n");	
				}
				}else{
					log.append("...image selection was aborted by user\n");
			 }
			}
		});
		
		
		JLabel lblKey = new JLabel("KEY:");
		lblKey.setFont(new Font("Vrinda", Font.BOLD, 12));
		lblKey.setBounds(12, 38, 49, 14);
		panel_1.add(lblKey);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Vrinda", Font.PLAIN, 11));
		textField_1.setBounds(56, 35, 386, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnSetKey = new JButton("Set Key");
		btnSetKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField_1.getText() == null || textField_1.getText().length() <= 0){
					log.append("KEY NOT STORED; Please enter a key that is at least 1 character long \n"); 
				}
				else{
					EncryptKey = textField_1.getText();
					textField_1.setText("");
					log.append("KEY SUCCESSFULLY STORED. Key stored as: " + EncryptKey + "\n");
				}
			}
		});
		btnSetKey.setFont(new Font("Vrinda", Font.BOLD, 12));
		btnSetKey.setBounds(450, 34, 90, 23);
		panel_1.add(btnSetKey);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(211, 162, 367, 218);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setFont(new Font("Vrinda", Font.BOLD, 12));
		lblMessage.setBounds(10, 10, 50, 14);
		panel_2.add(lblMessage);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(70, 10, 287, 197);
		panel_2.add(scrollPane_1);
		
		txtrTextMessage = new JTextArea();
		scrollPane_1.setViewportView(txtrTextMessage);
		txtrTextMessage.setText("Text Message");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 391, 990, 160);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblLog = new JLabel("Log:");
		lblLog.setFont(new Font("Vrinda", Font.BOLD, 12));
		lblLog.setBounds(10, 15, 21, 14);
		panel_3.add(lblLog);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 11, 939, 86);
		panel_3.add(scrollPane);
		
		log= new JTextArea();
		scrollPane.setViewportView(log);
		DefaultCaret caret = (DefaultCaret)log.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		log.setEditable(false);
		
		btnBeginEncode = new JButton("Begin Encode");
		btnBeginEncode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnEncode.isEnabled()){
					BufferedImage img = null;
					try {
					    img = ImageIO.read(new File(imagePath));
					    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
					    ImageIO.write(img, imagePath.substring(imagePath.lastIndexOf('.') + 1), byteStream);
					    byteStream.flush();
					    byte[] imgBytes = byteStream.toByteArray();
					    byteStream.close();
					   byte[] stringBytes = txtrTextMessage.getText().getBytes();
					   encodedImage = encode(imgBytes, stringBytes);
					   //try {
						    // retrieve image
						  byteStreamIn = new ByteArrayInputStream(encodedImage);
						  newImage = ImageIO.read(byteStreamIn);
				 
						ImageIO.write(newImage, "jpg", new File(
									"c:/ArchitEnterprise.jpg"));
						//} catch (IOException e) {
						//	JOptionPane.showMessageDialog(null, "Error: cannot create new image in current directory");
						//}
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error: Image not found or cannot create new image");
					}
				}
				if(rdbtnDecode.isEnabled()){
					BufferedImage img = null;
					try {
					    img = ImageIO.read(new File(imagePath));
					    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
					    ImageIO.write(img, imagePath.substring(imagePath.lastIndexOf('.') + 1), byteStream);
					    byteStream.flush();
					    byte[] imgBytes = byteStream.toByteArray();
					    byteStream.close();
					   byte[] decodedImage = decode(imgBytes);
					   String decodedMessage = decodedImage.toString();
					   txtrTextMessage.setText(decodedMessage);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error: Image not found");
					}
				}
			}
		});
		btnBeginEncode.setFont(new Font("Vrinda", Font.BOLD, 15));
		btnBeginEncode.setBounds(416, 108, 188, 41);
		panel_3.add(btnBeginEncode);
		
		ImageIcon icon = new ImageIcon("black.jpg");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(588, 11, 412, 369);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		imgLabel = new JLabel();
		imgLabel.setBounds(0, 0, 412, 369);
		imgLabel.setIcon(icon);
		panel_4.add(imgLabel);
		
		ImageIcon logo = new ImageIcon("Archit_Logo.png");
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 11, 568, 70);
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblA = new JLabel();
		lblA.setBounds(0, 0, 568, 70);
		lblA.setIcon(logo);
		panel_5.add(lblA);
	
	}
	public byte[] encode(byte[] A, byte[] B){
		byte[] bBits = allBytesToBits(B);
		return encodeToByteArray(A, bBits);
	}
	
	public byte[] decode(byte[] A){
		byte[] encodedBits = decodeToByteArray(A);
		return encodedBits;
	}

	
	public byte[] allBytesToBits(byte[] A) {
		byte[] bigArray = new byte[A.length * 8];
		for (int i = 0; i < A.length; i++) {
			byte[] aTemp = byteToBits(A[i]);
			for (int z = 0; z < aTemp.length; z++) {
				bigArray[(i*8)+z] = aTemp[z];
			}
		}
		return bigArray;
	}

	public byte[] byteToBits(byte A) {
		byte[] bits = new byte[8];
		for (int i = 7; i >= 0; i--) {
			bits[i] = (byte) ((A >> i) & 1);
		}
		return bits;
	}
	
	public byte[] allBitsToBytes(byte[] A){
		byte[] bigArray = new byte[A.length/8];
		int bigIndex = 0;
		byte[] temp = new byte[8];
		for(int i = 0; i < A.length; i++){
			int modIndex = i % 8;
			temp[modIndex] = A[i];
			if(modIndex == 7){
				bigArray[bigIndex] = bitsToByte(temp);
				bigIndex++;
			}
		}
		return bigArray;
	}
	public byte bitsToByte(byte[] A){
		byte one = 0x00;
		for (int i = 7; i >= 0; i--) {
			if (A[i] == 0x01) {
				one = (byte) (one | (1 << i));
			} else {
				one = (byte) (one & ~(1 << i));
			}
		}
		return one;
		
	}
	

	public byte appendBitToByte(byte A, byte B) {
		byte mask = 0x01;
		byte val = (byte) (mask & B);
		byte ret;
		if (val == 0x01) {
			ret = (byte) (A | (1 << 0));
		} else {
			ret = (byte) (A & ~(1 << 0));
		}
		return ret;
	}

	public byte retractBitFromByte(byte A) {
		byte ret = (byte) ((A >> 0) & 1);
		return ret;
	}
	
	public byte[] encodeToByteArray(byte[] A, byte[] B){	
		byte[] finalBytes = A;
		BigInteger size = BigInteger.valueOf(B.length); 	
		byte[] numBytes = size.toByteArray();
		int numLen = numBytes.length;
		byte[] numAsBits = new byte[24];
		byte[] tempBytes = allBytesToBits(numBytes);
		if (numLen > 3){
			//error message
			numLen = 3;	
		}
		int bitCounter = 0;
		for(int i = tempBytes.length-((numLen*8)); i < tempBytes.length; i++){
			numAsBits[bitCounter] = tempBytes[i];
			bitCounter++;
		}
		int numBitSize = numAsBits.length;
		for(int i = 0; i < numBitSize; i++){
			if(i < A.length){
				finalBytes[i] = appendBitToByte(A[i], numAsBits[i]);
			}else{
			//throw an error
			}
		}
		for(int i = 0; i < B.length; i++){
			if((numBitSize + i) < A.length){
				finalBytes[numBitSize+ i] = appendBitToByte(A[numBitSize + i], B[i]);
			}else{
				//throw an error
			}
		}
		
	return finalBytes;
	}
	
	public byte[] decodeToByteArray(byte[] A){	
		byte[] cryptSize = new byte[24];
		for (int i =0; i < cryptSize.length; i++){
			if(i < A.length){
				cryptSize[i] = retractBitFromByte(A[i]);
			}
		}
		byte[] cryptSizeBytes = allBitsToBytes(cryptSize);
		byte[] crytpReverse = {cryptSizeBytes[2], cryptSizeBytes[1], cryptSizeBytes[0]};
		int size = new BigInteger(crytpReverse ).intValue();

		byte[] tempBytes = new byte[size];
		for(int i = 24; i < 24 + size; i++){
			tempBytes[i-24] = retractBitFromByte(A[i]);
		}

		byte[] finalBytes = allBitsToBytes(tempBytes);
		return finalBytes;
	}
}
