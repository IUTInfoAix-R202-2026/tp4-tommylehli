package fr.univ_amu.iut.exercice5;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Test de l'exercice 5, côté vue (TestFX) : le contrôleur abonne la TableView à la liste
 * observable, configure les colonnes (cell value factories), lie le résumé et câble le formulaire
 * d'ajout. La logique (remplissage, résumé dérivé, recherche) est déjà couverte sans fenêtre par
 * {@link PokemonViewModelTest}.
 */
@ExtendWith(ApplicationExtension.class)
class PokemonControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    new PokemonApp().start(stage);
  }

  @SuppressWarnings("unchecked")
  private TableView<Pokemon> table(FxRobot robot) {
    return robot.lookup("#table").queryAs(TableView.class);
  }

  @Test
  void au_demarrage_la_table_affiche_les_pokemon_de_depart(FxRobot robot) {
    assertThat(table(robot).getItems())
        .as("la TableView doit être abonnée à la liste observable du ViewModel")
        .hasSize(6);
  }

  @Test
  void les_colonnes_affichent_les_donnees_du_premier_pokemon(FxRobot robot) {
    TableView<Pokemon> t = table(robot);
    assertThat(t.getColumns().get(0).getCellData(0)).as("colonne numéro").isEqualTo("1");
    assertThat(t.getColumns().get(1).getCellData(0)).as("colonne nom").isEqualTo("Bulbizarre");
    assertThat(t.getColumns().get(2).getCellData(0)).as("colonne type").isEqualTo("Plante");
  }

  @Test
  void le_label_resume_affiche_le_compte(FxRobot robot) {
    Label resume = robot.lookup("#labelResume").queryAs(Label.class);
    assertThat(resume.getText()).isEqualTo("6 Pokémon");
  }

  @Test
  void le_bouton_ajouter_est_desactive_sans_saisie(FxRobot robot) {
    Button ajouter = robot.lookup("#boutonAjouter").queryAs(Button.class);
    assertThat(ajouter.isDisabled()).as("rien n'est saisi au démarrage").isTrue();
  }

  @Test
  void ajouter_un_pokemon_par_son_nom_ajoute_une_ligne(FxRobot robot) {
    TextField champ = robot.lookup("#champRecherche").queryAs(TextField.class);
    robot.interact(() -> champ.setText("Dracaufeu"));

    Button ajouter = robot.lookup("#boutonAjouter").queryAs(Button.class);
    robot.interact(ajouter::fire);

    assertThat(table(robot).getItems()).as("une ligne a été ajoutée").hasSize(7);
    Label resume = robot.lookup("#labelResume").queryAs(Label.class);
    assertThat(resume.getText()).isEqualTo("7 Pokémon");
    assertThat(champ.getText())
        .as("le champ se vide après un ajout réussi (liaison bidirectionnelle VM -> vue)")
        .isEmpty();
  }

  @Test
  void remplir_le_champ_active_le_bouton(FxRobot robot) {
    Button ajouter = robot.lookup("#boutonAjouter").queryAs(Button.class);
    assertThat(ajouter.isDisabled()).as("désactivé tant que le champ est vide").isTrue();

    TextField champ = robot.lookup("#champRecherche").queryAs(TextField.class);
    robot.interact(() -> champ.setText("Dracaufeu"));

    assertThat(ajouter.isDisabled())
        .as("le bouton s'active dès qu'un nom est saisi (binding réactif sur la recherche)")
        .isFalse();
  }

  @Test
  void un_nom_inconnu_affiche_un_statut_sans_ajouter(FxRobot robot) {
    TextField champ = robot.lookup("#champRecherche").queryAs(TextField.class);
    robot.interact(() -> champ.setText("Pikachose"));

    Button ajouter = robot.lookup("#boutonAjouter").queryAs(Button.class);
    robot.interact(ajouter::fire);

    assertThat(table(robot).getItems()).hasSize(6);
    Label statut = robot.lookup("#labelStatut").queryAs(Label.class);
    assertThat(statut.getText()).as("un statut d'erreur doit s'afficher").isNotEmpty();
  }
}
