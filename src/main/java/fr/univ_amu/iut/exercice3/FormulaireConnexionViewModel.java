package fr.univ_amu.iut.exercice3;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel de l'exercice 3 : le formulaire de connexion de VigieChiro PR Companion.
 *
 * <p>Cet exercice rassemble plusieurs apports du module :
 *
 * <ul>
 *   <li><b>validation réactive</b> : le formulaire n'est "validable" que si les deux champs sont
 *       remplis. La propriété {@code validable} se recalcule à chaque frappe (affordance, Nielsen
 *       #5 : on empêche l'erreur en désactivant le bouton) ;
 *   <li>le pattern <b>Command</b> avec gestion d'erreur : {@code connecterCommand} ne laisse jamais
 *       remonter d'exception vers l'interface ; il publie l'état via {@code statut} ;
 *   <li>la <b>dépendance à une interface</b> ({@link ServiceAuth}) plutôt qu'à une implémentation :
 *       c'est ce découplage qui rend le ViewModel testable (avec un faux service) et que Guice
 *       automatisera à l'exercice 4.
 * </ul>
 */
public class FormulaireConnexionViewModel {

  private final ServiceAuth serviceAuth;

  private final StringProperty identifiant = new SimpleStringProperty("");
  private final StringProperty motDePasse = new SimpleStringProperty("");
  private final StringProperty statut = new SimpleStringProperty("");
  private final BooleanProperty validable = new SimpleBooleanProperty(false);

  public FormulaireConnexionViewModel(ServiceAuth serviceAuth) {
    this.serviceAuth = serviceAuth;

    // TODO exercice 3 : rendre le formulaire "validable" uniquement quand
    // l'identifiant ET le mot de passe sont non vides.
    //
    // Astuce : validable.bind(identifiant.isNotEmpty().and(motDePasse.isNotEmpty()));
    validable.bind(identifiant.isNotEmpty().and(motDePasse.isNotEmpty()));
  }

  public StringProperty identifiantProperty() {
    return identifiant;
  }

  public StringProperty motDePasseProperty() {
    return motDePasse;
  }

  public StringProperty statutProperty() {
    return statut;
  }

  public BooleanProperty validableProperty() {
    return validable;
  }

  /**
   * Commande de connexion. Met à jour {@code statut} selon le résultat. Ne lève jamais d'exception
   * vers l'appelant : c'est l'interface qui doit rester maîtresse de l'affichage.
   */
  public void connecterCommand() {
    // TODO exercice 3 : implémenter la commande de connexion.
    //
    // 1. Publier "Connexion en cours..." dans statut.
    // 2. Demander au serviceAuth de connecter identifiant + motDePasse.
    // 3. Selon le résultat, publier un message clair dans statut :
    //    - succès : "Bienvenue " + identifiant + " !"
    //    - échec  : "Identifiants incorrects. Vérifiez votre saisie."
    statut.set("Connexion en cours...");
    boolean estConnecte = serviceAuth.connecter(identifiant.get(), motDePasse.get());
    if (estConnecte) statut.set("Bienvenue " + identifiant.get() + " !");
    else statut.set("Identifiants incorrects. Vérifiez votre saisie.");
  }
}
