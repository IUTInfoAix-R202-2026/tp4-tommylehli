package fr.univ_amu.iut.exercice4;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.univ_amu.iut.exercice3.ServiceAuth;
import fr.univ_amu.iut.exercice3.ServiceAuthSimple;
import org.junit.jupiter.api.Test;

/**
 * Test de l'exercice 4 : on vérifie que le module Guice câble correctement les dépendances. Guice
 * fonctionne sans interface graphique : ce test est un pur test JUnit.
 */
class AppModuleTest {

  @Test
  void le_module_lie_serviceauth_a_son_implementation_simple() {
    Injector injector = Guice.createInjector(new AppModule());

    ServiceAuth service = injector.getInstance(ServiceAuth.class);

    assertThat(service)
        .as("Guice doit fournir un ServiceAuthSimple quand on demande un ServiceAuth")
        .isInstanceOf(ServiceAuthSimple.class);
  }

  @Test
  void guice_construit_le_viewmodel_avec_son_service_injecte() {
    Injector injector = Guice.createInjector(new AppModule());

    ConnexionViewModel vm = injector.getInstance(ConnexionViewModel.class);

    assertThat(vm).as("Guice doit savoir construire le ViewModel").isNotNull();

    // Preuve que le service a bien été injecté : la connexion avec le compte
    // de démonstration passe par ServiceAuthSimple.
    vm.identifiantProperty().set("marie");
    vm.motDePasseProperty().set("chiro2026");
    vm.connecterCommand();

    assertThat(vm.statutProperty().get()).isEqualTo("Bienvenue marie !");
  }
}
