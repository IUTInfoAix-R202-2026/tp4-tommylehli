package fr.univ_amu.iut.exercice7;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test du capstone, cote ViewModel : toute la logique de l'ecran se verifie sans interface
 * graphique. C'est la promesse du MVVM, ici sur l'ecran le plus riche du TP.
 */
class QualificationViewModelTest {

  private QualificationViewModel vm;

  @BeforeEach
  void preparer() {
    vm = new QualificationViewModel(new ServiceNuitsDemo());
  }

  @Test
  void la_nuit_contient_dix_sequences() {
    assertThat(vm.sequencesProperty())
        .as("la nuit de demonstration compte 10 sequences")
        .hasSize(10);
  }

  @Test
  void sans_selection_le_libelle_invite_a_choisir_et_l_ecoute_est_impossible() {
    assertThat(vm.descriptionSelectionProperty().get())
        .isEqualTo("(sélectionnez une séquence dans le tableau)");
    assertThat(vm.peutEcouterProperty().get())
        .as("sans selection on ne peut pas ecouter")
        .isFalse();
  }

  @Test
  void selectionner_une_sequence_active_l_ecoute_et_decrit_la_selection() {
    Sequence premiere = vm.sequencesProperty().get(0);
    vm.sequenceSelectionneeProperty().set(premiere);

    assertThat(vm.peutEcouterProperty().get()).isTrue();
    assertThat(vm.descriptionSelectionProperty().get())
        .as("la description doit mentionner l'heure et la frequence")
        .startsWith("Séquence 20:00 - ")
        .endsWith("kHz");
  }

  @Test
  void ecouter_passe_la_sequence_selectionnee_au_statut_ecoutee() {
    Sequence premiere = vm.sequencesProperty().get(0);
    assertThat(premiere.getStatut()).isEqualTo("À écouter");

    vm.sequenceSelectionneeProperty().set(premiere);
    vm.ecouterCommand();

    assertThat(premiere.getStatut()).isEqualTo("Écoutée");
  }

  @Test
  void les_trois_verdicts_sont_proposes() {
    assertThat(vm.listeVerdicts()).containsExactly("OK", "Douteux", "À jeter");
  }

  @Test
  void le_libelle_du_verdict_invite_a_saisir_tant_que_rien_n_est_enregistre() {
    assertThat(vm.verdictGlobalLibelleProperty().get()).isEqualTo("Verdict global : (à saisir)");
  }

  @Test
  void enregistrer_un_verdict_met_a_jour_le_libelle() {
    vm.verdictSaisiProperty().set("OK");
    vm.enregistrerVerdictCommand();

    assertThat(vm.verdictGlobalLibelleProperty().get()).isEqualTo("Verdict global : OK");
  }

  @Test
  void le_commentaire_est_relie_au_modele() {
    vm.commentaireProperty().set("Beaucoup de Pipistrelles cette nuit.");

    // La propriete exposee est directement celle du modele : on verifie qu'elle
    // porte bien la valeur saisie.
    assertThat(vm.commentaireProperty().get()).isEqualTo("Beaucoup de Pipistrelles cette nuit.");
  }
}
