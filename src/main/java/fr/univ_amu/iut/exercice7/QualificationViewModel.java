package fr.univ_amu.iut.exercice7;

import com.google.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * ViewModel du capstone : vérifier une nuit d'enregistrement (parcours P3 de la SAÉ 2.01).
 *
 * <p>C'est la pierre angulaire du TP : tout ce que vous avez appris s'y combine.
 *
 * <ul>
 *   <li>le service de données est <b>injecté par Guice</b> ({@code @Inject}) ;
 *   <li>la liste des séquences est une <b>ObservableList</b> exposée à la TableView ;
 *   <li>les libellés affichés sont des <b>propriétés dérivées</b> (description de la sélection,
 *       libellé du verdict) ;
 *   <li>les actions sont des <b>commandes</b> ({@code ecouterCommand}, {@code
 *       enregistrerVerdictCommand}).
 * </ul>
 *
 * <p>Tout est testable sans interface : voir {@code QualificationViewModelTest}.
 */
public class QualificationViewModel {

  private static final DateTimeFormatter HEURE = DateTimeFormatter.ofPattern("HH:mm");

  private final NuitVerification nuit;

  private final ObjectProperty<Sequence> sequenceSelectionnee = new SimpleObjectProperty<>();
  private final BooleanBinding peutEcouter = sequenceSelectionnee.isNotNull();
  private final StringProperty descriptionSelection = new SimpleStringProperty();
  private final StringProperty verdictSaisi = new SimpleStringProperty("");
  private final StringProperty verdictGlobalLibelle = new SimpleStringProperty();

  @Inject
  public QualificationViewModel(ServiceNuits service) {
    this.nuit = service.chargerNuit();

    // TODO exercice 7 : lier les deux libellés dérivés.
    //
    // 1. descriptionSelection :
    //    - si aucune séquence n'est sélectionnée -> "(sélectionnez une séquence dans le tableau)"
    //    - sinon -> "Séquence HH:mm - XX.X kHz" (heure puis fréquence à 1 décimale)
    //    Astuce : Bindings.createStringBinding(() -> {...}, sequenceSelectionnee).
    //
    // 2. verdictGlobalLibelle : "Verdict global : (à saisir)" tant que le verdict
    //    du modèle est vide, sinon "Verdict global : <verdict>".
    //    Astuce : dépend de nuit.verdictGlobalProperty().
    descriptionSelection.bind(
        Bindings.createStringBinding(
            () -> {
              Sequence seq = sequenceSelectionnee.get();
              if (seq == null) return ("(sélectionnez une séquence dans le tableau)");

              String heureString = seq.getHorodatage().format(HEURE);
              double frequence = seq.getFrequenceDominanteKHz();
              String frequenceFormat = String.format("%.1f kHz", frequence);

              return ("Séquence " + heureString + " - " + frequenceFormat + "kHz");
            },
            sequenceSelectionnee));

    verdictGlobalLibelle.bind(
        Bindings.createStringBinding(
            () -> {
              String verdict = nuit.verdictGlobalProperty().get();
              if (verdict == null || verdict.trim().isEmpty())
                return ("Verdict global : (à saisir)");
              else return ("Verdict global : " + verdict);
            },
            nuit.verdictGlobalProperty()));
  }

  public ObservableList<Sequence> sequencesProperty() {
    return nuit.getSequences();
  }

  public ObjectProperty<Sequence> sequenceSelectionneeProperty() {
    return sequenceSelectionnee;
  }

  public BooleanBinding peutEcouterProperty() {
    return peutEcouter;
  }

  public ReadOnlyStringProperty descriptionSelectionProperty() {
    return descriptionSelection;
  }

  public StringProperty commentaireProperty() {
    return nuit.commentaireProperty();
  }

  public StringProperty verdictSaisiProperty() {
    return verdictSaisi;
  }

  public ReadOnlyStringProperty verdictGlobalLibelleProperty() {
    return verdictGlobalLibelle;
  }

  /** Les trois verdicts possibles, proposés dans la ChoiceBox. */
  public List<String> listeVerdicts() {
    return List.of("OK", "Douteux", "À jeter");
  }

  /** Marque la séquence sélectionnée comme "Écoutée". */
  public void ecouterCommand() {
    // TODO exercice 7 : si une séquence est sélectionnée, passer son statut à "Écoutée".
    Sequence seq = sequenceSelectionnee.get();
    if (seq != null) seq.setStatut("Écoutée");
  }

  /** Enregistre le verdict saisi dans le modèle de la nuit. */
  public void enregistrerVerdictCommand() {
    // TODO exercice 7 : recopier le verdict saisi dans le modèle (nuit.setVerdictGlobal).
    nuit.setVerdictGlobal(verdictSaisi.getValue());
  }
}
