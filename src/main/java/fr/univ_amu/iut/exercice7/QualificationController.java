package fr.univ_amu.iut.exercice7;

import com.google.inject.Inject;
import fr.nedjar.vigiechiro.audio.AudioView;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * Contrôleur de vue du capstone.
 *
 * <p>Comme dans tous les exercices précédents, le contrôleur ne fait que câbler la vue au ViewModel
 * : il abonne la TableView à la liste des séquences, relaie la sélection au ViewModel, lie les
 * libellés et les commandes. Aucune logique métier ici.
 */
public class QualificationController {

  private static final DateTimeFormatter HEURE = DateTimeFormatter.ofPattern("HH:mm");

  @Inject private QualificationViewModel viewModel;

  @FXML private TableView<Sequence> tableSequences;
  @FXML private TableColumn<Sequence, String> colHorodatage;
  @FXML private TableColumn<Sequence, String> colFrequence;
  @FXML private TableColumn<Sequence, String> colDuree;
  @FXML private TableColumn<Sequence, String> colStatut;
  @FXML private Label labelSelection;
  @FXML private Button boutonEcouter;
  @FXML private TextArea zoneCommentaire;
  @FXML private ChoiceBox<String> choiceVerdict;
  @FXML private Label labelVerdictGlobal;
  @FXML private AudioView audioView;

  @FXML
  private void initialize() {
    // Composant audio de la SAE 2.01 (fourni) : une séquence par défaut au démarrage,
    // puis on recharge le fichier à chaque sélection dans le tableau (le composant
    // recalcule alors sonogramme et spectrogramme).
    chargerAudio("seq-1.wav");
    viewModel
        .sequenceSelectionneeProperty()
        .addListener(
            (obs, ancienne, seq) -> {
              if (seq != null) {
                chargerAudio(seq.getAudioRessource());
              }
            });

    // TODO exercice 7 : câbler entièrement la vue sur le ViewModel.
    //
    // 1. Colonnes (cell value factory) : horodatage (HH:mm), fréquence (%.1f kHz),
    //    durée (en s), statut.
    // 2. tableSequences.setItems(viewModel.sequencesProperty());
    // 3. Relayer la sélection : viewModel.sequenceSelectionneeProperty()
    //       .bind(tableSequences.getSelectionModel().selectedItemProperty());
    // 4. labelSelection <- descriptionSelectionProperty (sens unique).
    // 5. boutonEcouter désactivé quand rien n'est sélectionné :
    //       boutonEcouter.disableProperty().bind(viewModel.peutEcouterProperty().not());
    // 6. zoneCommentaire <-> commentaireProperty (bidirectionnel).
    // 7. choiceVerdict : items = viewModel.listeVerdicts(), valeur <-> verdictSaisiProperty.
    // 8. labelVerdictGlobal <- verdictGlobalLibelleProperty.

    colHorodatage.setCellValueFactory(
        c -> {
          LocalTime horodatage = c.getValue().getHorodatage();
          String heureString = horodatage.format(HEURE);
          return new SimpleStringProperty(heureString);
        });

    colFrequence.setCellValueFactory(
        c -> {
          double frequence = c.getValue().getFrequenceDominanteKHz();
          String frequenceString = String.format("%.1f kHz", frequence);
          return new SimpleStringProperty(frequenceString);
        });

    colDuree.setCellValueFactory(
        c -> {
          int duree = c.getValue().getDureeSecondes();
          return new SimpleStringProperty(duree + " s");
        });

    colStatut.setCellValueFactory(
        c -> {
          String statut = c.getValue().getStatut();
          return new SimpleStringProperty(statut);
        });

    tableSequences.setItems(viewModel.sequencesProperty());
    viewModel
        .sequenceSelectionneeProperty()
        .bind(tableSequences.getSelectionModel().selectedItemProperty());
    labelSelection.textProperty().bind(viewModel.descriptionSelectionProperty());
    boutonEcouter.disableProperty().bind(viewModel.peutEcouterProperty().not());
    zoneCommentaire.textProperty().bindBidirectional(viewModel.commentaireProperty());

    choiceVerdict.setItems(FXCollections.observableList(viewModel.listeVerdicts()));
    choiceVerdict.valueProperty().bindBidirectional(viewModel.verdictSaisiProperty());
    labelVerdictGlobal.textProperty().bind(viewModel.verdictGlobalLibelleProperty());
  }

  @FXML
  private void surEcouter() {
    viewModel.ecouterCommand();
    // Le composant audio de la SAE lance la lecture de la séquence (fourni).
    audioView.setPlaying(true);
  }

  @FXML
  private void surEnregistrerVerdict() {
    viewModel.enregistrerVerdictCommand();
  }

  /**
   * Charge l'enregistrement {@code ressource} (fourni dans les ressources) dans le composant {@link
   * AudioView} de la SAE, qui recalcule alors sonogramme, spectrogramme et lecture. Appelé à chaque
   * changement de sélection : on voit le composant recharger le fichier.
   */
  private void chargerAudio(String ressource) {
    try {
      audioView.setAudioFile(Path.of(getClass().getResource("/audio/" + ressource).toURI()));
    } catch (Exception e) {
      // Ressource absente : on laisse le composant vide (cas non bloquant pour le TP).
    }
  }
}
