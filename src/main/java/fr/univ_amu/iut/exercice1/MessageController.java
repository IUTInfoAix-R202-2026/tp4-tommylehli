package fr.univ_amu.iut.exercice1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Contrôleur de vue de l'exercice 1.
 *
 * <p>En MVVM, le contrôleur de vue est très <b>mince</b> : il ne contient aucune logique métier ni
 * d'affichage. Son unique rôle est de <b>câbler</b> les composants de la vue (FXML) aux propriétés
 * du {@link MessageViewModel}. On dit qu'il "branche les fils".
 *
 * <p>Le ViewModel est fourni par le constructeur. Pour l'instant (exercice 1), c'est la classe
 * {@link MessageApp} qui construit le ViewModel et le passe à la main via {@code
 * setControllerFactory}. À l'exercice 4, Guice fera ce travail automatiquement.
 */
public class MessageController {

  private final MessageViewModel viewModel;

  @FXML private TextField champTexte;
  @FXML private Label labelApercu;

  public MessageController(MessageViewModel viewModel) {
    this.viewModel = viewModel;
  }

  @FXML
  private void initialize() {
    // TODO exercice 1 : brancher la vue sur le ViewModel.
    //
    // - Liaison BIDIRECTIONNELLE entre le champ de saisie et texteProperty()
    //   (ce que tape l'utilisateur va dans le ViewModel, et inversement) :
    //     champTexte.textProperty().bindBidirectional(viewModel.texteProperty());
    // - Liaison SIMPLE (sens unique) de l'apercu : il se contente d'afficher
    //   ce que le ViewModel calcule :
    //     labelApercu.textProperty().bind(viewModel.apercuProperty());
    champTexte.textProperty().bindBidirectional(viewModel.texteProperty());
    labelApercu.textProperty().bind(viewModel.apercuProperty());
  }
}
