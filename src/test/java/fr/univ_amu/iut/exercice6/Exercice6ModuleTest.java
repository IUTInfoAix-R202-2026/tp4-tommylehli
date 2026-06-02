package fr.univ_amu.iut.exercice6;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import org.junit.jupiter.api.Test;

/**
 * Test de l'exercice 6 : les mécanismes avancés de Guice se vérifient sans interface graphique.
 *
 * <ul>
 *   <li>scope singleton : une instance unique partagée ;
 *   <li>{@code @Named} : le nom choisit l'implémentation ;
 *   <li>module de test : on remplace une dépendance par un mock Mockito, sans toucher au code
 *       applicatif.
 * </ul>
 */
class Exercice6ModuleTest {

  @Test
  void le_journal_est_un_singleton_partage() {
    Injector injector = Guice.createInjector(new Exercice6Module());

    JournalActivite premier = injector.getInstance(JournalActivite.class);
    JournalActivite second = injector.getInstance(JournalActivite.class);

    assertThat(premier)
        .as("@Singleton : Guice doit fournir la MÊME instance à chaque demande")
        .isSameAs(second);
  }

  @Test
  void le_nom_choisit_l_implementation_du_notifieur() {
    Injector injector = Guice.createInjector(new Exercice6Module());

    Notifieur console = injector.getInstance(Key.get(Notifieur.class, Names.named("console")));
    Notifieur silencieux =
        injector.getInstance(Key.get(Notifieur.class, Names.named("silencieux")));

    assertThat(console).isInstanceOf(NotifieurConsole.class);
    assertThat(silencieux).isInstanceOf(NotifieurSilencieux.class);
  }

  @Test
  void un_module_de_test_peut_injecter_un_mock() {
    Notifieur mockNotifieur = mock(Notifieur.class);

    // Modules.override remplace, le temps du test, le notifieur "console" par
    // notre mock. Aucune ligne du code applicatif n'est modifiée.
    Injector injector =
        Guice.createInjector(
            Modules.override(new Exercice6Module())
                .with(
                    new AbstractModule() {
                      @Override
                      protected void configure() {
                        bind(Notifieur.class)
                            .annotatedWith(Names.named("console"))
                            .toInstance(mockNotifieur);
                      }
                    }));

    ServiceSurveillance service = injector.getInstance(ServiceSurveillance.class);
    service.signaler("Pipistrelle detectee");

    verify(mockNotifieur).notifier("Pipistrelle detectee");
    assertThat(injector.getInstance(JournalActivite.class).evenements())
        .as("l'événement doit aussi avoir été enregistré dans le journal partagé")
        .contains("Pipistrelle detectee");
  }

  @Test
  void le_service_surveillance_se_construit_avec_le_notifieur_nomme() {
    // Sans le bind @Named("console") du module, Guice ne sait pas injecter le
    // paramètre du constructeur de ServiceSurveillance : la construction échoue.
    Injector injector = Guice.createInjector(new Exercice6Module());

    ServiceSurveillance service = injector.getInstance(ServiceSurveillance.class);
    service.signaler("Pipistrelle detectee");

    assertThat(injector.getInstance(JournalActivite.class).evenements())
        .as("le service construit via le module nommé doit partager le journal singleton")
        .contains("Pipistrelle detectee");
  }

  @Test
  void le_journal_singleton_cumule_les_evenements_de_plusieurs_services() {
    Injector injector = Guice.createInjector(new Exercice6Module());

    ServiceSurveillance premier = injector.getInstance(ServiceSurveillance.class);
    ServiceSurveillance second = injector.getInstance(ServiceSurveillance.class);

    premier.signaler("Pipistrelle");
    second.signaler("Murin");

    assertThat(injector.getInstance(JournalActivite.class).evenements())
        .as("deux services distincts partagent le MÊME journal singleton")
        .containsExactly("Pipistrelle", "Murin");
  }
}
