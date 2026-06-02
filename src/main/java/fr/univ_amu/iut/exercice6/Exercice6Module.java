package fr.univ_amu.iut.exercice6;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Module Guice de l'exercice 6.
 *
 * <p>Ici une même interface ({@link Notifieur}) a deux implémentations. Pour que Guice sache
 * laquelle fournir, on associe chaque implémentation à un <b>nom</b> ({@code @Named}). Un composant
 * demandera alors "le Notifieur nommé console" et Guice saura quoi injecter.
 *
 * <p>Remarque : {@link JournalActivite} n'apparaît pas ici. Son scope singleton est déclaré par
 * l'annotation {@code @Singleton} sur la classe elle-même ; aucun binding n'est requis.
 */
public class Exercice6Module extends AbstractModule {

  @Override
  protected void configure() {
    // TODO exercice 6 : lier chaque implémentation de Notifieur à un nom.
    //
    // - le nom "console"    -> NotifieurConsole
    // - le nom "silencieux" -> NotifieurSilencieux
    // Astuce :
    // bind(Notifieur.class).annotatedWith(Names.named("console")).to(NotifieurConsole.class);
    bind(Notifieur.class).annotatedWith(Names.named("console")).to(NotifieurConsole.class);
    bind(Notifieur.class).annotatedWith(Names.named("silencieux")).to(NotifieurSilencieux.class);
  }
}
