package ihm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import modele.Fromage;
import modele.Fromages;
import modele.GenerationFromages;


@SuppressWarnings("serial")
public class AffichageListeFromages extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<Fromage> theFromages;
	private DefaultListModel<String> nomFromages;
	JList<String> listeFromages;
	JButton BoutonPrix = new JButton();
	private Panier panier;
    private Fromages tousLesFromages;

	public static void main(String[] args) {
		AffichageListeFromages fa = new AffichageListeFromages();
		fa.setVisible(true);
	}

	public AffichageListeFromages() {
		this.panier = new Panier(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 500);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelHeader = new JPanel();
		this.contentPane.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));

		JLabel Titre = new JLabel("NOS FROMAGES");
		Titre.setHorizontalAlignment(SwingConstants.CENTER);
		Titre.setForeground(new Color(0, 128, 128));
		Titre.setFont(new Font("Agency FB", Font.BOLD, 33));
		panelHeader.add(Titre, BorderLayout.CENTER);
		BoutonPrix.setVerticalAlignment(SwingConstants.TOP);
		BoutonPrix.setForeground(new Color(0, 128, 128));
		BoutonPrix.setFont(new Font("Amiri Quran", Font.BOLD, 16));
		BoutonPrix.setBackground(new Color(255, 255, 255));
		this.BoutonPrix.setIcon(new ImageIcon(getClass().getResource("/icones/panier.png")));

		BoutonPrix.setText("0 €");
		this.BoutonPrix.setHorizontalAlignment(SwingConstants.RIGHT);
		panelHeader.add(this.BoutonPrix, BorderLayout.EAST);
 
		JPanel panelFooter = new JPanel();
		this.contentPane.add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new BorderLayout(0, 0));

		JPanel Filtres = new JPanel();
		Filtres.setBackground(new Color(240, 240, 240));
		Filtres.setForeground(new Color(255, 255, 255));
		Filtres.setBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Filtres", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		panelFooter.add(Filtres, BorderLayout.CENTER);
		Filtres.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setForeground(new Color(0, 0, 0));
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Tous les fromages", "Chèvre", "Brebis", "Vache"}));
		comboBox.setMaximumRowCount(4);
		comboBox.setBackground(new Color(255, 255, 255));
		Filtres.add(comboBox);
		
		JButton Quitter = new JButton("");
		Quitter.setForeground(new Color(255, 255, 255));
		Quitter.setBackground(new Color(0, 128, 128));
		Quitter.setIcon(new ImageIcon(getClass().getResource("/icones/quitter.png")));
		panelFooter.add(Quitter, BorderLayout.EAST);

		JScrollPane scrollPane = new JScrollPane();
		this.contentPane.add(scrollPane, BorderLayout.CENTER);

		this.theFromages = new DefaultListModel<Fromage>();
		this.nomFromages = new DefaultListModel<String>();
		Fromages tousLesFromages = new Fromages();
		tousLesFromages = modele.GenerationFromages.générationBaseFromages();
		int tailleFro = tousLesFromages.taille();
		for(int i = 0 ; i < tailleFro; i++) {
			this.theFromages.addElement(tousLesFromages.getFromages().get(i));
			this.nomFromages.addElement(tousLesFromages.getFromages().get(i).getDésignation());
		}

		
		this.listeFromages = new JList<>(this.nomFromages);
		listeFromages.setForeground(new Color(255, 255, 255));
		listeFromages.setBackground(new Color(0, 128, 128));
		scrollPane.setViewportView(this.listeFromages);

		this.listeFromages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selectedValue = AffichageListeFromages.this.listeFromages.getSelectedIndex();
					System.out.println("Double-clicked on: " + selectedValue);
					
					Fromage selectedFromage = theFromages.get(selectedValue);
		            InfoFromage Info = new InfoFromage(panier, selectedFromage, AffichageListeFromages.this); // Passer l'instance actuelle
		            Info.setVisible(true);  
					
				}
			}
		});

		Quitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Quitter l'application
                System.exit(0);
            }});
		BoutonPrix.addActionListener(e -> {
		    if (panier.getTable().getRowCount() == 0) {
		        // Affiche une fenêtre pop-up si le panier est vide
		        JOptionPane.showMessageDialog(this, "Votre panier est vide", "Panier", JOptionPane.INFORMATION_MESSAGE);
		    } else {
		        panier.setVisible(true);
		        this.setVisible(false);
		    }
		});
		
		this.tousLesFromages = modele.GenerationFromages.générationBaseFromages();
        
		comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String FiltreSelectionner = (String) comboBox.getSelectedItem();
                ListeUpdate(FiltreSelectionner);
            }
        });
		
	}
	
	private void ListeUpdate(String filtre) {
        theFromages.clear();
        nomFromages.clear();
        for (Fromage fromage : this.tousLesFromages.getFromages()) {
            if (fromage.getTypeFromage().getTypeDeLait()==filtre) {
                theFromages.addElement(fromage);
                nomFromages.addElement(fromage.getDésignation());
            }else if(filtre=="Tous les fromages"){
            	theFromages.addElement(fromage);
                nomFromages.addElement(fromage.getDésignation());
            }
        }
    }
}