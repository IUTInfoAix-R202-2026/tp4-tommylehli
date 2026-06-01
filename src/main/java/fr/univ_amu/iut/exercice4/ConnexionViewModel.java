package fr.univ_amu.iut.exercice4;

import com.google.inject.Inject;
import fr.univ_amu.iut.exercice3.ServiceAuth;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel de l'exercice 4 : même logique que le formulaire de l'exercice 3, mais le service est
 * désormais <b>injecté par Guice</b>.
 *
 * <p>La seule différence visible avec l'exercice 3 est l'annotation {@code @Inject} sur le
 * constructeur : elle indique à Guice "pour construire ce ViewModel, fournis-moi un ServiceAuth".
 * Guice résout cette dépendance grâce au binding déclaré dans {@link AppModule}.
 *
 * <p>Le ViewModel reste totalement ignorant de Guice au-delà de cette annotation : il ne crée aucun
 * service, ne connaît aucune implémentation concrète. Il est donc toujours testable directement (en
 * lui passant un faux service au constructeur), comme à l'exercice 3.
 */
public class ConnexionViewModel {

  private final ServiceAuth serviceAuth;

  private final StringProperty identifiant = new SimpleStringProperty("");
  private final StringProperty motDePasse = new SimpleStringProperty("");
  private final StringProperty statut = new SimpleStringProperty("");
  private final BooleanProperty validable = new SimpleBooleanProperty(false);

  @Inject
  // TODO exercice 4 : annoter ce constructeur avec @Inject pour que Guice
  // sache l'utiliser et lui fournir le ServiceAuth.
  public ConnexionViewModel(ServiceAuth serviceAuth) {
    this.serviceAuth = serviceAuth;
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

  public void connecterCommand() {
    statut.set("Connexion en cours...");
    boolean ok = serviceAuth.connecter(identifiant.get(), motDePasse.get());
    if (ok) {
      statut.set("Bienvenue " + identifiant.get() + " !");
    } else {
      statut.set("Identifiants incorrects. Vérifiez votre saisie.");
    }
  }
}
