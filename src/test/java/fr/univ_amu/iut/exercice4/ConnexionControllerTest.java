package fr.univ_amu.iut.exercice4;

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
 * Test de l'exercice 4, côté vue (TestFX) : on lance l'application réelle (ConnexionApp). Si elle
 * démarre et que la connexion fonctionne, c'est que toute la chaîne d'injection a été câblée par
 * Guice : l'injecteur est créé, le controllerFactory construit le contrôleur, son {@code @Inject}
 * reçoit le ViewModel, qui reçoit lui-même le ServiceAuth. Le module et le {@code @Inject} du
 * ViewModel sont par ailleurs couverts sans fenêtre par {@link AppModuleTest}.
 */
@ExtendWith(ApplicationExtension.class)
class ConnexionControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    new ConnexionApp().start(stage);
  }

  private void saisir(FxRobot robot, String id, String mdp) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    robot.interact(() -> identifiant.setText(id));
    robot.interact(() -> motDePasse.setText(mdp));
  }

  private String statut(FxRobot robot) {
    return robot.lookup("#labelStatut").queryAs(Label.class).getText();
  }

  @Test
  void la_fenetre_s_ouvre_avec_le_formulaire(FxRobot robot) {
    assertThat(robot.lookup("#champIdentifiant").tryQuery())
        .as("le bootstrap Guice doit charger et afficher la vue")
        .isPresent();
    assertThat(robot.lookup("#boutonValider").tryQuery()).isPresent();
  }

  @Test
  void au_demarrage_le_bouton_est_desactive(FxRobot robot) {
    Button valider = robot.lookup("#boutonValider").queryAs(Button.class);
    assertThat(valider.isDisabled())
        .as("le ViewModel injecté pilote disableProperty : champs vides => bouton désactivé")
        .isTrue();
  }

  @Test
  void se_connecter_via_guice_affiche_la_bienvenue(FxRobot robot) {
    saisir(robot, "marie", "chiro2026");
    Button valider = robot.lookup("#boutonValider").queryAs(Button.class);
    robot.interact(valider::fire);
    assertThat(statut(robot))
        .as("toute la chaîne Contrôleur → ViewModel → ServiceAuth doit être câblée par Guice")
        .isEqualTo("Bienvenue marie !");
  }

  @Test
  void un_mauvais_mot_de_passe_affiche_l_erreur(FxRobot robot) {
    saisir(robot, "marie", "mauvais");
    Button valider = robot.lookup("#boutonValider").queryAs(Button.class);
    robot.interact(valider::fire);
    assertThat(statut(robot))
        .as("le vrai ServiceAuthSimple est injecté : un mauvais mot de passe est refusé")
        .isEqualTo("Identifiants incorrects. Vérifiez votre saisie.");
  }
}
