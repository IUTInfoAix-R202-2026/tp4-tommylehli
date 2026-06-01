package fr.univ_amu.iut.exercice1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test de l'exercice 1.
 *
 * <p>Remarquez : AUCUN import JavaFX d'interface, AUCUN lancement d'application, AUCUN TestFX. Le
 * ViewModel se teste comme une classe Java ordinaire, instantanément. C'est la promesse clef du
 * MVVM : la logique d'affichage devient testable comme du code métier.
 */
class MessageViewModelTest {

  @Test
  void au_demarrage_le_texte_reflete_la_valeur_du_modele() {
    Message modele = new Message("Bonjour MVVM");
    MessageViewModel vm = new MessageViewModel(modele);

    assertThat(vm.texteProperty().get())
        .as("texteProperty doit être initialisé depuis le modèle")
        .isEqualTo("Bonjour MVVM");
  }

  @Test
  void modifier_la_propriete_met_a_jour_le_modele() {
    Message modele = new Message("Bonjour MVVM");
    MessageViewModel vm = new MessageViewModel(modele);

    vm.texteProperty().set("Chauve-souris");

    assertThat(modele.getTexte())
        .as("toute modification de la propriété doit être répercutée dans le modèle")
        .isEqualTo("Chauve-souris");
  }

  @Test
  void l_apercu_derive_automatiquement_du_texte() {
    MessageViewModel vm = new MessageViewModel(new Message(""));

    vm.texteProperty().set("Pipistrelle");

    assertThat(vm.apercuProperty().get())
        .as("apercuProperty doit être une vue dérivée du texte saisi")
        .isEqualTo("Aperçu : Pipistrelle");
  }
}
