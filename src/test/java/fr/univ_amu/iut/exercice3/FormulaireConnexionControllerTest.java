package fr.univ_amu.iut.exercice3;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Test de l'exercice 3, côté vue (TestFX) : le contrôleur lie les deux champs au ViewModel,
 * désactive le bouton tant que le formulaire n'est pas validable, relaie le clic vers la commande
 * et affiche le statut. La logique de validation et de connexion est déjà couverte sans fenêtre par
 * {@link FormulaireConnexionViewModelTest}.
 */
@ExtendWith(ApplicationExtension.class)
class FormulaireConnexionControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    new FormulaireConnexionApp().start(stage);
  }

  @Test
  void au_demarrage_le_bouton_est_desactive(FxRobot robot) {
    Button valider = robot.lookup("#boutonValider").queryAs(Button.class);
    assertThat(valider.isDisabled())
        .as("les champs sont vides : le bouton doit être désactivé (disableProperty)")
        .isTrue();
  }

  @Test
  void remplir_les_deux_champs_active_le_bouton(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);

    robot.interact(() -> identifiant.setText("marie"));
    robot.interact(() -> motDePasse.setText("chiro2026"));

    Button valider = robot.lookup("#boutonValider").queryAs(Button.class);
    assertThat(valider.isDisabled())
        .as("les deux champs sont reliés au ViewModel : remplis, le bouton s'active")
        .isFalse();
  }

  @Test
  void se_connecter_affiche_le_message_de_bienvenue(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    robot.interact(() -> identifiant.setText("marie"));
    robot.interact(() -> motDePasse.setText("chiro2026"));

    Button valider = robot.lookup("#boutonValider").queryAs(Button.class);
    robot.interact(valider::fire);

    Label statut = robot.lookup("#labelStatut").queryAs(Label.class);
    assertThat(statut.getText())
        .as("le clic doit déclencher connecterCommand et le statut s'afficher")
        .isEqualTo("Bienvenue marie !");
  }
}
