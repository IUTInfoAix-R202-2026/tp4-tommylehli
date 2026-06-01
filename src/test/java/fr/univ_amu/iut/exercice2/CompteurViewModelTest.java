package fr.univ_amu.iut.exercice2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test de l'exercice 2 : la logique du compteur se vérifie intégralement sans interface graphique.
 * Chaque commande est appelée directement sur le ViewModel et on observe le {@code message}.
 */
class CompteurViewModelTest {

  @Test
  void au_demarrage_le_message_affiche_zero() {
    CompteurViewModel vm = new CompteurViewModel(new Compteur());

    assertThat(vm.messageProperty().get())
        .as("au démarrage le compteur vaut 0")
        .isEqualTo("Compteur à 0");
  }

  @Test
  void incrementer_augmente_le_compteur_de_un() {
    CompteurViewModel vm = new CompteurViewModel(new Compteur());

    vm.incrementerCommand();

    assertThat(vm.messageProperty().get()).isEqualTo("Compteur à 1");
  }

  @Test
  void plusieurs_increments_se_cumulent() {
    CompteurViewModel vm = new CompteurViewModel(new Compteur());

    vm.incrementerCommand();
    vm.incrementerCommand();
    vm.incrementerCommand();

    assertThat(vm.messageProperty().get()).isEqualTo("Compteur à 3");
  }

  @Test
  void decrementer_diminue_le_compteur() {
    CompteurViewModel vm = new CompteurViewModel(new Compteur());

    vm.incrementerCommand();
    vm.decrementerCommand();
    vm.decrementerCommand();

    assertThat(vm.messageProperty().get()).isEqualTo("Compteur à -1");
  }

  @Test
  void reinitialiser_remet_le_compteur_a_zero() {
    CompteurViewModel vm = new CompteurViewModel(new Compteur());

    vm.incrementerCommand();
    vm.incrementerCommand();
    vm.reinitialiserCommand();

    assertThat(vm.messageProperty().get()).isEqualTo("Compteur à 0");
  }
}
