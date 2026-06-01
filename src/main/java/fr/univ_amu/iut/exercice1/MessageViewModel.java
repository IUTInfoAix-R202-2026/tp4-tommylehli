package fr.univ_amu.iut.exercice1;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel de l'exercice 1.
 *
 * <p>Le ViewModel est le cœur du pattern MVVM. Il expose l'état de l'interface sous forme de
 * <b>propriétés observables</b> ({@link StringProperty}, etc.) et fait le pont avec le {@link
 * Message modèle}. Point capital : il n'importe AUCUN composant JavaFX d'interface (pas de {@code
 * Label}, pas de {@code TextField}). Il est donc testable avec un simple test JUnit, sans lancer
 * d'application graphique.
 *
 * <p>Ici, le ViewModel :
 *
 * <ul>
 *   <li>expose {@code texte} (lecture/écriture) que la vue liera à un champ de saisie ;
 *   <li>maintient le modèle synchronisé : toute modification de la propriété est répercutée dans le
 *       {@link Message} ;
 *   <li>expose {@code apercu} (lecture seule), une propriété DÉRIVÉE qui se recalcule toute seule.
 * </ul>
 */
public class MessageViewModel {

  private final Message message;

  private final StringProperty texte = new SimpleStringProperty();
  private final StringProperty apercu = new SimpleStringProperty();

  public MessageViewModel(Message message) {
    this.message = message;

    // TODO exercice 1 : câbler le ViewModel.
    //
    // 1. Initialiser la propriété `texte` avec la valeur actuelle du modèle
    //    (message.getTexte()).
    // 2. Quand `texte` change, recopier la nouvelle valeur dans le modèle
    //    (message.setTexte(...)) : c'est ce qui garde le modèle à jour.
    // 3. Lier `apercu` (lecture seule pour la vue) à une version dérivée de
    //    `texte` : le texte saisi précédé de la mention "Aperçu : ".
    //    Astuce : Bindings.concat("Aperçu : ", texte).
    texte.set(message.getTexte());
    texte.addListener(
        (observable, oldValue, newValue) -> {
          message.setTexte(newValue);
        });
    apercu.bind(Bindings.concat("Aperçu : ", texte));
  }

  public StringProperty texteProperty() {
    return texte;
  }

  public StringProperty apercuProperty() {
    return apercu;
  }
}
