package ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Facture extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField titre;
	private JTextField merci;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Facture frame = new Facture(null, null, null, null, null,null);
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
	public Facture(Paiement paiement, String sousTotal, String frais, String total, String livreur, JTable table) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		titre = new JTextField();
		titre.setEditable(false);
		titre.setToolTipText("");
		titre.setFont(new Font("Agency FB", Font.BOLD, 30));
		titre.setForeground(new Color(255, 255, 255));
		titre.setBackground(new Color(0, 128, 128));
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		titre.setText("VOTRE FACTURE");
		panel.add(titre, BorderLayout.NORTH);
		titre.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 10));
		
		merci = new JTextField();
		merci.setHorizontalAlignment(SwingConstants.CENTER);
		merci.setEditable(false);
		merci.setFont(new Font("Alef", Font.PLAIN, 17));
		merci.setForeground(new Color(0, 128, 128));
		merci.setText("L'ÉQUIPE Fromarché VOUS REMERCIE DE VOTRE VISITE !");
		panel_1.add(merci, BorderLayout.NORTH);
		merci.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		// Création du nouveau tableau pour la facture
        DefaultTableModel factureModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Produit", "Prix Unitaire", "Quantité", "Total" }) {
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche la modification des cellules
            }
        };
        JTable factureTable = new JTable(factureModel);
        factureTable.setFont(new Font("Tahoma", Font.BOLD, 10));

        // Copier les données du panier en supprimant la première colonne
        DefaultTableModel panierModel = (DefaultTableModel) table.getModel();
        for (int i = 0; i < panierModel.getRowCount(); i++) {
            Object produit = panierModel.getValueAt(i, 1);
            Object prix = panierModel.getValueAt(i, 2);
            Object quantite = panierModel.getValueAt(i, 3);
            Object totalProduit = panierModel.getValueAt(i, 4);

            factureModel.addRow(new Object[] { produit, prix, quantite, totalProduit });
        }
        JScrollPane scrollPane = new JScrollPane(factureTable);
        panel_3.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new GridLayout(4, 2, 0, 7));
		
		JLabel prix_commande = new JLabel("PRIX TTC COMMANDE :   "+sousTotal+" par "+paiement.getMoyenPaiement());
		prix_commande.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_4.add(prix_commande);
		
		JLabel frais_livraison = new JLabel("FRAIS DE LIVRAISON :    "+frais+" par "+ livreur);
		frais_livraison.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_4.add(frais_livraison);
		
		JLabel total_TTC = new JLabel("TOTAL TTC :              "+ total);
		total_TTC.setForeground(new Color(0, 128, 128));
		total_TTC.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_4.add(total_TTC);
		
		JPanel panel_5 = new JPanel();
		panel_5.setForeground(new Color(0, 128, 128));
		panel_3.add(panel_5, BorderLayout.NORTH);
		
		JLabel NP = new JLabel(paiement.getNom().toUpperCase()+" "+paiement.getPrenom());
		NP.setFont(new Font("Tahoma", Font.BOLD, 13));
		NP.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel Adresse = new JLabel("Adresse : "+paiement.getAdresse());
		Adresse.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel mail = new JLabel("Mail : "+paiement.getMail());
		mail.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel tel = new JLabel("Tel : "+paiement.getTelephone());
		tel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_5.setLayout(new GridLayout(5, 1, 0, 7));
		panel_5.add(NP);
		panel_5.add(Adresse);
		panel_5.add(mail);
		panel_5.add(tel);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton imprimer = new JButton("Imprimer");
		imprimer.setForeground(new Color(255, 255, 255));
		imprimer.setBackground(new Color(0, 128, 128));
		panel_2.add(imprimer);
		imprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Imprimer(contentPane).print(); // Appel de la méthode d'impression
            }
        });
		
		
		JButton quitter = new JButton("Quitter");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		quitter.setForeground(new Color(0, 128, 128));
		panel_2.add(quitter);
	}

}