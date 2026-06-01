package fr.univ_amu.iut.exercice2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Contrôleur de vue de l'exercice 2.
 *
 * <p>Encore une fois, le contrôleur est un simple câblage : il lie le label au {@code message} du
 * ViewModel, et fait suivre les clics de boutons vers les commandes correspondantes. Aucune logique
 * ici : le "quoi faire" vit dans le ViewModel, le "comment l'afficher" dans le FXML.
 */
public class CompteurController {

  private final CompteurViewModel viewModel;

  @FXML private Label labelCompteur;

  public CompteurController(CompteurViewModel viewModel) {
    this.viewModel = viewModel;
  }

  @FXML
  private void initialize() {
    // TODO exercice 2 : lier le texte du label au message du ViewModel.
    labelCompteur.textProperty().bind(viewModel.messageProperty());
  }

  @FXML
  private void surIncrementer() {
    // TODO exercice 2 : appeler la commande du ViewModel.
    viewModel.incrementerCommand();
  }

  @FXML
  private void surDecrementer() {
    // TODO exercice 2 : appeler la commande du ViewModel.
    viewModel.decrementerCommand();
  }

  @FXML
  private void surReinitialiser() {
    // TODO exercice 2 : appeler la commande du ViewModel.
    viewModel.reinitialiserCommand();
  }
}
