package fr.univ_amu.iut.exercice3;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test de l'exercice 3.
 *
 * <p>On fournit au ViewModel un faux service d'authentification (un simple lambda) : c'est possible
 * parce que le ViewModel dépend de l'INTERFACE {@link ServiceAuth}, pas d'une implémentation. On
 * teste ainsi la logique de validation et de connexion sans aucune interface graphique.
 */
class FormulaireConnexionViewModelTest {

  @Test
  void le_formulaire_n_est_pas_validable_au_demarrage() {
    FormulaireConnexionViewModel vm =
        new FormulaireConnexionViewModel((identifiant, motDePasse) -> true);

    assertThat(vm.validableProperty().get())
        .as("les deux champs sont vides : le formulaire ne doit pas être validable")
        .isFalse();
  }

  @Test
  void le_formulaire_devient_validable_quand_les_deux_champs_sont_remplis() {
    FormulaireConnexionViewModel vm =
        new FormulaireConnexionViewModel((identifiant, motDePasse) -> true);

    vm.identifiantProperty().set("marie");
    assertThat(vm.validableProperty().get()).as("un seul champ rempli ne suffit pas").isFalse();

    vm.motDePasseProperty().set("chiro2026");
    assertThat(vm.validableProperty().get())
        .as("les deux champs remplis : le formulaire est validable")
        .isTrue();
  }

  @Test
  void une_connexion_reussie_affiche_un_message_de_bienvenue() {
    FormulaireConnexionViewModel vm =
        new FormulaireConnexionViewModel((identifiant, motDePasse) -> true);
    vm.identifiantProperty().set("marie");
    vm.motDePasseProperty().set("chiro2026");

    vm.connecterCommand();

    assertThat(vm.statutProperty().get()).isEqualTo("Bienvenue marie !");
  }

  @Test
  void une_connexion_refusee_affiche_un_message_d_erreur() {
    FormulaireConnexionViewModel vm =
        new FormulaireConnexionViewModel((identifiant, motDePasse) -> false);
    vm.identifiantProperty().set("marie");
    vm.motDePasseProperty().set("mauvais");

    vm.connecterCommand();

    assertThat(vm.statutProperty().get())
        .as("le message d'échec doit être clair et en langage humain (Nielsen #9)")
        .isEqualTo("Identifiants incorrects. Vérifiez votre saisie.");
  }

  @Test
  void le_service_simple_reconnait_le_compte_de_demonstration() {
    ServiceAuth service = new ServiceAuthSimple();

    assertThat(service.connecter("marie", "chiro2026")).isTrue();
    assertThat(service.connecter("marie", "faux")).isFalse();
  }
}
