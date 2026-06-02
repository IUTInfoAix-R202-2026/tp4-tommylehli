package fr.univ_amu.iut.exercice7;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Test du capstone, côté vue (TestFX) : on vérifie que le contrôleur câble bien la TableView, la
 * sélection, le bouton Écouter, la ChoiceBox de verdict et les libellés.
 */
@ExtendWith(ApplicationExtension.class)
class QualificationControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null);
    new Qualification().start(stage);
  }

  @SuppressWarnings("unchecked")
  private TableView<Sequence> table(FxRobot robot) {
    return robot.lookup("#tableSequences").queryAs(TableView.class);
  }

  @Test
  void la_table_affiche_les_dix_sequences(FxRobot robot) {
    assertThat(table(robot).getItems()).as("la TableView doit être peuplée").hasSize(10);
  }

  @Test
  void le_bouton_ecouter_est_desactive_sans_selection(FxRobot robot) {
    Button ecouter = robot.lookup("#boutonEcouter").queryAs(Button.class);
    assertThat(ecouter.isDisabled()).as("rien n'est sélectionné au démarrage").isTrue();
  }

  @Test
  void selectionner_une_ligne_active_le_bouton_et_decrit_la_selection(FxRobot robot) {
    robot.interact(() -> table(robot).getSelectionModel().select(0));

    Button ecouter = robot.lookup("#boutonEcouter").queryAs(Button.class);
    Label selection = robot.lookup("#labelSelection").queryAs(Label.class);

    assertThat(ecouter.isDisabled()).isFalse();
    assertThat(selection.getText()).startsWith("Séquence 20:00 - ");
  }

  @Test
  void ecouter_passe_la_sequence_au_statut_ecoutee(FxRobot robot) {
    robot.interact(() -> table(robot).getSelectionModel().select(0));
    Button ecouter = robot.lookup("#boutonEcouter").queryAs(Button.class);

    robot.interact(ecouter::fire);

    assertThat(table(robot).getItems().get(0).getStatut()).isEqualTo("Écoutée");
  }

  @Test
  @SuppressWarnings("unchecked")
  void la_choicebox_propose_les_trois_verdicts(FxRobot robot) {
    ChoiceBox<String> choix = robot.lookup("#choiceVerdict").queryAs(ChoiceBox.class);
    assertThat(choix.getItems()).containsExactly("OK", "Douteux", "À jeter");
  }

  @Test
  @SuppressWarnings("unchecked")
  void enregistrer_un_verdict_via_la_vue_met_a_jour_le_libelle(FxRobot robot) {
    ChoiceBox<String> choix = robot.lookup("#choiceVerdict").queryAs(ChoiceBox.class);
    Button enregistrer = robot.lookup("#boutonEnregistrer").queryAs(Button.class);

    robot.interact(() -> choix.setValue("OK"));
    robot.interact(enregistrer::fire);

    Label verdict = robot.lookup("#labelVerdictGlobal").queryAs(Label.class);
    assertThat(verdict.getText()).isEqualTo("Verdict global : OK");
  }
}
