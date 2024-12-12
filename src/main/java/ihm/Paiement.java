package ihm;
import modele.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class Paiement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nom;
    private JTextField prenom;
    private JTextField adresse;
    private JTextField cp;
    private JTextField ville;
    private JTextField tel;
    private JTextField mail;

    private ButtonGroup paymentGroup;
    private ButtonGroup newsletterGroup;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Paiement frame = new Paiement(null);
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
    public Paiement(Panier panier) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 714, 427);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel header = new JPanel();
        contentPane.add(header, BorderLayout.NORTH);
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 0));
        
        JLabel titre = new JLabel("VOS COORDONNÉES  ");
        titre.setForeground(new Color(0, 128, 128));
        titre.setFont(new Font("Agency FB", Font.BOLD, 30));
        header.add(titre);
        
        JPanel centre = new JPanel();
        contentPane.add(centre, BorderLayout.CENTER);
        
        JLabel lblNewLabel = new JLabel("Nom :");
        lblNewLabel.setBounds(113, 6, 60, 27);
        
        JLabel lblNewLabel_1 = new JLabel("Prénom :");
        lblNewLabel_1.setBounds(100, 28, 60, 27);
        centre.setLayout(null);
        centre.add(lblNewLabel);
        centre.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Adresse 1 :");
        lblNewLabel_2.setBounds(100, 52, 67, 27);
        centre.add(lblNewLabel_2);
        
        nom = new JTextField();
        nom.setBounds(177, 10, 223, 19);
        centre.add(nom);
        nom.setColumns(10);
        
        prenom = new JTextField();
        prenom.setBounds(177, 32, 223, 19);
        centre.add(prenom);
        prenom.setColumns(10);
        
        adresse = new JTextField();
        adresse.setColumns(10);
        adresse.setBounds(177, 56, 223, 19);
        centre.add(adresse);
        
        JLabel CP = new JLabel("Code postal :");
        CP.setBounds(82, 76, 91, 27);
        centre.add(CP);
        
        cp = new JTextField();
        cp.setColumns(10);
        cp.setBounds(177, 80, 223, 19);
        centre.add(cp);
        
        JLabel Ville = new JLabel("Ville :");
        Ville.setBounds(121, 107, 39, 17);
        centre.add(Ville);
        
        ville = new JTextField();
        ville.setColumns(10);
        ville.setBounds(177, 106, 223, 19);
        centre.add(ville);
        
        JLabel Tel = new JLabel("Téléphone :");
        Tel.setBounds(100, 130, 78, 17);
        centre.add(Tel);
        
        tel = new JTextField();
        tel.setColumns(10);
        tel.setBounds(177, 130, 223, 17);
        centre.add(tel);
        
        JLabel Mail = new JLabel("Mail :");
        Mail.setBounds(130, 150, 43, 27);
        centre.add(Mail);
        
        mail = new JTextField();
        mail.setColumns(10);
        mail.setBounds(177, 154, 223, 17);
        centre.add(mail);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 10, 10);
        centre.add(panel);
        
        JPanel footer = new JPanel();
        contentPane.add(footer, BorderLayout.SOUTH);
        
        JPanel paiement = new JPanel();
        paiement.setBorder(new TitledBorder(null, "Moyen de paiment", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
        
        JRadioButton CB = new JRadioButton("Carte bancaire");
        CB.setActionCommand("Carte Bancaire");
        paiement.add(CB);
        
        JRadioButton Paypal = new JRadioButton("Paypal");
        Paypal.setActionCommand("PayPal");
        paiement.add(Paypal);
        
        JRadioButton Cheque = new JRadioButton("Chèque");
        Cheque.setActionCommand("Chèque");
        paiement.add(Cheque);
        
        paymentGroup = new ButtonGroup();
        paymentGroup.add(CB);
        paymentGroup.add(Paypal);
        paymentGroup.add(Cheque);
        
        JPanel newsletter = new JPanel();
        newsletter.setBorder(new TitledBorder(null, "Abonnement \u00E0 notre Newsletter", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
        
        JRadioButton oui = new JRadioButton("OUI");
        newsletter.add(oui);
        
        JRadioButton non = new JRadioButton("NON\r\n");
        newsletter.add(non);
        
        newsletterGroup = new ButtonGroup();
        newsletterGroup.add(oui);
        newsletterGroup.add(non);
        
        JPanel boutons = new JPanel();
        
        JButton valider = new JButton("VALIDER");
        valider.setForeground(new Color(255, 255, 255));
        valider.setBackground(new Color(0, 128, 128));
        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ElementsValides()) {
                    System.out.println("Informations valides et complètes.");
                    Facture f = new Facture(Paiement.this, panier.getSousTotal(), panier.getFrais(), panier.getTotal(), panier.getLivreur(),panier.getTable());
                    f.setVisible(true);
                    dispose();
                } else {
                    System.out.println("Veuillez remplir tous les champs correctement.");
                }
            }
        });
        
        JButton annuler = new JButton("Annuler");
        annuler.setForeground(new Color(0, 128, 128));
        annuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panier.setVisible(true);
                dispose(); // Fermer la fenêtre actuelle
            }
        });
        footer.setLayout(new BorderLayout(0, 0));
        footer.add(paiement, BorderLayout.NORTH);
        footer.add(newsletter, BorderLayout.CENTER);
        footer.add(boutons, BorderLayout.SOUTH);
        boutons.setLayout(new BorderLayout(0, 0));
        boutons.add(valider, BorderLayout.WEST);
        boutons.add(annuler, BorderLayout.EAST);
    }

    private boolean ElementsValides() {
        if (nom.getText().trim().isEmpty() ||
            prenom.getText().trim().isEmpty() ||
            adresse.getText().trim().isEmpty() ||
            cp.getText().trim().isEmpty() ||
            ville.getText().trim().isEmpty() ||
            tel.getText().trim().isEmpty() ||
            mail.getText().trim().isEmpty()) {
            return false;
        }
        
        if (!Pattern.matches("\\d{5}", cp.getText().trim())) {
            return false; // Code postal invalide
        }
        
        if (!Pattern.matches("\\d{10}", tel.getText().trim())) {
            return false; // Numéro de téléphone invalide
        }
        
        if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", mail.getText().trim())) {
            return false; // Adresse email invalide
        }
        
        if (paymentGroup.getSelection() == null || newsletterGroup.getSelection() == null) {
            return false; // Pas de sélection de moyen de paiement ou d'abonnement à la newsletter
        }
        
        return true;
    }
    public String getNom() {
        return nom.getText().trim();
    }

    public String getPrenom() {
        return prenom.getText().trim();
    }

    public String getAdresse() {
        return adresse.getText().trim() + ", " + cp.getText().trim() + " " + ville.getText().trim().toUpperCase();
    }

    public String getTelephone() {
        return tel.getText().trim();
    }

    public String getMail() {
        return mail.getText().trim();
    }
    
    public String getMoyenPaiement() {
    	return paymentGroup.getSelection().getActionCommand();
    }
}
