package ihm;

import modele.*;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SpinnerNumberModel;
import java.awt.FlowLayout;
import javax.swing.DropMode;

public class InfoFromage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel infos;
    private Article articleSelectionner;
    private float prixSelectionner;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InfoFromage frame = new InfoFromage(new Panier(null), new Fromage("Default"), null);
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
    public InfoFromage(Panier panier, Fromage selectedFromage, AffichageListeFromages affichageListeFromages) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 765, 495);
        infos = new JPanel();
        infos.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(infos);
        infos.setLayout(new BorderLayout(0, 0));

        JPanel title = new JPanel();
        infos.add(title, BorderLayout.NORTH);
        title.setLayout(new GridLayout(1, 1, 0, 0));

        JPanel titre = new JPanel();
        title.add(titre);
        titre.setLayout(new GridLayout(0, 1, 0, 0));

        JLabel Nom = new JLabel("");
        Nom.setForeground(new Color(0, 128, 128));
        Nom.setHorizontalAlignment(SwingConstants.CENTER);
        Nom.setVerticalAlignment(SwingConstants.TOP);
        Nom.setFont(new Font("Agency FB", Font.BOLD, 33));
        Nom.setText(selectedFromage.getDésignation());
        titre.add(Nom);

        JPanel description = new JPanel();
        description.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Description", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 128, 128)));
        description.setToolTipText("");
        infos.add(description, BorderLayout.CENTER);
        description.setLayout(new GridLayout(1, 1, 0, 0));

        JTextArea texte = new JTextArea();
        texte.setFont(new Font("Arial", Font.PLAIN, 17));
        texte.setLineWrap(true);
        texte.setEditable(false);
        texte.setText(selectedFromage.getDescription());
        description.add(texte);

        JPanel Choix = new JPanel();
        infos.add(Choix, BorderLayout.SOUTH);
        Choix.setLayout(new BorderLayout(0, 0));

        JPanel Prix_Quantité = new JPanel();
        Choix.add(Prix_Quantité, BorderLayout.NORTH);

        JComboBox<String> prix = new JComboBox<>();
        prix.setForeground(new Color(0, 128, 128));
        DefaultComboBoxModel<String> prixModel = new DefaultComboBoxModel<>();
        for (Article article : selectedFromage.getArticles()) {
            String prixTTC = article.getPrixTTC() + " €";
            String TypeVente = article.getClé();
            prixModel.addElement("Prix TTC: " + prixTTC + "/" + TypeVente);
        }
        Prix_Quantité.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        prix.setModel(prixModel);
        prix.setBackground(new Color(255, 255, 255));
        prix.setSelectedIndex(0);
        Prix_Quantité.add(prix);

        JSpinner quantité = new JSpinner();
        quantité.setForeground(new Color(240, 240, 240));
        quantité.setModel(new SpinnerNumberModel(1, 1, 1, 1));
        quantité.setFont(new Font("Tahoma", Font.PLAIN, 13));
        quantité.setBackground(new Color(128, 255, 255));
        Prix_Quantité.add(quantité);
        if (prix.getSelectedIndex() > -1) {
        	articleSelectionner = selectedFromage.getArticles().get(prix.getSelectedIndex());
            prixSelectionner = articleSelectionner.getPrixTTC();
            int quantite = articleSelectionner.getQuantitéEnStock();
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, quantite, 1);
            quantité.setModel(spinnerModel);
        }

        prix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = prix.getSelectedIndex();
                if (index > -1) {
                	articleSelectionner = selectedFromage.getArticles().get(index);
                    prixSelectionner = articleSelectionner.getPrixTTC();
                    int quantite = articleSelectionner.getQuantitéEnStock();
                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, quantite, 1);
                    quantité.setModel(spinnerModel);
                }
            }
        });

        JPanel Panier_Annuler = new JPanel();
        Choix.add(Panier_Annuler, BorderLayout.SOUTH);

        JButton ajouterPanier = new JButton("Ajouter au Panier");
        ajouterPanier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                if (articleSelectionner != null) {
                    int quantite = (int) quantité.getValue();
                    float totalPrix = prixSelectionner * quantite;
                    String Vente = articleSelectionner.getClé();
                    
                    // Ajoutez l'article au panier avec les informations nécessaires
                    panier.ajouterAuPanier(selectedFromage.getNomImage(), selectedFromage.getDésignation(), String.valueOf(prixSelectionner+"€ / "+Vente), 
                    		String.valueOf(quantite), String.valueOf(totalPrix));
                    articleSelectionner.setQuantitéEnStock(articleSelectionner.getQuantitéEnStock()-quantite); //gestion du stock
                    panier.calculerPanier(panier.getTransport());
                    affichageListeFromages.BoutonPrix.setText(panier.getSousTotal());
                    dispose();
                }
            }
        });
        ajouterPanier.setForeground(Color.WHITE);
        ajouterPanier.setBackground(new Color(0, 128, 128));
        Panier_Annuler.add(ajouterPanier);

        JButton annuler = new JButton("Annuler");
        annuler.setForeground(new Color(0, 128, 128));
        annuler.setBackground(new Color(255, 255, 255));
        Panier_Annuler.add(annuler);
        annuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel image = new JPanel();
        image.setToolTipText("");
        infos.add(image, BorderLayout.WEST);
        image.setLayout(new BorderLayout(0, 0));

        JLabel img = new JLabel("");
        ImageIcon frimage = new ImageIcon(getClass().getResource("/images/fromages/hauteur200/" + selectedFromage.getNomImage() + ".jpg"));
        Image imageFromage = frimage.getImage();
        Image scaledFrimage = imageFromage.getScaledInstance(300, 300,java.awt.Image.SCALE_SMOOTH);
        frimage = new ImageIcon(scaledFrimage);
        img.setIcon(frimage);
        img.setToolTipText("");
        image.add(img);
    }
}
