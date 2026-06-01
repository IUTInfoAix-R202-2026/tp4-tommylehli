package fr.univ_amu.iut.exercice4;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'exercice 4.
 *
 * <p>Comparez avec l'exercice 3 : il n'y a plus de {@code new ServiceAuthSimple()} ni de {@code new
 * ConnexionViewModel(...)} à la main. On crée un {@link Injector} à partir du module, puis on dit à
 * {@link FXMLLoader} d'utiliser Guice comme fabrique de contrôleurs ({@code setControllerFactory}).
 * Toute la chaîne de dépendances (Contrôleur -> ViewModel -> ServiceAuth) est câblée
 * automatiquement.
 */
public class ConnexionApp extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Exercice 4 - Connexion avec Guice");

    // TODO exercice 4 : créer l'injecteur Guice à partir du module, puis
    // brancher FXMLLoader dessus, charger la vue et l'afficher.
    //
    // 1. Injector injector = Guice.createInjector(new AppModule());
    // 2. FXMLLoader loader = new FXMLLoader(getClass().getResource("ConnexionView.fxml"));
    // 3. loader.setControllerFactory(injector::getInstance);
    //    -> tous les contrôleurs FXML seront instanciés par Guice et
    //       recevront leurs @Inject.
    // 4. Parent racine = loader.load();
    //    stage.setScene(new Scene(racine));
    //    stage.show();
    Injector injector = Guice.createInjector(new AppModule());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ConnexionView.fxml"));
    loader.setControllerFactory(injector::getInstance);
    Parent racine = loader.load();
    stage.setScene(new Scene(racine));
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
