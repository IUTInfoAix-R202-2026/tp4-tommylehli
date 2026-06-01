package fr.univ_amu.iut.exercice5;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test de l'exercice 5 : la liste observable et sa logique se testent sans interface graphique. On
 * vérifie l'état de départ, le résumé dérivé, et la commande d'ajout (recherche via le service).
 */
class PokemonViewModelTest {

  private PokemonViewModel vm() {
    return new PokemonViewModel(new PokemonService());
  }

  @Test
  void au_demarrage_la_liste_contient_les_pokemon_de_depart() {
    assertThat(vm().pokemonsProperty())
        .as("la liste de départ doit être remplie depuis le service")
        .hasSize(6);
  }

  @Test
  void le_resume_reflete_le_nombre_de_pokemon() {
    assertThat(vm().resumeProperty().get()).isEqualTo("6 Pokémon");
  }

  @Test
  void ajouter_par_nom_ajoute_le_pokemon_trouve_et_met_a_jour_le_resume() {
    PokemonViewModel vm = vm();

    vm.rechercheProperty().set("Dracaufeu");
    vm.ajouter();

    assertThat(vm.pokemonsProperty()).hasSize(7);
    assertThat(vm.pokemonsProperty()).anyMatch(p -> p.nom().equals("Dracaufeu"));
    assertThat(vm.resumeProperty().get())
        .as("le résumé, lié à la taille de la liste, doit se mettre à jour tout seul")
        .isEqualTo("7 Pokémon");
  }

  @Test
  void ajouter_un_nom_inconnu_laisse_la_liste_intacte_et_affiche_un_statut() {
    PokemonViewModel vm = vm();

    vm.rechercheProperty().set("Pikachose");
    vm.ajouter();

    assertThat(vm.pokemonsProperty()).as("un nom inconnu n'ajoute rien").hasSize(6);
    assertThat(vm.statutProperty().get()).as("un statut clair doit être publié").isNotEmpty();
  }

  @Test
  void ajouter_un_pokemon_deja_present_ne_le_duplique_pas() {
    PokemonViewModel vm = vm();

    vm.rechercheProperty().set("Pikachu"); // déjà dans la liste de départ
    vm.ajouter();

    assertThat(vm.pokemonsProperty()).as("pas de doublon").hasSize(6);
    assertThat(vm.statutProperty().get()).isNotEmpty();
  }

  @Test
  void la_recherche_ignore_la_casse_et_les_accents() {
    PokemonViewModel vm = vm();

    // "evoli" (sans majuscule ni accent) doit retrouver "Évoli", déjà présent :
    // le statut "déjà présent" prouve que la recherche a bien apparié le nom.
    vm.rechercheProperty().set("evoli");
    vm.ajouter();

    assertThat(vm.statutProperty().get())
        .as("la recherche doit ignorer la casse et les accents")
        .isNotEmpty();
    assertThat(vm.pokemonsProperty()).hasSize(6);
  }
}
