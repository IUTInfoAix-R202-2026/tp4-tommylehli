package fr.univ_amu.iut.exercice2;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Test de l'exercice 2, côté vue (TestFX) : le contrôleur relie le label au message du ViewModel et
 * fait suivre chaque clic vers la commande correspondante. La logique des commandes est déjà
 * couverte sans fenêtre par {@link CompteurViewModelTest}.
 *
 * <p>On déclenche les boutons via {@code fire()} (et non un clic souris simulé) : c'est la méthode
 * fiable en environnement sans portail d'entrée (Codespace, CI), et c'est aussi instantané.
 */
@ExtendWith(ApplicationExtension.class)
class CompteurControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    new CompteurApp().start(stage);
  }

  private Label label(FxRobot robot) {
    return robot.lookup("#labelCompteur").queryAs(Label.class);
  }

  private Button bouton(FxRobot robot, String texte) {
    return robot.lookup(texte).queryButton();
  }

  @Test
  void au_demarrage_le_label_affiche_zero(FxRobot robot) {
    assertThat(label(robot).getText())
        .as("le label doit être lié au message du ViewModel dès le chargement")
        .isEqualTo("Compteur à 0");
  }

  @Test
  void cliquer_sur_plus_incremente_le_label(FxRobot robot) {
    robot.interact(() -> bouton(robot, "+").fire());
    assertThat(label(robot).getText())
        .as("le bouton + doit appeler incrementerCommand")
        .isEqualTo("Compteur à 1");
  }

  @Test
  void cliquer_sur_moins_decremente_le_label(FxRobot robot) {
    robot.interact(() -> bouton(robot, "−").fire());
    assertThat(label(robot).getText())
        .as("le bouton − doit appeler decrementerCommand")
        .isEqualTo("Compteur à -1");
  }

  @Test
  void cliquer_sur_reinitialiser_remet_a_zero(FxRobot robot) {
    robot.interact(() -> bouton(robot, "+").fire());
    robot.interact(() -> bouton(robot, "+").fire());
    robot.interact(() -> bouton(robot, "Réinitialiser").fire());
    assertThat(label(robot).getText())
        .as("le bouton Réinitialiser doit appeler reinitialiserCommand")
        .isEqualTo("Compteur à 0");
  }
}
