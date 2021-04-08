package br.com.locadora.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import br.com.locadora.dao.CarroDAO;
import br.com.locadora.dao.ClienteDAO;
import br.com.locadora.dao.ReservaDAO;
import br.com.locadora.model.Carro;
import br.com.locadora.model.Cliente;
import br.com.locadora.model.Reserva;

public class ReservaApp extends JFrame {
	private JComboBox cbxCliente;
	private JComboBox cbxCarro;
	private JButton cadastrar;
	private JButton cancelar;
	private JButton novo;
	private JTextField txtMarca;
	private JTextField txtDataRetirada;
	private JTextField txtDataDevolucao;
	private Date dataRetirada;
	private Date dataDevolucao;
	
	public ReservaApp() {
		
		setTitle("Reserva de Carros");;
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = buildPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}
	
	private JPanel buildPanel() {
		
		JPanel panel = new JPanel();
		JLabel lblCliente = new JLabel("Cliente:");
		cbxCliente = new JComboBox<>();
		cbxCliente.setPreferredSize(new Dimension(200, 30));
		
		JLabel lblCarro = new JLabel("Carro:");
		cbxCarro = new JComboBox<>();
		cbxCarro.setPreferredSize(new Dimension(100, 30));
		
		JLabel lblMarca = new JLabel("Marca:");
		txtMarca = new JTextField();
		txtMarca.setColumns(10);
		
		JLabel lblDataRet = new JLabel("Data Retirada:");
		txtDataRetirada = new JTextField();
		txtDataRetirada.setColumns(8);
		
		JLabel lblDataDev = new JLabel("Data Devolução:");
		txtDataDevolucao = new JTextField();
		txtDataDevolucao.setColumns(8);
		
		cadastrar = new JButton("Cadastrar");
		cadastrar.setPreferredSize(new Dimension(95, 30));
		cadastrar.addActionListener(this::onCadastrarClick);
		
		cancelar = new JButton("Cancelar");
		cancelar.setPreferredSize(new Dimension(95, 30));
		cancelar.addActionListener((ActionEvent e) -> {confirmExit();});
		
		novo = new JButton("Novo");
		novo.setPreferredSize(new Dimension(95, 30));
		novo.addActionListener(this::onNovoClick);
		
		desabilitar();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc;
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(lblCliente, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cbxCliente, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(lblCarro, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 80);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cbxCarro, gbc);
		
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(lblMarca, gbc);
		
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 80);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(txtMarca, gbc);
		
		
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(lblDataRet, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(txtDataRetirada, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(lblDataDev, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(txtDataDevolucao, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(15, 5, 15, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(novo, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(15, 0, 15, 100);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cadastrar, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(15, 10, 15, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cancelar, gbc);
		
		
		return panel;
	}
	
	
	private void actionComboBoxs() {
		
		cbxCarro.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				cbCarroActionPerformed(evt);
			}
		});
	}
	
	
	private void cbCarroActionPerformed(ActionEvent evt) {

		if (cbxCarro.getItemCount() > 1) {
			Carro carro = (Carro) cbxCarro.getSelectedItem();
			txtMarca.setText(String.valueOf(carro.getMarca()));
		}
	}
	
	public void carregarCarro() {
		CarroDAO dao = new CarroDAO();
		List<Carro> listaCarro = dao.listaCarro();
		cbxCarro.removeAllItems();
		
		for (Carro carro : listaCarro) {
			cbxCarro.addItem(carro);
		}
	}
	
	public void carregarCliente() {
		ClienteDAO dao = new ClienteDAO();
		List<Cliente> listaCliente = dao.listaCliente();
		cbxCliente.removeAllItems();
		
		for(Cliente cliente : listaCliente) {
			cbxCliente.addItem(cliente);
		}
	}
	
	private void habilitar() {
		cbxCliente.setEnabled(true);
		cbxCarro.setEnabled(true);
		txtMarca.setEnabled(true);
		txtDataRetirada.setEnabled(true);
		txtDataDevolucao.setEnabled(true);
		carregarCliente();
		carregarCarro();
	}
	
	private void desabilitar() {
		cbxCliente.setEnabled(false);
		cbxCarro.setEnabled(false);
		txtMarca.setEnabled(false);
		txtDataRetirada.setEnabled(false);
		txtDataDevolucao.setEnabled(false);
	} 
	
	public void onNovoClick(ActionEvent e) {
		
		habilitar();
		actionComboBoxs();
	}
	
	public void onCadastrarClick(ActionEvent e) {
		
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		try {
			dataRetirada = (Date) sdf.parse(txtDataRetirada.getText());
			dataDevolucao = (Date) sdf.parse(txtDataDevolucao.getText());
		} catch (ParseException e1) {
			// varificação data invalida
			JOptionPane.showMessageDialog(cadastrar, "Data Inválida", "Aviso", JOptionPane.ERROR_MESSAGE);
			return; 
		}
	
		Reserva reserva = new Reserva();
		Cliente cliente = new Cliente();
		Carro carro = new Carro();
		cliente = (Cliente) cbxCliente.getSelectedItem();
		carro = (Carro) cbxCarro.getSelectedItem();
		// verificações
		
		reserva.setCliente(cliente);
		reserva.setCarro(carro);
		reserva.setDataRetirada(dataRetirada);
		reserva.setDataDevolucao(dataDevolucao);
	
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				ReservaDAO dao = new ReservaDAO();
				dao.salvar(reserva);
				
				return null;
			}

			protected void done() {
				try {
					get(); // pegando o resultado 
					JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
					// deixando campos em brancos apos o cadastro realizado
					txtMarca.setText("");
					txtDataRetirada.setText("");
					txtDataDevolucao.setText("");
					
				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(null, "Reserva não realizada!", "Error message", JOptionPane.ERROR_MESSAGE);
				}
			};
		};

		worker.execute();
	}
	
	private void confirmExit() {
		int answer = JOptionPane.showConfirmDialog(null,
				"Se você sair do aplicativo, o cadastro será cancelado. Deseja continuar?", "Cancelar",
				JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
			setVisible(false);
			// releaseConnection();
			dispose();
		}
	}
	
}
