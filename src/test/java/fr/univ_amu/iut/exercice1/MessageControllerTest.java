package fr.univ_amu.iut.exercice1;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Test de l'exercice 1, côté vue (TestFX) : on vérifie que le contrôleur câble bien le champ de
 * saisie et le label d'aperçu sur le ViewModel. La logique est déjà couverte sans fenêtre par
 * {@link MessageViewModelTest} ; ici on ne teste QUE le branchement de la vue.
 */
@ExtendWith(ApplicationExtension.class)
class MessageControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    new MessageApp().start(stage);
  }

  @Test
  void au_demarrage_l_apercu_reflete_le_texte_initial(FxRobot robot) {
    Label apercu = robot.lookup("#labelApercu").queryAs(Label.class);
    assertThat(apercu.getText())
        .as("le label doit être lié à apercuProperty() dès le chargement")
        .isEqualTo("Aperçu : Bonjour MVVM");
  }

  @Test
  void saisir_dans_le_champ_met_a_jour_l_apercu(FxRobot robot) {
    TextField champ = robot.lookup("#champTexte").queryAs(TextField.class);

    robot.interact(() -> champ.setText("Pipistrelle"));

    Label apercu = robot.lookup("#labelApercu").queryAs(Label.class);
    assertThat(apercu.getText())
        .as("taper dans le champ doit se propager au ViewModel puis à l'aperçu")
        .isEqualTo("Aperçu : Pipistrelle");
  }
}
