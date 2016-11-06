package editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ShopTableEditor extends JFrame{

	private static final long serialVersionUID = -3712934328644904502L;
	private TableModel model;
	private int row;
	
	JTextField nameText = new JTextField(15);
	JTextField priceText = new JTextField(15);
	JTextField descrText = new JTextField(25);
	JTextField imageText = new JTextField(15);
	JTextField categoryText = new JTextField(15);
	JButton ok = new JButton("ОК");
	
	public ShopTableEditor(TableModel model, int row) {
		super("Edit Row № " + row);
		this.model = model;
		this.row = row;
		setSize(350, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		jBInitEdit();
	}
	
	public ShopTableEditor(TableModel model) {
		super("Add new row");
		this.model = model;
		setSize(350, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		jBInitNew();
	}
	
	private void jBInitEdit () {
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				MyConnection.editRow(
						Integer.toString(row+1), 
						nameText.getText(), 
						priceText.getText(), 
						descrText.getText(), 
						imageText.getText(), 
						categoryText.getText());
				model.replaceRow(new String[]{
								Integer.toString(row+1),
								nameText.getText(),
								priceText.getText(),
								descrText.getText(),
								imageText.getText(),
								categoryText.getText()
								}, row);
				model.fireTableDataChanged();
			}
		});
		
		nameText.setText(model.getRowData(row)[1]);
		priceText.setText(model.getRowData(row)[2]);
		descrText.setText(model.getRowData(row)[3]);
		imageText.setText(model.getRowData(row)[4]);
		categoryText.setText(model.getRowData(row)[5]);
				
		jBInit();
	}
	
	private void jBInitNew () {
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				MyConnection.addNewRow(Integer.toString(
						MyConnection.getNextId()), 
						nameText.getText(), 
						priceText.getText(), 
						descrText.getText(), 
						imageText.getText(), 
						categoryText.getText());
				model.addRow(new String[]{
								Integer.toString(MyConnection.getNextId()),
								nameText.getText(),
								priceText.getText(),
								descrText.getText(),
								imageText.getText(),
								categoryText.getText()
								});
				model.fireTableDataChanged();
			}
		});
		
		jBInit();
	}
	
	private void jBInit () {
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(ok);
		
		JPanel firstPanel = new JPanel();
		firstPanel.add(new JLabel("Имя: "));
		firstPanel.add(nameText);
		firstPanel.add(new JLabel("Цена: "));
		firstPanel.add(priceText);
		
		JPanel secondPanel = new JPanel();
		secondPanel.add(new JLabel("Описание: "));
		secondPanel.add(descrText);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(firstPanel);
		mainPanel.add(secondPanel);
		
		JPanel thirdPanel = new JPanel();
		thirdPanel.add(new JLabel("Картинка: "));
		thirdPanel.add(imageText);
		thirdPanel.add(new JLabel("Категория: "));
		thirdPanel.add(categoryText);
		
		add(mainPanel, BorderLayout.PAGE_START);
		add(thirdPanel);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		setVisible(true);
		pack();
	}
}
