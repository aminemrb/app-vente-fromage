package ihm;
import modele.*;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class Panier extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField SousTotal;
    private JTextField frais;
    private JTextField total2;
    private JTable table;
    private JComboBox<String> transport; 
    
 // Frais de port en fonction du transporteur et du total d'achat
    private static final double[][] SHIPPING_COSTS = {
        {60, 14.90, 90, 9.90, 120, 4.90, Double.MAX_VALUE, 0}, // Colissimo
        {60, 14.90, 90, 9.90, 120, 4.90, Double.MAX_VALUE, 0}, // Chronorelais
        {50, 23.80, 80, 17.80, 120, 9.90, Double.MAX_VALUE, 0}  // Chronofresh
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Panier frame = new Panier(new AffichageListeFromages());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Panier(AffichageListeFromages Accueil) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 592, 529);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 128, 128));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel header = new JPanel();
        header.setBackground(new Color(0, 128, 128));
        contentPane.add(header, BorderLayout.NORTH);

        JLabel TITRE = new JLabel("VOTRE PANIER");
        TITRE.setIcon(null);
        TITRE.setHorizontalAlignment(SwingConstants.CENTER);
        TITRE.setBackground(new Color(255, 255, 255));
        TITRE.setForeground(new Color(255, 255, 255));
        TITRE.setFont(new Font("Agency FB", Font.BOLD, 30));

        JButton raffraichir = new JButton("Calculer le panier");
        raffraichir.setBackground(new Color(255, 255, 255));
        raffraichir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculerPanier(transport);
            }
        });
        raffraichir.setForeground(new Color(0, 128, 128));
        header.setLayout(new BorderLayout(0, 0));
        header.add(TITRE, BorderLayout.CENTER);
        header.add(raffraichir, BorderLayout.EAST);

        JPanel footer = new JPanel();
        footer.setBackground(new Color(192, 192, 192));
        contentPane.add(footer, BorderLayout.SOUTH);
        footer.setLayout(new BorderLayout(0, 0));

        JPanel livraison_prix = new JPanel();
        livraison_prix.setBackground(new Color(0, 128, 128));
        footer.add(livraison_prix, BorderLayout.NORTH);
        livraison_prix.setLayout(new BorderLayout(0, 0));

        JPanel prix = new JPanel();
        prix.setBackground(new Color(0, 128, 128));
        livraison_prix.add(prix, BorderLayout.EAST);
        prix.setLayout(new GridLayout(3, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("Sous-Total");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBackground(new Color(192, 192, 192));
        prix.add(lblNewLabel);

        SousTotal = new JTextField();
        SousTotal.setBackground(new Color(255, 255, 255));
        SousTotal.setEditable(false);
        prix.add(SousTotal);
        SousTotal.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Frais de ports");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBackground(new Color(192, 192, 192));
        prix.add(lblNewLabel_1);

        frais = new JTextField();
        frais.setBackground(new Color(255, 255, 255));
        frais.setEditable(false);
        prix.add(frais);
        frais.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("TOTAL");
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setBackground(new Color(192, 192, 192));
        prix.add(lblNewLabel_2);

        total2 = new JTextField();
        total2.setForeground(new Color(0, 128, 128));
        total2.setBackground(new Color(255, 255, 255));
        total2.setEditable(false);
        prix.add(total2);
        total2.setColumns(10);

        JPanel Livraison = new JPanel();
        Livraison.setBackground(new Color(0, 128, 128));
        livraison_prix.add(Livraison, BorderLayout.WEST);
        Livraison.setLayout(new BorderLayout(0, 0));

        JLabel offre = new JLabel("120€ d'achats = Frais de port OFFERT !");
        offre.setBackground(new Color(0, 128, 128));
        offre.setFont(new Font("Tahoma", Font.PLAIN, 15));
        offre.setForeground(new Color(255, 255, 255));
        Livraison.add(offre, BorderLayout.NORTH);

        JPanel livreur = new JPanel();
        livreur.setBackground(new Color(0, 128, 128));
        livreur.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Livraison", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        Livraison.add(livreur, BorderLayout.CENTER);
        
        transport = new JComboBox<>(); 
        transport.setForeground(new Color(0, 128, 128));
        transport.setBackground(new Color(255, 255, 255));
        transport.setModel(new DefaultComboBoxModel<>(new String[] {"Colissimo", "Chronofresh", "Chronorelais"}));
        GroupLayout Glivreur = new GroupLayout(livreur);
        Glivreur.setHorizontalGroup(
        	Glivreur.createParallelGroup(Alignment.LEADING)
        		.addGroup(Glivreur.createSequentialGroup()
        			.addGap(76)
        			.addComponent(transport, 0, 168, Short.MAX_VALUE)
        			.addContainerGap())
        );
        Glivreur.setVerticalGroup(
        	Glivreur.createParallelGroup(Alignment.LEADING)
        		.addGroup(Glivreur.createSequentialGroup()
        			.addGap(5)
        			.addComponent(transport, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        			.addContainerGap())
        );
        livreur.setLayout(Glivreur);
        

        JPanel boutons = new JPanel();
        boutons.setBackground(new Color(0, 128, 128));
        footer.add(boutons, BorderLayout.SOUTH);

        JButton Payer = new JButton("Procéder au paiement");
        Payer.setForeground(new Color(0, 128, 128));
        Payer.setBackground(new Color(255, 255, 255));
        boutons.add(Payer);
        Payer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(table.getRowCount()>0) {
	                Paiement paiement = new Paiement(Panier.this);
	                paiement.setVisible(true);
	                calculerPanier(transport);
	                dispose(); // Fermer la fenêtre actuelle
            	}else {
            		System.out.println("Votre panier est vide");
            	}
            }
        });

        JButton Vider = new JButton("Vider le panier");
        Vider.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		viderPanier();
        		Accueil.setVisible(true);
        		Accueil.BoutonPrix.setText("0€");
        		Panier.this.setVisible(false);
        	}
        });
        Vider.setBackground(new Color(255, 255, 255));
        Vider.setForeground(new Color(0, 128, 128));
        boutons.add(Vider);

        JButton Continuer = new JButton("Continuer les achats");
        Continuer.setBackground(new Color(255, 255, 255));
        Continuer.setForeground(new Color(0, 128, 128));
        Continuer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Accueil.BoutonPrix.setText(getSousTotal());
                Accueil.setVisible(true);
                dispose(); // Fermer la fenêtre actuelle
            }
        });
        boutons.add(Continuer);

        DefaultTableModel model = new DefaultTableModel(new Object[][] {},
                new String[] { "Image", "Produit", "Prix", "Quantité", "Total" }) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return ImageIcon.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche la modification des cellules
            }
        };
        table = new JTable(model);
        table.setRowHeight(70); // Ajuster la hauteur des lignes pour les images
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        


    }


    

    public void ajouterAuPanier(String image, String produit, String prix, String quantite, String total) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        boolean found = false;

        // Recherche si le produit existe déjà dans le panier
        for (int i = 0; i < model.getRowCount(); i++) {
            String produitExistant = (String) model.getValueAt(i, 1);
            String prixExistant = (String) model.getValueAt(i, 2);

            // Vérifier si le produit et le type de vente sont identiques
            if (produitExistant.equals(produit) && prixExistant.equals(prix)) {
                // Mettre à jour la quantité et le total
                int quantiteExistante = Integer.parseInt((String) model.getValueAt(i, 3));
                int nouvelleQuantite = quantiteExistante + Integer.parseInt(quantite);
                double prixUnitaire = Double.parseDouble(prix.split(" ")[0].replace(",", ".").replace("€", ""));
                double nouveauTotal = nouvelleQuantite * prixUnitaire;

                model.setValueAt(String.valueOf(nouvelleQuantite), i, 3);
                model.setValueAt(String.format("%.2f€", nouveauTotal), i, 4);
                found = true;
                break;
            }
        }

        // Si le produit n'existe pas dans le panier, l'ajouter
        if (!found) {
            ImageIcon frimage = new ImageIcon(getClass().getResource("/images/fromages/hauteur40/" + image + ".jpg"));
            model.addRow(new Object[]{frimage, produit, prix, quantite, total + "€"});
        }
    }


    
    public void viderPanier () {
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
    	model.setRowCount(0);
    	// supprimer le sous-total
        SousTotal.setText(null);

        // supprimer les frais de port
        frais.setText(null);;

        // supprimer le total
        total2.setText(null);
        
    }

    public void calculerPanier(JComboBox<String> transport) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        double sousTotal = 0.0;

        // Parcourir les lignes du tableau pour calculer le sous-total
        for (int i = 0; i < model.getRowCount(); i++) {
            String totalString = (String) model.getValueAt(i, 4);
            totalString = totalString.replace("€", "").replace(",", ".").trim(); // Remplacer la virgule par un point
            try {
                double total = Double.parseDouble(totalString);
                sousTotal += total;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (model.getRowCount() == 0) {
            System.out.println("Votre panier est vide");
        } else {
            // Afficher le sous-total dans le champ de texte
            SousTotal.setText(String.format("%.2f€", sousTotal));

            // Calculer les frais de port en fonction du transporteur sélectionné
            String transporteur = (String) transport.getSelectedItem();
            double fraisDePort = calculerFraisDePort(transporteur, sousTotal);
            frais.setText(String.format("%.2f€", fraisDePort));

            // Calculer le total
            double total = sousTotal + fraisDePort;
            total2.setText(String.format("%.2f€", total));
        }
    }


    private double calculerFraisDePort(String transporteur, double fraisDePort) {
        int transporteurIndex = 0;
        switch (transporteur) {
            case "Colissimo":
                transporteurIndex = 0;
                break;
            case "Chronorelais":
                transporteurIndex = 1;
                break;
            case "Chronofresh":
                transporteurIndex = 2;
                break;
            default:
                return 0.0;
        }

        for (int i = 0; i < SHIPPING_COSTS[transporteurIndex].length; i += 2) {
            if (fraisDePort < SHIPPING_COSTS[transporteurIndex][i]) {
                return SHIPPING_COSTS[transporteurIndex][i + 1];
            }
        }
        return 0.0;
    }
    
    public String getSousTotal() {
    	return SousTotal.getText();
    }
    public String getFrais() {
    	return frais.getText();
    }
    public String getTotal() {
    	return total2.getText();
    }
    public String getLivreur() {
    	return transport.getSelectedItem().toString();
    }
    
    public JTable getTable() {
    	table.setRowHeight(30);
    	table.setColumnSelectionInterval(0, 1);
    	return table;
    }
    public JComboBox<String> getTransport() {
    	return transport;
    }

}
