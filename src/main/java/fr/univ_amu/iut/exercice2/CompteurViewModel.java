package fr.univ_amu.iut.exercice2;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel de l'exercice 2.
 *
 * <p>Cet exercice illustre deux idées clefs du MVVM :
 *
 * <ol>
 *   <li>une propriété d'AFFICHAGE dérivée ({@code message}) : le ViewModel transforme la donnée
 *       brute du modèle (un entier) en texte prêt à afficher ;
 *   <li>le pattern <b>Command</b> : chaque action de l'interface (clic sur un bouton) correspond à
 *       une méthode publique sans paramètre du ViewModel ({@code incrementerCommand}, etc.).
 * </ol>
 *
 * <p>Le ViewModel reste testable sans interface : voir {@code CompteurViewModelTest}.
 */
public class CompteurViewModel {

  private final Compteur compteur;

  private final StringProperty message = new SimpleStringProperty();

  public CompteurViewModel(Compteur compteur) {
    this.compteur = compteur;

    // TODO exercice 2 : lier `message` à la valeur du compteur.
    //
    // `message` doit afficher "Compteur à N" où N est la valeur courante,
    // et se mettre à jour tout seul quand le compteur change.
    // Astuce : Bindings.concat("Compteur à ", compteur.valeurProperty()).
    message.bind(Bindings.concat("Compteur à ", compteur.valeurProperty()));
  }

  public StringProperty messageProperty() {
    return message;
  }

  // ----- Commandes (pattern Command) -----

  public void incrementerCommand() {
    // TODO exercice 2 : déléguer au modèle.
    compteur.incrementer();
  }

  public void decrementerCommand() {
    // TODO exercice 2 : déléguer au modèle.
    compteur.decrementer();
  }

  public void reinitialiserCommand() {
    // TODO exercice 2 : déléguer au modèle.
    compteur.reinitialiser();
  }
}
