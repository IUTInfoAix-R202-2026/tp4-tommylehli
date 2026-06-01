package fr.univ_amu.iut.exercice3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Contrôleur de vue de l'exercice 3.
 *
 * <p>Le contrôleur ne contient toujours aucune logique : il lie les champs aux propriétés du
 * ViewModel (bidirectionnel), lie le statut (sens unique), désactive le bouton tant que le
 * formulaire n'est pas validable, et fait suivre le clic vers la commande.
 */
public class FormulaireConnexionController {

  private final FormulaireConnexionViewModel viewModel;

  @FXML private TextField champIdentifiant;
  @FXML private PasswordField champMotDePasse;
  @FXML private Button boutonValider;
  @FXML private Label labelStatut;

  public FormulaireConnexionController(FormulaireConnexionViewModel viewModel) {
    this.viewModel = viewModel;
  }

  @FXML
  private void initialize() {
    // TODO exercice 3 : brancher la vue sur le ViewModel.
    //
    // - champIdentifiant <-> identifiantProperty (bidirectionnel)
    // - champMotDePasse  <-> motDePasseProperty  (bidirectionnel)
    // - labelStatut      <-  statutProperty      (sens unique)
    // - boutonValider désactivé tant que le formulaire n'est pas validable :
    //     boutonValider.disableProperty().bind(viewModel.validableProperty().not());
    champIdentifiant.textProperty().bindBidirectional(viewModel.identifiantProperty());
    champMotDePasse.textProperty().bindBidirectional(viewModel.motDePasseProperty());
    labelStatut.textProperty().bind(viewModel.statutProperty());
    boutonValider.disableProperty().bind(viewModel.validableProperty().not());
  }

  @FXML
  private void surValider() {
    // TODO exercice 3 : déclencher la commande de connexion.
    viewModel.connecterCommand();
  }
}
